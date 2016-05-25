
struct _ds3_metadata {
    GHashTable* metadata;
};

typedef struct {
    char* buff;
    size_t size;
    size_t total_read;
}ds3_xml_send_buff;

typedef enum {
    BULK_PUT,
    BULK_GET,
    BULK_DELETE,
    GET_PHYSICAL_PLACEMENT
}object_list_type;

void ds3_client_register_logging(ds3_client* client, ds3_log_lvl log_lvl, void (* log_callback)(const char* log_message, void* user_data), void* user_data) {
    if (client == NULL) {
        fprintf(stderr, "Cannot configure a null ds3_client for logging.\n");
        return;
    }
    if (client->log != NULL) {
        g_free(client->log);
    }
    ds3_log* log = g_new0(ds3_log, 1);
    log->log_callback = log_callback;
    log->user_data = user_data;
    log->log_lvl = log_lvl;

    client->log = log;
}

static void _ds3_free_metadata_entry(gpointer pointer) {
    ds3_metadata_entry* entry;
    if (pointer == NULL) {
        return; // do nothing
    }

    entry = (ds3_metadata_entry*) pointer;

    ds3_free_metadata_entry(entry);
}

/*
 * This copies all the header values in the ds3_string_multimap_entry struct so that they may be safely returned to the user
 * without having to worry about if the data is freed internally.
 */
static const char* METADATA_PREFIX = "x-amz-meta-";
static ds3_metadata_entry* ds3_metadata_entry_init(ds3_string_multimap_entry* header_entry) {
    guint i;
    ds3_str* header_value;
    GPtrArray* values = g_ptr_array_new();
    ds3_str* key_name;
    ds3_str* full_key;
    ds3_metadata_entry* response = g_new0(ds3_metadata_entry, 1);
    int metadata_prefix_length = strlen(METADATA_PREFIX);

    unsigned int num_values = ds3_string_multimap_entry_get_num_values(header_entry);
    for (i = 0; i < num_values; i++) {
        header_value = ds3_string_multimap_entry_get_value_by_index(header_entry, i);
        g_ptr_array_add(values, header_value);
    }

    full_key = ds3_string_multimap_entry_get_key(header_entry);
    key_name = ds3_str_init(full_key->value + metadata_prefix_length);
    ds3_str_free(full_key);

    response->num_values = num_values;
    response->name = key_name;
    response->values = (ds3_str**) g_ptr_array_free(values, FALSE);
    fprintf(stderr, "creating metadata entry of: %s\n", key_name->value);
    return response;
}

/* The headers hash table contains all the response headers which have the following types:
 * Key - char*
 * Value - ds3_response_header
 *
 * All values should be copied from the struct to avoid memory issues
 */
static ds3_metadata* _init_metadata(ds3_string_multimap* response_headers) {
    struct _ds3_metadata* metadata = g_new0(struct _ds3_metadata, 1);
    GHashTableIter iter;
    gpointer _key, _value;
    ds3_str* key;
    ds3_metadata_entry* entry;
    metadata->metadata = g_hash_table_new_full(g_str_hash, g_str_equal, g_free, _ds3_free_metadata_entry);

    if (response_headers == NULL) {
        fprintf(stderr, "response headers was null\n");
    }

    //TODO refactor out glib: ds3_string_multimap needs a lookup_prefix()
    g_hash_table_iter_init(&iter, ds3_string_multimap_get_hashtable(response_headers));
    while(g_hash_table_iter_next(&iter, &_key, &_value)) {
        key = (ds3_str*) _key;
        if (g_str_has_prefix(key->value, "x-amz-meta-")) {
            ds3_string_multimap_entry* mm_entry = ds3_string_multimap_lookup(response_headers, key);
            entry = ds3_metadata_entry_init(mm_entry);
            g_hash_table_insert(metadata->metadata, g_strdup(entry->name->value), entry);
            ds3_string_multimap_entry_free(mm_entry);
        }
    }

    return (ds3_metadata*) metadata;
}

ds3_metadata_entry* ds3_metadata_get_entry(const ds3_metadata* _metadata, const char* name) {
    ds3_metadata_entry* copy;
    ds3_metadata_entry* orig;
    ds3_str** metadata_copy;
    uint64_t i;
    struct _ds3_metadata* metadata = (struct _ds3_metadata*) _metadata;

    if (_metadata == NULL) {
        return NULL;
    }

    orig = (ds3_metadata_entry*) g_hash_table_lookup(metadata->metadata, name);
    if (orig == NULL) {
        return NULL;
    }
    copy = g_new0(ds3_metadata_entry, 1);
    metadata_copy = g_new0(ds3_str*, orig->num_values);

    for (i = 0; i < orig->num_values; i++) {
        metadata_copy[i] = ds3_str_dup(orig->values[i]);
    }

    copy->num_values = orig->num_values;
    copy->name = ds3_str_dup(orig->name);
    copy->values = metadata_copy;

    return copy;
}

unsigned int ds3_metadata_size(const ds3_metadata* _metadata) {
    struct _ds3_metadata* metadata = (struct _ds3_metadata*) _metadata;
    if (metadata == NULL) {
        return 0;
    }
    return g_hash_table_size(metadata->metadata);
}

ds3_metadata_keys_result* ds3_metadata_keys(const ds3_metadata* _metadata) {
    GPtrArray* return_keys;
    ds3_metadata_keys_result* result;
    struct _ds3_metadata* metadata;
    GList* keys;
    GList* tmp_key;

    if (_metadata == NULL) {
        return NULL;
    }

    return_keys = g_ptr_array_new();
    result = g_new0(ds3_metadata_keys_result, 1);
    metadata = (struct _ds3_metadata*) _metadata;
    keys = g_hash_table_get_keys(metadata->metadata);
    tmp_key = keys;

    while(tmp_key != NULL) {
        g_ptr_array_add(return_keys, ds3_str_init((char*)tmp_key->data));
        tmp_key = tmp_key->next;
    }

    g_list_free(keys);
    result->num_keys = return_keys->len;
    result->keys = (ds3_str**) g_ptr_array_free(return_keys, FALSE);
    return result;
}

static size_t _ds3_send_xml_buff(void* buffer, size_t size, size_t nmemb, void* user_data) {
    size_t to_read;
    size_t remaining;
    ds3_xml_send_buff* xml_buff;

    xml_buff = (ds3_xml_send_buff*) user_data;
    to_read = size * nmemb;
    remaining = xml_buff->size - xml_buff->total_read;

    if (remaining < to_read) {
        to_read = remaining;
    }

    strncpy((char*)buffer, xml_buff->buff + xml_buff->total_read, to_read);
    xml_buff->total_read += to_read;
    return to_read;
}

static void _cleanup_hash_value(gpointer value) {
    g_free(value);
}

static GHashTable* _create_hash_table(void) {
    GHashTable* hash =  g_hash_table_new_full(g_str_hash, g_str_equal, _cleanup_hash_value, _cleanup_hash_value);
    return hash;
}

ds3_creds* ds3_create_creds(const char* access_id, const char* secret_key) {
    ds3_creds* creds;
    if (access_id == NULL || secret_key == NULL) {
        fprintf(stderr, "Arguments cannot be NULL\n");
        return NULL;
    }

    creds = g_new0(ds3_creds, 1);
    creds->access_id = ds3_str_init(access_id);
    creds->secret_key = ds3_str_init(secret_key);

    return creds;
}

void ds3_client_register_net(ds3_client* client, ds3_error* (* net_callback)(const ds3_client* client,
                                                                             const ds3_request* _request,
                                                                             void* read_user_struct,
                                                                             size_t (*read_handler_func)(void*, size_t, size_t, void*),
                                                                             void* write_user_struct,
                                                                             size_t (*write_handler_func)(void*, size_t, size_t, void*),
                                                                             ds3_string_multimap** return_headers)) {
    if (client == NULL) {
        fprintf(stderr, "Cannot configure a null ds3_client for net_callback.\n");
        return;
    }

    client->net_callback = net_callback;
}

ds3_client* ds3_create_client(const char* endpoint, ds3_creds* creds) {
    ds3_client* client;
    if (endpoint == NULL) {
        fprintf(stderr, "Null endpoint\n");
        return NULL;
    }

    client = g_new0(ds3_client, 1);
    client->endpoint = ds3_str_init(endpoint);
    client->creds = creds;
    client->num_redirects = 5L; //default to 5 redirects before failing

    ds3_client_register_net( client, net_process_request );

    return client;
}

ds3_error* ds3_create_client_from_env(ds3_client** client) {
    ds3_creds* creds;
    ds3_client* _client;
    char* endpoint = getenv("DS3_ENDPOINT");
    char* access_key = getenv("DS3_ACCESS_KEY");
    char* secret_key = getenv("DS3_SECRET_KEY");
    char* http_proxy = getenv("http_proxy");

    if (endpoint == NULL) {
        return ds3_create_error(DS3_ERROR_MISSING_ARGS, "Missing enviornment variable 'DS3_ENDPOINT'");
    }

    if (access_key == NULL) {
        return ds3_create_error(DS3_ERROR_MISSING_ARGS, "Missing enviornment variable 'DS3_ACCESS_KEY'");
    }

    if (secret_key == NULL) {
        return ds3_create_error(DS3_ERROR_MISSING_ARGS, "Missing enviornment variable 'DS3_SECRET_KEY'");
    }

    creds = ds3_create_creds(access_key, secret_key);
    _client = ds3_create_client(endpoint, creds);

    if (http_proxy != NULL) {
        ds3_client_proxy(_client, http_proxy);
    }

    *client = _client;

    return NULL;
}

static void _set_map_value(GHashTable* map, const char* key, const char* value) {
    gpointer escaped_key = (gpointer) escape_url(key);

    //TODO update this to handle multiple values being set for a header field
    gpointer escaped_value;
    if (value != NULL) {
        if (g_strcmp0(key, "Range") == 0) {
            escaped_value = (gpointer) escape_url_range_header(value);
        } else {
            escaped_value = (gpointer) escape_url(value);
        }
    } else {
        escaped_value = NULL;
    }
    g_hash_table_insert(map, escaped_key, escaped_value);
}

static void _set_query_param(ds3_request* _request, const char* key, const char* value) {
    struct _ds3_request* request = (struct _ds3_request*) _request;
    _set_map_value(request->query_params, key, value);
}

static void _set_header(ds3_request* _request, const char* key, const char* value) {
    struct _ds3_request* request = (struct _ds3_request*) _request;
    _set_map_value(request->headers, key, value);
}

void ds3_client_proxy(ds3_client* client, const char* proxy) {
    client->proxy = ds3_str_init(proxy);
}

void ds3_request_set_prefix(ds3_request* _request, const char* prefix) {
    _set_query_param(_request, "prefix", prefix);
}

void ds3_request_set_metadata(ds3_request* _request, const char* name, const char* value) {

    char* prefixed_name = g_strconcat("x-amz-meta-", name, NULL);

    _set_header(_request, prefixed_name, value);

    g_free(prefixed_name);
}

void ds3_request_reset_byte_range(ds3_request* _request) {
    g_hash_table_remove(_request->headers, "Range");
}

void ds3_request_set_byte_range(ds3_request* _request, int64_t rangeStart, int64_t rangeEnd) {
    char* range_value;

    gpointer header_value = g_hash_table_lookup(_request->headers, "Range");
    if (header_value != NULL) {
        range_value = g_strdup_printf("%s,%ld-%ld", (char*)header_value, rangeStart, rangeEnd);
    } else {
        range_value = g_strdup_printf("bytes=%ld-%ld", rangeStart, rangeEnd);
    }

    _set_header(_request, "Range", range_value);
    g_free(range_value);
}

void ds3_request_set_custom_header(ds3_request* _request, const char* header_name, const char* header_value) {
   _set_header(_request, header_name, header_value);
}

void ds3_request_set_bucket_name(ds3_request* _request, const char* bucket_name) {
    _set_query_param(_request, "bucket_id", bucket_name);
}

void ds3_request_set_creation_date(ds3_request* _request, const char* creation_date) {
    _set_query_param(_request, "creation_date", creation_date);
}

void ds3_request_set_md5(ds3_request* _request, const char* md5) {
    struct _ds3_request* request = (struct _ds3_request*) _request;
    request->checksum_type = DS3_CHECKSUM_TYPE_MD5;
    request->checksum = ds3_str_init(md5);
}

void ds3_request_set_sha256(ds3_request* _request, const char* sha256) {
    struct _ds3_request* request = (struct _ds3_request*) _request;
    request->checksum_type = DS3_CHECKSUM_TYPE_SHA_256;
    request->checksum = ds3_str_init(sha256);
}

void ds3_request_set_sha512(ds3_request* _request, const char* sha512) {
    struct _ds3_request* request = (struct _ds3_request*) _request;
    request->checksum_type = DS3_CHECKSUM_TYPE_SHA_512;
    request->checksum = ds3_str_init(sha512);
}

void ds3_request_set_crc32(ds3_request* _request, const char* crc32) {
    struct _ds3_request* request = (struct _ds3_request*) _request;
    request->checksum_type = DS3_CHECKSUM_TYPE_CRC_32;
    request->checksum = ds3_str_init(crc32);
}

void ds3_request_set_crc32c(ds3_request* _request, const char* crc32c) {
    struct _ds3_request* request = (struct _ds3_request*) _request;
    request->checksum_type = DS3_CHECKSUM_TYPE_CRC_32C;
    request->checksum = ds3_str_init(crc32c);
}

void ds3_request_set_delimiter(ds3_request* _request, const char* delimiter) {
    _set_query_param(_request, "delimiter", delimiter);
}

void ds3_request_set_marker(ds3_request* _request, const char* marker) {
    _set_query_param(_request, "marker", marker);
}

void ds3_request_set_max_keys(ds3_request* _request, uint32_t max_keys) {
    int metadata_prefix_length = strlen(METADATA_PREFIX);
    char max_keys_s[metadata_prefix_length];
    memset(max_keys_s, 0, sizeof(char) * metadata_prefix_length);
    g_snprintf(max_keys_s, sizeof(char) * metadata_prefix_length, "%u", max_keys);
    _set_query_param(_request, "max-keys", max_keys_s);
}
static const char UNSIGNED_LONG_BASE_10[] = "4294967296";
static const unsigned int UNSIGNED_LONG_BASE_10_STR_LEN = sizeof(UNSIGNED_LONG_BASE_10);

void ds3_request_set_preferred_number_of_chunks(ds3_request* _request, uint32_t num_chunks) {
    char num_chunks_s[UNSIGNED_LONG_BASE_10_STR_LEN];
    memset(num_chunks_s, 0, sizeof(char) * UNSIGNED_LONG_BASE_10_STR_LEN);
    g_snprintf(num_chunks_s, sizeof(char) * UNSIGNED_LONG_BASE_10_STR_LEN, "%u", num_chunks);
    _set_query_param(_request, "preferred_number_of_chunks", num_chunks_s);
}

void ds3_request_set_max_upload_size(ds3_request* _request, uint32_t max_upload_size) {
    char max_size_s[UNSIGNED_LONG_BASE_10_STR_LEN];
    memset(max_size_s, 0, sizeof(char) * UNSIGNED_LONG_BASE_10_STR_LEN);
    g_snprintf(max_size_s, sizeof(char) * UNSIGNED_LONG_BASE_10_STR_LEN, "%u", max_upload_size);
    _set_query_param(_request, "max_upload_size", max_size_s);
}

void ds3_request_set_name(ds3_request* _request, const char* name) {
    _set_query_param(_request, "name", name);
}

void ds3_request_set_id(ds3_request* _request, const char* id) {
    _set_query_param(_request, "id", id);
}

void ds3_request_set_type(ds3_request* _request, ds3_s3_object_type type) {
    const char* type_as_string = _get_ds3_s3_object_type_str(type);
    if (type_as_string != NULL) {
        _set_query_param(_request, "type", type_as_string);
    }
}

void ds3_request_set_page_length(ds3_request* _request, const char* page_length) {
    _set_query_param(_request, "page_length", page_length);
}

void ds3_request_set_page_offset(ds3_request* _request, const char* page_offset) {
    _set_query_param(_request, "page_offset", page_offset);
}

void ds3_request_set_version(ds3_request* _request, const char* version) {
    _set_query_param(_request, "version", version);
}

static struct _ds3_request* _common_request_init(http_verb verb, ds3_str* path) {
    struct _ds3_request* request = g_new0(struct _ds3_request, 1);
    request->headers = _create_hash_table();
    request->query_params = _create_hash_table();
    request->verb = verb;
    request->path = path;
    return request;
}

static ds3_str* _build_path(const char* path_prefix, const char* bucket_name, const char* object_name) {
    ds3_str* buildPathArgs = NULL;
    char* escaped_bucket_name = NULL;
    char* escaped_object_name = NULL;
    char* joined_path = NULL;
    char* full_path = NULL;

    if (bucket_name != NULL) {
        if (g_str_has_suffix(bucket_name, "/") == TRUE) {
            char* chomp_bucket = g_strndup(bucket_name, strlen(bucket_name)-1);
            escaped_bucket_name = escape_url(chomp_bucket);
            g_free(chomp_bucket);
        } else {
            escaped_bucket_name = escape_url(bucket_name);
        }
    }
    if (object_name != NULL) {
        escaped_object_name = escape_url_object_name(object_name);
    }

    joined_path = g_strjoin("/", escaped_bucket_name, escaped_object_name, NULL);
    full_path = g_strconcat(path_prefix, joined_path, NULL);
    g_free(joined_path);

    buildPathArgs = ds3_str_init(full_path);
    g_free(full_path);

    if (escaped_bucket_name != NULL) {
        g_free(escaped_bucket_name);
    }
    if (escaped_object_name != NULL) {
        g_free(escaped_object_name);
    }

    return buildPathArgs;
}
