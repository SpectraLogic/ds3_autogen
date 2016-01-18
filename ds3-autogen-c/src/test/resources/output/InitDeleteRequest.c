ds3_request* ds3_init_delete_bucket(const char* bucket_name) {
    return (ds3_request*) _common_request_init(HTTP_DELETE, _build_path("/", bucket_name, NULL));
}
