typedef enum {
    BULK_PUT,
    BULK_GET,
    BULK_DELETE,
    GET_PHYSICAL_PLACEMENT,
    COMPLETE_MPU,
    STRING,
    STRING_LIST,
    ID_LIST,
    DATA
}object_list_type;

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

static int num_chars_in_ds3_str(const ds3_str *const str, char ch) {
    int num_matches = 0;
    size_t index;

    for (index = 0; index < str->size; index++) {
        if (str->value[index] == '/') {
            num_matches++;
        }
    }

    return num_matches;
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

static xmlDocPtr _generate_xml_bulk_objects_list(const ds3_bulk_object_list_response* obj_list, object_list_type list_type) {
    char size_buff[STRING_BUFFER_SIZE];
    xmlDocPtr doc;
    ds3_bulk_object_response* obj;
    xmlNodePtr objects_node, object_node;
    size_t obj_index;

    // Start creating the xml body to send to the server.
    doc = xmlNewDoc((xmlChar*)"1.0");
    objects_node = xmlNewNode(NULL, (xmlChar*) "Objects");

    for (obj_index = 0; obj_index < obj_list->num_objects; obj_index++) {
        obj = obj_list->objects[obj_index];
        memset(size_buff, 0, sizeof(size_buff));
        g_snprintf(size_buff, STRING_BUFFER_SIZE, "%" PRIu64, obj->length);

        object_node = xmlNewNode(NULL, (xmlChar*) "Object");
        xmlAddChild(objects_node, object_node);

        xmlSetProp(object_node, (xmlChar*) "Name", (xmlChar*) obj->name->value);
        if (list_type == BULK_PUT) {
            xmlSetProp(object_node, (xmlChar*) "Size", (xmlChar*) size_buff);
        }

        if (list_type == BULK_GET && obj->version_id != NULL && strlen(obj->version_id->value) != 0) {
        	xmlSetProp(object_node, (xmlChar*) "VersionId", (xmlChar*) obj->version_id->value);
        }
    }

    xmlDocSetRootElement(doc, objects_node);

    return doc;
}

static xmlDocPtr _generate_xml_complete_mpu(const ds3_complete_multipart_upload_response* mpu_list) {
    char size_buff[STRING_BUFFER_SIZE];
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

        g_snprintf(size_buff, STRING_BUFFER_SIZE, "%d", part->part_number);
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

static xmlDocPtr _generate_xml_ids(ds3_ids_list* ids_list) {
    xmlDocPtr doc;
    ds3_str* key;
    xmlNodePtr ids_node, id_node;
    int id_num;

    // Start creating the xml body to send to the server.
    doc = xmlNewDoc((xmlChar*)"1.0");
    ids_node = xmlNewNode(NULL, (xmlChar*) "Ids");

    for (id_num = 0; id_num < ids_list->num_strings; id_num++) {
        key = ids_list->strings_list[id_num];

        xmlNewTextChild(id_node, NULL, (xmlChar*) "Id", (xmlChar*) key->value);
        xmlAddChild(ids_node, id_node);
    }

    xmlDocSetRootElement(doc, ids_node);
    return doc;
}

static ds3_error* _init_request_payload(const ds3_request* _request,
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
            doc = _generate_xml_bulk_objects_list(request->object_list, operation_type);
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

        case ID_LIST:
            if (request->ids == NULL || request->ids->num_strings == 0) {
                return ds3_create_error(DS3_ERROR_MISSING_ARGS, "The suspect blob command requires a list of ids to process");
            }
            doc = _generate_xml_ids(request->ids);
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

