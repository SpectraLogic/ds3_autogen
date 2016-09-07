void ds3_multipart_upload_part_response_free(ds3_multipart_upload_part_response* response) {
    if (response == NULL) {
        return;
    }

    ds3_str_free(response->etag);

    g_free(response);
}

void ds3_complete_multipart_upload_response_free(ds3_complete_multipart_upload_response* response) {
    if (response == NULL) {
        return;
    }

    int index;
    for (index = 0; index < response->num_parts; index++) {
        ds3_multipart_upload_part_response_free(response->parts[index]);
    }
    g_free(response->parts);
    g_free(response);
}

void ds3_delete_objects_response_free(ds3_delete_objects_response* response) {
    if (response == NULL) {
        return;
    }

    int index;
    for (index = 0; index < response->num_strings; index++) {
        ds3_str_free(response->strings_list[index]);
    }
    g_free(response->strings_list);
    g_free(response);
}

void ds3_paging_free(ds3_paging* paging) {
    g_free(paging);
}

void ds3_request_free(ds3_request* _request) {
    struct _ds3_request* request;
    if (_request == NULL) {
        return;
    }

    request = (struct _ds3_request*) _request;

    ds3_str_free(request->path);
    ds3_str_free(request->checksum);

    if (request->headers != NULL) {
        g_hash_table_destroy(request->headers);
    }
    if (request->query_params != NULL) {
        g_hash_table_destroy(request->query_params);
    }

    g_free(request);
}

void ds3_error_free(ds3_error* error) {
    if (error == NULL) {
        return;
    }

    ds3_str_free(error->message);

    ds3_error_response_free(error->error);

    g_free(error);
}

void ds3_cleanup(void) {
    net_cleanup();
    xmlCleanupParser();
}

