static ds3_bucket* _parse_bucket( const ds3_log* log, xmlDocPtr doc, xmlNodePtr root) {
    xmlNodePtr child_node;
    ds3_bucket* bucket = g_new0(ds3_bucket, 1);

    for (child_node = root->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {
        if (element_equal(child_node, "CreationDate")) {
            bucket->creation_date = xml_get_string(doc, child_node);
        } else if (element_equal(child_node, "Name")) {
            bucket->name = xml_get_string(doc, child_node);
        } else {
            ds3_log_message(log, DS3_ERROR, "Unknown element: (%s)\n", child_node->name);
        }
    }

    return bucket;
}

static GPtrArray* _parse_buckets(const ds3_log* log, xmlDocPtr doc, xmlNodePtr root) {
    xmlNodePtr child_node;
    GPtrArray* buckets_array = g_ptr_array_new();

    for (child_node = root->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {
        g_ptr_array_add(buckets_array, _parse_bucket(log, doc, child_node));
    }

    return buckets_array;
}

static ds3_owner* _parse_owner(const ds3_log* log, const xmlDocPtr doc, const xmlNodePtr owner_node) {
    xmlNodePtr child_node;
    ds3_owner* owner = g_new0(ds3_owner, 1);

    for (child_node = owner_node->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {
        if (element_equal(child_node, "DisplayName")) {
            owner->name = xml_get_string(doc, child_node);
        } else if (element_equal(child_node, "ID")) {
            owner->id = xml_get_string(doc, child_node);
        } else {
            ds3_log_message(log, DS3_ERROR, "Unknown element: (%s)\n", child_node->name);
        }
    }

    return owner;
}

static ds3_get_service_response* _parse_get_service_response(const ds3_log* log, const xmlDocPtr doc, const xmlNodePtr root) {
    ds3_get_service_response* response = g_new0(ds3_get_service_response, 1);
    xmlNodePtr child_node;

    for (child_node = root->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {
        if (element_equal(child_node, "Buckets") == true) {
            GPtrArray* buckets_array = _parse_buckets(log, doc, child_node);
            response->buckets = (ds3_bucket**)buckets_array->pdata;
            response->num_buckets = buckets_array->len;
            g_ptr_array_free(buckets_array, FALSE);
        } else if (element_equal(child_node, "Owner") == true) {
            response->owner = _parse_owner(log, doc, child_node);
        } else {
            ds3_log_message(log, DS3_ERROR, "Unknown xml element: (%s)\b", child_node->name);
        }
    }

    return response;
}

ds3_error* ds3_get_service(const ds3_client* client, const ds3_request* request, ds3_get_service_response** _response) {
    xmlDocPtr doc;
    xmlNodePtr root;
    ds3_error* error;

    error = _get_request_xml_nodes(client, request, &doc, &root, "ListAllMyBucketsResult");
    if (error != NULL) {
        return error;
    }

    *_response = _parse_get_service_response(client->log, doc, root);
    xmlFreeDoc(doc);
    return NULL;
}
