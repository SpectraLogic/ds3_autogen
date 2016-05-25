ds3_error* head_bucket_request(const ds3_client* client, const ds3_request* request) {
    if (client == NULL || request == NULL) {
        return ds3_create_error(DS3_ERROR_MISSING_ARGS, "All arguments must be filled in for request processing");
    }
    if (g_ascii_strncasecmp(request->path->value, "//", 2) == 0) {
        return ds3_create_error(DS3_ERROR_MISSING_ARGS, "The bucket name parameter is required.");
    }

    return _internal_request_dispatcher(client, request, NULL, NULL, NULL, NULL, NULL);
}

