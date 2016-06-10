
void ds3_creds_free(ds3_creds* creds) {
    if (creds == NULL) {
        return;
    }

    ds3_str_free(creds->access_id);
    ds3_str_free(creds->secret_key);
    g_free(creds);
}

void ds3_client_free(ds3_client* client) {
    if (client == NULL) {
      return;
    }

    ds3_str_free(client->endpoint);
    ds3_str_free(client->proxy);
    if (client->log != NULL) {
        g_free(client->log);
    }
    g_free(client);
}

void ds3_multipart_upload_part_response_free(ds3_multipart_upload_part_response* response) {
    if (response == NULL) {
        return;
    }

    ds3_str_free(response->etag);
}

void ds3_complete_multipart_upload_response_free(ds3_complete_multipart_upload_response* response) {
    if (response == NULL) {
        return;
    }

    int index;
    for (index = 0; index < response->num_parts; index++) {
        ds3_multipart_upload_part_response_free(response->parts[index]);
    }
}

void ds3_delete_objects_response_free(ds3_delete_objects_response* response) {
    if (response == NULL) {
        return;
    }

    int index;
    for (index = 0; index < response->num_strings; index++) {
        ds3_str_free(response->strings_list[index]);
    }
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

void ds3_metadata_free(ds3_metadata* _metadata) {
    struct _ds3_metadata* metadata;
    if (_metadata == NULL) return;

    metadata = (struct _ds3_metadata*) _metadata;

    if (metadata->metadata == NULL) return;

    g_hash_table_destroy(metadata->metadata);

    g_free(metadata);
}

void ds3_metadata_entry_free(ds3_metadata_entry* entry) {
    int value_index;
    ds3_str* value;
    if (entry->name != NULL) {
        ds3_str_free(entry->name);
    }
    if (entry->values != NULL) {
        for (value_index = 0; value_index < entry->num_values; value_index++) {
            value = entry->values[value_index];
            ds3_str_free(value);
        }
        g_free(entry->values);
    }
    g_free(entry);
}

void ds3_metadata_keys_free(ds3_metadata_keys_result* metadata_keys) {
    uint64_t key_index;
    if (metadata_keys == NULL) {
        return;
    }

    if (metadata_keys->keys != NULL) {
        for (key_index = 0; key_index < metadata_keys->num_keys; key_index++) {
            ds3_str_free(metadata_keys->keys[key_index]);
        }
        g_free(metadata_keys->keys);
    }
    g_free(metadata_keys);
}

void ds3_error_free(ds3_error* error) {
    if (error == NULL) {
        return;
    }

    ds3_str_free(error->message);

    if (error->error != NULL) {
        ds3_error_response* error_response = error->error;

        ds3_str_free(error_response->message);
        ds3_str_free(error_response->resource);

        g_free(error_response);
    }

    g_free(error);
}

void ds3_cleanup(void) {
    net_cleanup();
    xmlCleanupParser();
}

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

ds3_bulk_object_list_response* ds3_convert_object_list(const ds3_bulk_object_response** objects, size_t num_objects) {
    size_t object_index;
    ds3_bulk_object_list_response* obj_list = ds3_init_bulk_object_list();

    GPtrArray* ds3_bulk_object_response_array = g_ptr_array_new();

    for (object_index = 0; object_index < num_objects; object_index++) {
        ds3_bulk_object_response* response = g_new0(ds3_bulk_object_response, 1);
        response->name = ds3_str_dup(objects[object_index]->name);
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

