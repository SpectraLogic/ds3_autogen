
size_t ds3_write_to_file(void* buffer, size_t size, size_t nmemb, void* user_data) {
    return fwrite(buffer, size, nmemb, (FILE*) user_data);
}

size_t ds3_read_from_file(void* buffer, size_t size, size_t nmemb, void* user_data) {
    return fread(buffer, size, nmemb, (FILE*) user_data);
}

size_t ds3_write_to_fd(void* buffer, size_t size, size_t nmemb, void* user_data) {
    return write(*(int*)user_data, buffer, size * nmemb);
}

size_t ds3_read_from_fd(void* buffer, size_t size, size_t nmemb, void* user_data) {
    return read(*(int*)user_data, buffer, size * nmemb);
}

static ds3_bulk_object_response* _ds3_bulk_object_from_file(const char* file_name, const char* base_path) {
    struct stat file_info;
    int result;
    ds3_bulk_object_response* obj = g_new0(ds3_bulk_object_response, 1);
    char* file_to_stat;
    memset(&file_info, 0, sizeof(struct stat));

    if (base_path != NULL) {
        file_to_stat = g_strconcat(base_path, file_name, NULL);
    } else {
        file_to_stat = g_strdup(file_name);
    }

    result = stat(file_to_stat, &file_info);
    if (result != 0) {
        fprintf(stderr, "Failed to get file info for %s\n", file_name);
    }

    obj->name = ds3_str_init(file_name);

    if (S_ISDIR(file_info.st_mode)) {
        obj->length = 0;
    } else {
        obj->length = file_info.st_size;
    }

    g_free(file_to_stat);

    return obj;
}

ds3_bulk_object_list_response* ds3_convert_file_list(const char** file_list, size_t num_files) {
    return ds3_convert_file_list_with_basepath(file_list, num_files, NULL);
}

ds3_bulk_object_list_response* ds3_convert_file_list_with_basepath(const char** file_list, size_t num_files, const char* base_path) {
    size_t file_index;
    ds3_bulk_object_list_response* obj_list = ds3_init_bulk_object_list();

    GPtrArray* ds3_bulk_object_response_array = g_ptr_array_new();
    for (file_index = 0; file_index < num_files; file_index++) {
        g_ptr_array_add(ds3_bulk_object_response_array, _ds3_bulk_object_from_file(file_list[file_index], base_path));
    }

    obj_list->objects = (ds3_bulk_object_response**)ds3_bulk_object_response_array->pdata;
    obj_list->num_objects = ds3_bulk_object_response_array->len;
    g_ptr_array_free(ds3_bulk_object_response_array, FALSE);

    return obj_list;
}

ds3_bulk_object_list_response* ds3_convert_object_list(const ds3_contents_response** objects, size_t num_objects) {
    size_t object_index;
    ds3_bulk_object_list_response* obj_list = ds3_init_bulk_object_list();

    GPtrArray* ds3_bulk_object_response_array = g_ptr_array_new();

    for (object_index = 0; object_index < num_objects; object_index++) {
        ds3_bulk_object_response* response = g_new0(ds3_bulk_object_response, 1);
        response->name = ds3_str_dup(objects[object_index]->key);
        g_ptr_array_add(ds3_bulk_object_response_array, response);
    }

    obj_list->objects = (ds3_bulk_object_response**)ds3_bulk_object_response_array->pdata;
    obj_list->num_objects = ds3_bulk_object_response_array->len;
    g_ptr_array_free(ds3_bulk_object_response_array, FALSE);

    return obj_list;
}

ds3_bulk_object_list_response* ds3_init_bulk_object_list() {
    return g_new0(ds3_bulk_object_list_response, 1);
}
