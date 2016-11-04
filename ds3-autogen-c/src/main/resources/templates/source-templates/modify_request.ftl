
static const char UNSIGNED_LONG_BASE_10[] = "4294967296";
static const unsigned int UNSIGNED_LONG_BASE_10_STR_LEN = sizeof(UNSIGNED_LONG_BASE_10);

typedef struct {
    char* buff;
    size_t size;
    size_t total_read;
}ds3_xml_send_buff;

typedef enum {
    BULK_PUT,
    BULK_GET,
    BULK_DELETE,
    GET_PHYSICAL_PLACEMENT,
    COMPLETE_MPU,
    STRING,
    STRING_LIST,
    DATA
}object_list_type;

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

static void _set_header(ds3_request* _request, const char* key, const char* value) {
    struct _ds3_request* request = (struct _ds3_request*) _request;
    _set_map_value(request->headers, key, value);
}

void ds3_request_set_custom_header(ds3_request* _request, const char* header_name, const char* header_value) {
   _set_header(_request, header_name, header_value);
}

void ds3_request_set_metadata(ds3_request* _request, const char* name, const char* value) {
    if ((value == NULL)
     || (strlen(value) == 0)) {
        fprintf(stderr, "Ignoring metadata key \"%s\" which has a NULL or empty value.\n", name);
        return;
    }

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

static void _set_query_param(const ds3_request* _request, const char* key, const char* value) {
    const struct _ds3_request* request = (const struct _ds3_request*) _request;
    _set_map_value(request->query_params, key, value);
}

static void _set_query_param_flag(const ds3_request* _request, const char* key, ds3_bool value) {
    if (value == False) {
        g_hash_table_remove(_request->headers, key);
    } else {
        _set_query_param(_request, key, NULL);
    }
}

static void _set_query_param_uint64_t(const ds3_request* _request, const char* key, uint64_t value) {
    char string_buffer[UNSIGNED_LONG_BASE_10_STR_LEN];
    memset(string_buffer, 0, sizeof(string_buffer));
    snprintf(string_buffer, sizeof(string_buffer), "%lu", value);
    _set_query_param(_request, key, string_buffer);
}

static void _set_query_param_int(const ds3_request* _request, const char* key, int value) {
    char string_buffer[UNSIGNED_LONG_BASE_10_STR_LEN];
    memset(string_buffer, 0, sizeof(string_buffer));
    snprintf(string_buffer, sizeof(string_buffer), "%d", value);
    _set_query_param(_request, key, string_buffer);
}

static void _set_query_param_float(const ds3_request* _request, const char* key, float value) {
    char string_buffer[UNSIGNED_LONG_BASE_10_STR_LEN];
    memset(string_buffer, 0, sizeof(string_buffer));
    snprintf(string_buffer, sizeof(string_buffer), "%f", value);
    _set_query_param(_request, key, string_buffer);
}
<#list getOptionalQueryParams() as queryParam>
    <#include "RequestSetQueryParameter.ftl">
</#list>

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
        if (g_str_has_suffix(bucket_name, "/") == (gboolean)TRUE) {
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
