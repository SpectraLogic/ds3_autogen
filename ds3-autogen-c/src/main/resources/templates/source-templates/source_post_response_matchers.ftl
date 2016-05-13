
#define LENGTH_BUFF_SIZE 21

static xmlDocPtr _generate_xml_objects_list(const ds3_bulk_object_list* obj_list, object_list_type list_type, ds3_chunk_ordering order) {
    char size_buff[LENGTH_BUFF_SIZE]; //The max size of an uint64_t should be 20 characters
    xmlDocPtr doc;
    ds3_bulk_object obj;
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
        xmlSetProp(objects_node, (xmlChar*) "ChunkClientProcessingOrderGuarantee", (xmlChar*) _get_chunk_order_str(order));
    }

    for (i = 0; i < obj_list->size; i++) {
        memset(&obj, 0, sizeof(ds3_bulk_object));
        memset(size_buff, 0, sizeof(char) * LENGTH_BUFF_SIZE);

        obj = obj_list->list[i];
        g_snprintf(size_buff, sizeof(char) * LENGTH_BUFF_SIZE, "%llu", (unsigned long long int) obj.length);

        object_node = xmlNewNode(NULL, (xmlChar*) "Object");
        xmlAddChild(objects_node, object_node);

        if (list_type == BULK_DELETE) {
            xmlNewTextChild(object_node, NULL, (xmlChar*) "Key", (xmlChar*) obj.name->value);
        } else {
            xmlSetProp(object_node, (xmlChar*) "Name", (xmlChar*) obj.name->value);
            if (list_type == BULK_PUT) {
                xmlSetProp(object_node, (xmlChar*) "Size", (xmlChar*) size_buff);
            }
        }
    }

    xmlDocSetRootElement(doc, objects_node);

    return doc;
}

static object_list_type _bulk_request_type(const struct _ds3_request* request) {

    char* value = (char *) g_hash_table_lookup(request->query_params, "operation");static ds3_error* _common_job(const ds3_client* client, const ds3_request* request, ds3_bulk_response** response) {
    ds3_error* error;
    GByteArray* xml_blob = g_byte_array_new();
    ds3_bulk_response* bulk_response;
    xmlDocPtr doc;

    error = net_process_request(client, request, xml_blob, ds3_load_buffer, NULL, NULL, NULL);

    if (error != NULL) {
        g_byte_array_free(xml_blob, TRUE);
        return error;
    }

    // Start processing the data that was received back.
    doc = xmlParseMemory((const char*) xml_blob->data, xml_blob->len);
    if (doc == NULL) {
        g_byte_array_free(xml_blob, TRUE);
        return ds3_create_error(DS3_ERROR_REQUEST_FAILED, "Unexpected empty response body.");
    }

    _parse_master_object_list(client->log, doc, &bulk_response);

    xmlFreeDoc(doc);
    g_byte_array_free(xml_blob, TRUE);

    *response = bulk_response;
    return NULL;
}


    if (strcmp(value, "start_bulk_get") == 0) {
        return BULK_GET;
    }
    return BULK_PUT;
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
    if (request->headers != NULL) {
        g_hash_table_destroy(request->headers);
    }
    if (request->query_params != NULL) {
        g_hash_table_destroy(request->query_params);
    }
    ds3_str_free(request->buildPathArgs);
    ds3_str_free(request->checksum);
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

        ds3_str_free(error_response->status_message);
        ds3_str_free(error_response->error_body);

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

static ds3_bulk_object _ds3_bulk_object_from_file(const char* file_name, const char* base_path) {
    struct stat file_info;
    int result;
    ds3_bulk_object obj;
    char* file_to_stat;
    memset(&file_info, 0, sizeof(struct stat));
    memset(&obj, 0, sizeof(ds3_bulk_object));

    if (base_path != NULL) {
        file_to_stat = g_strconcat(base_path, file_name, NULL);
    } else {
        file_to_stat = g_strdup(file_name);
    }

    result = stat(file_to_stat, &file_info);
    if (result != 0) {
        fprintf(stderr, "Failed to get file info for %s\n", file_name);
    }

    memset(&obj, 0, sizeof(ds3_bulk_object));

    obj.name = ds3_str_init(file_name);

    if (S_ISDIR(file_info.st_mode)) {
        obj.length = 0;
    } else {
      obj.length = file_info.st_size;
    }

    g_free(file_to_stat);

    return obj;
}

ds3_bulk_object_list* ds3_convert_file_list(const char** file_list, uint64_t num_files) {
    return ds3_convert_file_list_with_basepath(file_list, num_files, NULL);
}

ds3_bulk_object_list* ds3_convert_file_list_with_basepath(const char** file_list, uint64_t num_files, const char* base_path) {
    uint64_t file_index;
    ds3_bulk_object_list* obj_list = ds3_init_bulk_object_list(num_files);

    for (file_index = 0; file_index < num_files; file_index++) {
        obj_list->list[file_index] = _ds3_bulk_object_from_file(file_list[file_index], base_path);
    }

    return obj_list;
}

ds3_bulk_object_list* ds3_convert_object_list(const ds3_object* objects, uint64_t num_objects) {
    uint64_t object_index;
    ds3_bulk_object_list* obj_list = ds3_init_bulk_object_list(num_objects);

    for (object_index = 0; object_index < num_objects; object_index++) {
        ds3_bulk_object obj;
        memset(&obj, 0, sizeof(ds3_bulk_object));
        obj.name = ds3_str_dup(objects[object_index].name);
        obj_list->list[object_index] = obj;
    }

    return obj_list;
}

ds3_bulk_object_list* ds3_init_bulk_object_list(uint64_t num_files) {
    ds3_bulk_object_list* obj_list = g_new0(ds3_bulk_object_list, 1);
    obj_list->size = num_files;
    obj_list->list = g_new0(ds3_bulk_object, num_files);

    return obj_list;
}

void ds3_free_bulk_object_list(ds3_bulk_object_list* object_list) {
    uint64_t list_index, count;
    if (object_list == NULL) {
        return;
    }

    count = object_list->size;
    for (list_index = 0; list_index < count; list_index++) {
        ds3_str_free(object_list->list[list_index].name);
    }

    ds3_str_free(object_list->server_id);
    ds3_str_free(object_list->node_id);
    ds3_str_free(object_list->chunk_id);

    g_free(object_list->list);
    g_free(object_list);
}

void ds3_free_nodes_list(ds3_nodes_list* nodes_list) {
    uint64_t list_index;
    if (nodes_list == NULL || nodes_list->list == NULL) {
        return;
    }

    for (list_index = 0; list_index < nodes_list->size; list_index++) {
        ds3_node* current_node = nodes_list->list[list_index];

        ds3_str_free(current_node->endpoint);
        ds3_str_free(current_node->id);
        g_free(current_node);
    }

    g_free(nodes_list->list);
    g_free(nodes_list);
}


