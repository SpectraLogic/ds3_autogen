
#define LENGTH_BUFF_SIZE 21

static xmlDocPtr _generate_xml_objects_list(const ds3_bulk_object_list_response* obj_list, object_list_type list_type, ds3_job_chunk_client_processing_order_guarantee order) {
    char size_buff[LENGTH_BUFF_SIZE]; //The max size of an uint64_t should be 20 characters
    xmlDocPtr doc;
    ds3_bulk_object_response* obj;
    xmlNodePtr objects_node, object_node;
    int i;
    // Start creating the xml body to send to the server.
    doc = xmlNewDoc((xmlChar*)"1.0");
    if (list_type == BULK_DELETE) {
        objects_node = xmlNewNode(NULL, (xmlChar*) "Delete");
    } else {
        objects_node = xmlNewNode(NULL, (xmlChar*) "Objects");
    }

    if (list_type == BULK_GET) {
        xmlSetProp(objects_node, (xmlChar*) "ChunkClientProcessingOrderGuarantee", (xmlChar*) _get_ds3_job_chunk_client_processing_order_guarantee_str(order));
    }

    for (i = 0; i < obj_list->num_objects; i++) {
        //memset(&obj, 0, sizeof(ds3_bulk_object_response));
        //memset(size_buff, 0, sizeof(char) * LENGTH_BUFF_SIZE);

        obj = obj_list->objects[i];
        g_snprintf(size_buff, sizeof(char) * LENGTH_BUFF_SIZE, "%llu", (unsigned long long int) obj->length);

        object_node = xmlNewNode(NULL, (xmlChar*) "Object");
        xmlAddChild(objects_node, object_node);

        if (list_type == BULK_DELETE) {
            xmlNewTextChild(object_node, NULL, (xmlChar*) "Key", (xmlChar*) obj->name->value);
        } else {
            xmlSetProp(object_node, (xmlChar*) "Name", (xmlChar*) obj->name->value);
            if (list_type == BULK_PUT) {
                xmlSetProp(object_node, (xmlChar*) "Size", (xmlChar*) size_buff);
            }
        }
    }

    xmlDocSetRootElement(doc, objects_node);

    return doc;
}

void ds3_free_creds(ds3_creds* creds) {
    if (creds == NULL) {
        return;
    }

    ds3_str_free(creds->access_id);
    ds3_str_free(creds->secret_key);
    g_free(creds);
}

void ds3_free_client(ds3_client* client) {
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

void ds3_free_request(ds3_request* _request) {
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

    ds3_bulk_object_list_response_free(request->object_list);
    g_free(request);
}

void ds3_free_metadata(ds3_metadata* _metadata) {
    struct _ds3_metadata* metadata;
    if (_metadata == NULL) return;

    metadata = (struct _ds3_metadata*) _metadata;

    if (metadata->metadata == NULL) return;

    g_hash_table_destroy(metadata->metadata);

    g_free(metadata);
}

void ds3_free_metadata_entry(ds3_metadata_entry* entry) {
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

void ds3_free_metadata_keys(ds3_metadata_keys_result* metadata_keys) {
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

void ds3_free_error(ds3_error* error) {
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

static ds3_bulk_object_response _ds3_bulk_object_from_file(const char* file_name, const char* base_path) {
    struct stat file_info;
    int result;
    ds3_bulk_object_response obj;
    char* file_to_stat;
    memset(&file_info, 0, sizeof(struct stat));
    memset(&obj, 0, sizeof(ds3_bulk_object_response));

    if (base_path != NULL) {
        file_to_stat = g_strconcat(base_path, file_name, NULL);
    } else {
        file_to_stat = g_strdup(file_name);
    }

    result = stat(file_to_stat, &file_info);
    if (result != 0) {
        fprintf(stderr, "Failed to get file info for %s\n", file_name);
    }

    memset(&obj, 0, sizeof(ds3_bulk_object_response));

    obj.name = ds3_str_init(file_name);

    if (S_ISDIR(file_info.st_mode)) {
        obj.length = 0;
    } else {
      obj.length = file_info.st_size;
    }

    g_free(file_to_stat);

    return obj;
}

ds3_bulk_object_list_response* ds3_convert_file_list(const char** file_list, uint64_t num_files) {
    return ds3_convert_file_list_with_basepath(file_list, num_files, NULL);
}

ds3_bulk_object_list_response* ds3_convert_file_list_with_basepath(const char** file_list, uint64_t num_files, const char* base_path) {
    uint64_t file_index;
    ds3_bulk_object_list_response* obj_list = ds3_init_bulk_object_list(num_files);

    for (file_index = 0; file_index < num_files; file_index++) {
        *obj_list->objects[file_index] = _ds3_bulk_object_from_file(file_list[file_index], base_path);
    }

    return obj_list;
}

ds3_bulk_object_list_response* ds3_convert_object_list(const ds3_bulk_object_response* objects, uint64_t num_objects) {
    uint64_t object_index;
    ds3_bulk_object_list_response* obj_list = ds3_init_bulk_object_list(num_objects);

    for (object_index = 0; object_index < num_objects; object_index++) {
        ds3_bulk_object_response obj;
        memset(&obj, 0, sizeof(ds3_bulk_object_response));
        obj.name = ds3_str_dup(objects[object_index].name);
        obj_list->objects[object_index] = &obj;
    }

    return obj_list;
}

ds3_bulk_object_list_response* ds3_init_bulk_object_list(uint64_t num_files) {
    ds3_bulk_object_list_response* obj_list = g_new0(ds3_bulk_object_list_response, 1);
    obj_list->num_objects = num_files;
    *obj_list->objects = g_new0(ds3_bulk_object_response, num_files);

    return obj_list;
}

