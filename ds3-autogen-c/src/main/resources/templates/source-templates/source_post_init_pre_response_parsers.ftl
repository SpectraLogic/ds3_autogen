
static ds3_error* _internal_request_dispatcher(
        const ds3_client* client,
        const ds3_request* request,
        void* read_user_struct,
        size_t (*read_handler_func)(void*, size_t, size_t, void*),
        void* write_user_struct,
        size_t (*write_handler_func)(void*, size_t, size_t, void*),
        ds3_string_multimap** return_headers) {
    if (client == NULL || request == NULL) {
        return ds3_create_error(DS3_ERROR_MISSING_ARGS, "All arguments must be filled in for request processing");
    }
    return net_process_request(client, request, read_user_struct, read_handler_func, write_user_struct, write_handler_func, return_headers);
}

static int num_chars_in_ds3_str(const ds3_str* str, char ch) {
    int num_matches = 0;
    int index;

    for (index = 0; index < str->size; index++) {
        if (str->value[index] == '/') {
            num_matches++;
        }
    }

    return num_matches;
}

static bool attribute_equal(const struct _xmlAttr* attribute, const char* attribute_name) {
    return xmlStrcmp(attribute->name, (const xmlChar*) attribute_name) == 0;
}

static bool element_equal(const xmlNodePtr xml_node, const char* element_name) {
    return xmlStrcmp(xml_node->name, (const xmlChar*) element_name) == 0;
}

static uint16_t xml_get_uint16(xmlDocPtr doc, xmlNodePtr child_node) {
    xmlChar* text;
    uint16_t size;
    text = xmlNodeListGetString(doc, child_node->xmlChildrenNode, 1);
    if (text == NULL) {
        return 0;
    }
    size = atoi((char*)text);
    xmlFree(text);
    return size;
}

static uint64_t xml_get_uint16_from_attribute(xmlDocPtr doc, struct _xmlAttr* attribute) {
    return xml_get_uint16(doc, (xmlNodePtr) attribute);
}

static uint64_t xml_get_uint64(xmlDocPtr doc, xmlNodePtr child_node) {
    xmlChar* text;
    uint64_t size;
    text = xmlNodeListGetString(doc, child_node->xmlChildrenNode, 1);
    if (text == NULL) {
        return 0;
    }
    size = g_ascii_strtoull((const char*)text, NULL, 10);
    xmlFree(text);
    return size;
}

static uint64_t xml_get_uint64_from_attribute(xmlDocPtr doc, struct _xmlAttr* attribute) {
    return xml_get_uint64(doc, (xmlNodePtr) attribute);
}

static ds3_str* xml_get_string(xmlDocPtr doc, xmlNodePtr child_node) {
    xmlChar* text;
    ds3_str* result;
    text = xmlNodeListGetString(doc, child_node->xmlChildrenNode, 1);
    if (text == NULL) {
        // Element is found, but is empty: <name />
        return NULL;
    }
    result = ds3_str_init((const char*) text);
    xmlFree(text);
    return result;
}

static ds3_str* xml_get_string_from_attribute(xmlDocPtr doc, struct _xmlAttr* attribute) {
    return xml_get_string(doc, (xmlNodePtr) attribute);
}

static ds3_bool xml_get_bool(const ds3_log* log, xmlDocPtr doc, const xmlNodePtr xml_node) {
    xmlChar* text;
    ds3_bool result;
    text = xmlNodeListGetString(doc, xml_node->xmlChildrenNode, 1);
    if (xmlStrcmp(text, (xmlChar*)"true") == 0) {
        result = True;
    } else if (xmlStrcmp(text, (xmlChar*)"false") == 0) {
        result = False;
    } else {
        ds3_log_message(log, DS3_ERROR, "Unknown boolean value");
        result = False;
    }
    xmlFree(text);
    return result;
}

static uint64_t xml_get_bool_from_attribute(const ds3_log* log, xmlDocPtr doc, struct _xmlAttr* attribute) {
    return xml_get_bool(log, doc, (xmlNodePtr) attribute);
}

static ds3_error* _get_request_xml_nodes(
        GByteArray* xml_blob,
        xmlDocPtr* _doc,
        xmlNodePtr* _root,
        char* root_element_name) {
    xmlNodePtr root;

    xmlDocPtr doc = xmlParseMemory((const char*) xml_blob->data, xml_blob->len);
    if (doc == NULL) {
        char* message = g_strconcat("Failed to parse response document.  The actual response is: ", xml_blob->data, NULL);
        g_byte_array_free(xml_blob, TRUE);
        ds3_error* error = ds3_create_error(DS3_ERROR_INVALID_XML, message);
        g_free(message);
        return error;
    }

    root = xmlDocGetRootElement(doc);
    if (element_equal(root, root_element_name) == false) {
        char* message = g_strconcat("Expected the root element to be '", root_element_name, "'.  The actual response is: ", xml_blob->data, NULL);
        xmlFreeDoc(doc);
        g_byte_array_free(xml_blob, TRUE);
        ds3_error* error = ds3_create_error(DS3_ERROR_INVALID_XML, message);
        g_free(message);
        return error;
    }

    *_doc = doc;
    *_root = root;

    g_byte_array_free(xml_blob, TRUE);
    return NULL;
}

#define LENGTH_BUFF_SIZE 21

static xmlDocPtr _generate_xml_bulk_objects_list(const ds3_bulk_object_list_response* obj_list, object_list_type list_type, ds3_job_chunk_client_processing_order_guarantee order) {
    char size_buff[LENGTH_BUFF_SIZE]; //The max size of an uint64_t should be 20 characters
    xmlDocPtr doc;
    ds3_bulk_object_response* obj;
    xmlNodePtr objects_node, object_node;
    int i;

    // Start creating the xml body to send to the server.
    doc = xmlNewDoc((xmlChar*)"1.0");
    objects_node = xmlNewNode(NULL, (xmlChar*) "Objects");

    if (list_type == BULK_GET) {
        xmlSetProp(objects_node, (xmlChar*) "ChunkClientProcessingOrderGuarantee", (xmlChar*) _get_ds3_job_chunk_client_processing_order_guarantee_str(order));
    }

    for (i = 0; i < obj_list->num_objects; i++) {
        obj = obj_list->objects[i];
        g_snprintf(size_buff, sizeof(char) * LENGTH_BUFF_SIZE, "%llu", (unsigned long long int) obj->length);

        object_node = xmlNewNode(NULL, (xmlChar*) "Object");
        xmlAddChild(objects_node, object_node);

        xmlSetProp(object_node, (xmlChar*) "Name", (xmlChar*) obj->name->value);
        if (list_type == BULK_PUT) {
            xmlSetProp(object_node, (xmlChar*) "Size", (xmlChar*) size_buff);
        }
    }

    xmlDocSetRootElement(doc, objects_node);

    return doc;
}

static xmlDocPtr _generate_xml_complete_mpu(const ds3_complete_multipart_upload_response* mpu_list) {
    char size_buff[LENGTH_BUFF_SIZE]; //The max size of an uint64_t should be 20 characters
    xmlDocPtr doc;
    ds3_multipart_upload_part_response* part;
    xmlNodePtr parts_node, part_node;
    int part_num;

    // Start creating the xml body to send to the server.
    doc = xmlNewDoc((xmlChar*)"1.0");
    parts_node = xmlNewNode(NULL, (xmlChar*) "CompleteMultipartUpload");

    for (part_num = 0; part_num < mpu_list->num_parts; part_num++) {
        part = mpu_list->parts[part_num];

        part_node = xmlNewNode(NULL, (xmlChar*) "Part");
        xmlAddChild(parts_node, part_node);

        g_snprintf(size_buff, sizeof(char) * LENGTH_BUFF_SIZE, "%d", part->part_number);
        xmlNewTextChild(part_node, NULL, (xmlChar*) "PartNumber", (xmlChar*) size_buff);

        xmlNewTextChild(part_node, NULL, (xmlChar*) "ETag", (xmlChar*) part->etag->value);
    }

    xmlDocSetRootElement(doc, parts_node);
    return doc;
}

static xmlDocPtr _generate_xml_delete_objects(ds3_delete_objects_response* keys_list) {
    xmlDocPtr doc;
    ds3_str* key;
    xmlNodePtr del_node, obj_node;
    int key_num;

    // Start creating the xml body to send to the server.
    doc = xmlNewDoc((xmlChar*)"1.0");
    del_node = xmlNewNode(NULL, (xmlChar*) "Delete");

    for (key_num = 0; key_num < keys_list->num_strings; key_num++) {
        key = keys_list->strings_list[key_num];

        obj_node = xmlNewNode(NULL, (xmlChar*) "Object");
        xmlAddChild(del_node, obj_node);

        xmlNewTextChild(obj_node, NULL, (xmlChar*) "Key", (xmlChar*) key->value);
    }

    xmlDocSetRootElement(doc, del_node);
    return doc;
}

ds3_error* _init_request_payload(const ds3_request* _request,
                                 ds3_xml_send_buff* send_buff,
                                 const object_list_type operation_type) {
    xmlDocPtr doc;

    struct _ds3_request* request = (struct _ds3_request*) _request;

    // Clear send_buff
    memset(send_buff, 0, sizeof(ds3_xml_send_buff));

    switch(operation_type) {
        case BULK_PUT:
        case BULK_GET:
        case GET_PHYSICAL_PLACEMENT:
            if (request->object_list == NULL || request->object_list->num_objects == 0) {
                return ds3_create_error(DS3_ERROR_MISSING_ARGS, "The bulk command requires a list of objects to process");
            }
            doc = _generate_xml_bulk_objects_list(request->object_list, operation_type, request->chunk_ordering);
            break;

        case COMPLETE_MPU:
            if (request->mpu_list == NULL || request->mpu_list->num_parts == 0) {
                return ds3_create_error(DS3_ERROR_MISSING_ARGS, "The complete multipart upload command requires a list of objects to process");
            }
            doc = _generate_xml_complete_mpu(request->mpu_list);
            break;

        case BULK_DELETE:
        case STRING_LIST:
            if (request->delete_objects == NULL || request->delete_objects->num_strings == 0) {
                return ds3_create_error(DS3_ERROR_MISSING_ARGS, "The delete objects command requires a list of objects to process");
            }
            doc = _generate_xml_delete_objects(request->delete_objects);
            break;

        case STRING: // *** not XML - do not interpret
            send_buff->buff = request->delete_objects->strings_list[0]->value;
            send_buff->size = request->delete_objects->strings_list[0]->size;
            request->length = send_buff->size;
            return NULL;
            break;

        default:
            return ds3_create_error(DS3_ERROR_INVALID_XML, "Unknown request payload type");
    }

    xmlDocDumpFormatMemory(doc, (xmlChar**) &send_buff->buff, (int*) &send_buff->size, 1);
    request->length = send_buff->size; // make sure to set the size of the request.

    xmlFreeDoc(doc);

    return NULL;
}

