static void _parse_buckets(const ds3_log* log, xmlDocPtr doc, xmlNodePtr buckets_node, ds3_get_service_response* response) {
    xmlNodePtr data_ptr;
    xmlNodePtr curr;
    GArray* array = g_array_new(FALSE, TRUE, sizeof(ds3_bucket));

    for (curr = buckets_node->xmlChildrenNode; curr != NULL; curr = curr->next) {
        ds3_bucket bucket;
        memset(&bucket, 0, sizeof(ds3_bucket));

        for (data_ptr = curr->xmlChildrenNode; data_ptr != NULL; data_ptr = data_ptr->next) {
            if (element_equal(data_ptr, "CreationDate")) {
                bucket.creation_date = xml_get_string(doc, data_ptr);
            } else if (element_equal(data_ptr, "Name")) {
                bucket.name = xml_get_string(doc, data_ptr);
            } else {
                ds3_log_message(log, DS3_ERROR, "Unknown element: (%s)\n", data_ptr->name);
            }
        }
        g_array_append_val(array, bucket);
    }

    response->num_buckets = array->len;
    response->buckets = (ds3_bucket*)array->data;
    g_array_free(array, FALSE);
}

static ds3_owner* _parse_owner(xmlDocPtr doc, xmlNodePtr owner_node) {
    xmlNodePtr child_node;
    ds3_owner* owner = g_new0(ds3_owner, 1);

    for (child_node = owner_node->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {
        if (element_equal(child_node, "DisplayName")) {
            owner->name = xml_get_string(doc, child_node);
        } else if (element_equal(child_node, "ID")) {
            owner->id = xml_get_string(doc, child_node);
        } else {
            fprintf(stderr, "Unknown xml element: (%s)\n", child_node->name);
        }
    }

    return owner;
}

ds3_error* ds3_get_service(const ds3_client* client, const ds3_request* request, ds3_get_service_response** _response) {
    ds3_get_service_response* response;
    xmlDocPtr doc;
    xmlNodePtr root;
    xmlNodePtr child_node;
    ds3_error* error;

    error = _get_request_xml_nodes(client, request, &doc, &root, "ListAllMyBucketsResult");
    if (error != NULL) {
        return error;
    }

    response = g_new0(ds3_get_service_response, 1);
    for (child_node = root->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {
        if (element_equal(child_node, "Buckets") == true) {
            _parse_buckets(client->log, doc, child_node, response);
        } else if (element_equal(child_node, "Owner") == true) {
            response->owner = _parse_owner(doc, child_node);
        } else {
            ds3_log_message(client->log, DS3_ERROR, "Unknown xml element: (%s)\b", child_node->name);
        }
    }

    xmlFreeDoc(doc);
    *_response = response;
    return NULL;
}
