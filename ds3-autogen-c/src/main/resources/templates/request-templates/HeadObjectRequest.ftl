ds3_error* ds3_head_object_request(const ds3_client* client, const ds3_request* request, ds3_head_object_response** response) {
    ds3_error* error;
    ds3_string_multimap* return_headers;
    ds3_metadata* metadata;

    int num_slashes = num_chars_in_ds3_str(request->path, '/');
    if (num_slashes < 2 || ((num_slashes == 2) && ('/' == request->path->value[request->path->size-1]))) {
        return ds3_create_error(DS3_ERROR_MISSING_ARGS, "The object name parameter is required.");
    } else if (g_ascii_strncasecmp(request->path->value, "//", 2) == 0) {
        return ds3_create_error(DS3_ERROR_MISSING_ARGS, "The bucket name parameter is required.");
    }

    error = _internal_request_dispatcher(client, request, NULL, NULL, NULL, NULL, &return_headers);

    if (error == NULL) {
        ds3_head_object_response* response_ptr = g_new0(ds3_head_object_response, 1);
        response_ptr->metadata = _init_metadata(return_headers);
        response_ptr->blob_checksum_type = get_blob_checksum_type(client->log, return_headers);
        response_ptr->blob_checksums = get_blob_checksums(client->log, return_headers);
        *response = response_ptr;
        ds3_string_multimap_free(return_headers);
    }

    return error;
}

