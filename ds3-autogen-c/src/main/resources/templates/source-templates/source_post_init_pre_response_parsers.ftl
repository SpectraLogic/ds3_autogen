
static ds3_error* _internal_request_dispatcher(
        const ds3_client* client,
        const ds3_request* request,
        void* read_user_struct,
        size_t (*read_handler_func)(void*, size_t, size_t, void*),
        void* write_user_struct,
        size_t (*write_handler_func)(void*, size_t, size_t, void*)) {
    if (client == NULL || request == NULL) {
        return ds3_create_error(DS3_ERROR_MISSING_ARGS, "All arguments must be filled in for request processing");
    }
    return net_process_request(client, request, read_user_struct, read_handler_func, write_user_struct, write_handler_func, NULL);
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
        const ds3_client* client,
        const ds3_request* request,
        xmlDocPtr* _doc,
        xmlNodePtr* _root,
        char* root_element_name) {
    xmlDocPtr doc;
    xmlNodePtr root;
    GByteArray* xml_blob = g_byte_array_new();

    ds3_error* error = _internal_request_dispatcher(client, request, xml_blob, ds3_load_buffer, NULL, NULL);
    if (error != NULL) {
        g_byte_array_free(xml_blob, TRUE);
        return error;
    }

    doc = xmlParseMemory((const char*) xml_blob->data, xml_blob->len);
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

static void _parse_build_information(const ds3_log* log, xmlDocPtr doc, xmlNodePtr build_info_node, ds3_build_information** _build_info_response) {
    ds3_build_information* build_info_response;
    xmlNodePtr build_element;
    build_info_response = g_new0(ds3_build_information, 1);

    for (build_element = build_info_node->xmlChildrenNode; build_element != NULL; build_element = build_element->next) {
        if (element_equal(build_element, "Branch") == true) {
            build_info_response->branch = xml_get_string(doc, build_element);
        } else if (element_equal(build_element, "Revision")) {
            build_info_response->revision = xml_get_string(doc, build_element);
        } else if (element_equal(build_element, "Version")) {
            build_info_response->version = xml_get_string(doc, build_element);
        } else {
            ds3_log_message(log, DS3_ERROR, "Unknown element: (%s)\n", build_element->name);
        }
    }
    *_build_info_response = build_info_response;
}
