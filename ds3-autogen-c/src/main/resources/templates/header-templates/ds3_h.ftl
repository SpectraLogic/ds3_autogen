<#include "../CopyrightHeader.ftl"/>

/** @file ds3.h
 *  @brief The public definitions for the Spectra S3 C SDK
 */

#ifndef __DS3_HEADER__
#define __DS3_HEADER__

#include <stdint.h>
#include <string.h>
#include <curl/curl.h>
#include "ds3_bool.h"
#include "ds3_string.h"
#include "ds3_string_multimap.h"
#include "ds3_uint64_string_map.h"
#include "ds3_library_exports.h"

#ifdef __cplusplus
extern "C" {
#endif

#define DS3_READFUNC_ABORT CURL_READFUNC_ABORT


typedef struct {
    int page_truncated;
    int total_result_count;
}ds3_paging;

typedef struct _ds3_request ds3_request;

typedef struct _ds3_connection_pool ds3_connection_pool;

typedef struct {
    ds3_str*    name;
    ds3_str**   values;
    uint64_t    num_values;
}ds3_metadata_entry;

typedef struct {
    ds3_str**  keys;
    uint64_t   num_keys;
}ds3_metadata_keys_result;

typedef struct _ds3_metadata ds3_metadata;

typedef enum {
    HTTP_GET,
    HTTP_PUT,
    HTTP_POST,
    HTTP_DELETE,
    HTTP_HEAD
}http_verb;

typedef enum {
    DS3_ERROR,
    DS3_WARN,
    DS3_INFO,
    DS3_DEBUG,
    DS3_TRACE
}ds3_log_lvl;

typedef struct {
    void     (* log_callback)(const char* log_message, void* user_data);
    void*       user_data;
    ds3_log_lvl log_lvl;
}ds3_log;

typedef struct {
    ds3_str* access_id;
    ds3_str* secret_key;
}ds3_creds;

typedef enum {
  DS3_ERROR_INVALID_XML,
  DS3_ERROR_CURL_HANDLE,
  DS3_ERROR_REQUEST_FAILED,
  DS3_ERROR_MISSING_ARGS,
  DS3_ERROR_BAD_STATUS_CODE,
  DS3_ERROR_TOO_MANY_REDIRECTS
}ds3_error_code;

typedef struct {
    ds3_str* etag;
    int      part_number;
}ds3_multipart_upload_part_response;

typedef struct {
    ds3_multipart_upload_part_response** parts;
    int                         num_parts;
}ds3_complete_multipart_upload_response;

typedef struct {
    ds3_str** strings_list;
    int       num_strings;
}ds3_delete_objects_response;

typedef struct {
    ds3_str** strings_list;
    int       num_strings;
}ds3_ids_list;

<#-- **************************************** -->
<#-- Generate all Models                      -->
<#list getEnums() as enumEntry>
    <#include "TypedefEnum.ftl">
</#list>
<#-- **************************************** -->

typedef struct {
    ds3_metadata* metadata;
    ds3_checksum_type *blob_checksum_type;
    ds3_uint64_string_map* blob_checksums;
}ds3_head_object_response;

<#-- **************************************** -->
<#-- Generate all Structs                     -->
<#list getStructs() as structEntry>
    <#include "TypedefStruct.ftl">
</#list>
<#-- **************************************** -->

typedef struct {
    ds3_error_code      code;
    ds3_str*            message;
    ds3_error_response* error;
}ds3_error;

typedef struct _ds3_client {
    ds3_str*      endpoint;
    ds3_str*      proxy;
    uint64_t      num_redirects;
    ds3_creds*    creds;
    ds3_log*      log;
    ds3_error* (* net_callback)(const struct _ds3_client* client,
                                const ds3_request* _request,
                                void* read_user_struct,
                                size_t (*read_handler_func)(void*, size_t, size_t, void*),
                                void* write_user_struct,
                                size_t (*write_handler_func)(void*, size_t, size_t, void*),
                                ds3_string_multimap** return_headers);
    ds3_connection_pool* connection_pool;
}ds3_client;

<#-- ********************************************* -->
<#-- Generate all "FreeStruct" functions prototypes -->
<#list getStructs() as structEntry>
    <#include "FreeStructPrototype.ftl">
</#list>
<#-- ********************************************* -->

LIBRARY_API void ds3_request_free(ds3_request* request);
LIBRARY_API void ds3_error_free(ds3_error* error);
LIBRARY_API void ds3_head_object_response_free(ds3_head_object_response* response);
LIBRARY_API void ds3_multipart_upload_part_response_free(ds3_multipart_upload_part_response* response);
LIBRARY_API void ds3_complete_multipart_upload_response_free(ds3_complete_multipart_upload_response* response);
LIBRARY_API void ds3_delete_objects_response_free(ds3_delete_objects_response* response);
LIBRARY_API void ds3_ids_list_free(ds3_ids_list* ids);

LIBRARY_API ds3_metadata_entry* ds3_metadata_get_entry(const ds3_metadata* metadata, const char* name);
LIBRARY_API unsigned int ds3_metadata_size(const ds3_metadata* metadata);
LIBRARY_API ds3_metadata_keys_result* ds3_metadata_keys(const ds3_metadata* metadata);

LIBRARY_API void ds3_metadata_free(ds3_metadata* _metadata);
LIBRARY_API void ds3_metadata_entry_free(ds3_metadata_entry* entry);
LIBRARY_API void ds3_metadata_keys_free(ds3_metadata_keys_result* metadata_keys);

LIBRARY_API void ds3_creds_free(ds3_creds* client);
LIBRARY_API void ds3_client_free(ds3_client* client);

LIBRARY_API ds3_creds*  ds3_create_creds(const char *const access_id, const char *const secret_key);
LIBRARY_API ds3_client* ds3_create_client(const char *const endpoint, ds3_creds* creds);
LIBRARY_API ds3_error*  ds3_create_client_from_env(ds3_client** client);
LIBRARY_API ds3_client* ds3_copy_client(const ds3_client* client);
LIBRARY_API void        ds3_client_register_logging(ds3_client* client, ds3_log_lvl log_lvl, void (* log_callback)(const char* log_message, void* user_data), void* user_data);
LIBRARY_API void        ds3_client_register_net(ds3_client* client, ds3_error* (* net_callback)(const ds3_client* client,
                                                                                                const ds3_request* _request,
                                                                                                void* read_user_struct,
                                                                                                size_t (*read_handler_func)(void*, size_t, size_t, void*),
                                                                                                void* write_user_struct,
                                                                                                size_t (*write_handler_func)(void*, size_t, size_t, void*),
                                                                                                ds3_string_multimap** return_headers));
LIBRARY_API void ds3_client_proxy(ds3_client* client, const char *const proxy);

// Set optional request query parameters
LIBRARY_API void ds3_request_set_byte_range(ds3_request* _request, int64_t rangeStart, int64_t rangeEnd);
LIBRARY_API void ds3_request_reset_byte_range(ds3_request* _request);
<#list getOptionalQueryParams() as queryParam>
LIBRARY_API ${parameterHelper.generateSetQueryParamSignature(queryParam)};
</#list>

// Set headers / metadata
LIBRARY_API void ds3_request_set_custom_header(ds3_request* request, const char *const header_name, const char *const header_value);
LIBRARY_API void ds3_request_set_md5(ds3_request* request, const char *const md5);
LIBRARY_API void ds3_request_set_sha256(ds3_request* request, const char *const sha256);
LIBRARY_API void ds3_request_set_sha512(ds3_request* request, const char *const sha512);
LIBRARY_API void ds3_request_set_crc32(ds3_request* request, const char *const crc32);
LIBRARY_API void ds3_request_set_crc32c(ds3_request* request, const char *const crc32c);
// Any attempt to set a key with an empty or NULL value will be ignored.
LIBRARY_API void ds3_request_set_metadata(ds3_request* request, const char *const name, const char *const value);

LIBRARY_API ds3_error* ds3_get_object_with_metadata(const ds3_client* client,
                                                    const ds3_request* request,
                                                    void* user_data,
                                                    size_t (* callback)(void*, size_t, size_t, void*),
                                                    ds3_metadata** _metadata);

<#-- **************************************** -->
<#-- Generate all "RequestPrototypes"         -->
<#list getRequests() as requestEntry>

    <#if requestEntry.getName() == "ds3_head_bucket_request">
LIBRARY_API ds3_request* ds3_init_head_bucket_request(const char *const bucket_name);
LIBRARY_API ds3_error* ds3_head_bucket_request(const ds3_client* client, const ds3_request* request);
    <#elseif requestEntry.getName() == "ds3_head_object_request">
LIBRARY_API ds3_request* ds3_init_head_object_request(const char* bucket_name, const char *const object_name);
LIBRARY_API ds3_error* ds3_head_object_request(const ds3_client* client, const ds3_request* request, ds3_head_object_response** response);
    <#else>
        <#include "RequestCommentDocumentation.ftl">
        <#include "InitRequestPrototype.ftl">
        <#include "RequestPrototype.ftl">
    </#if>
</#list>
<#-- **************************************** -->


LIBRARY_API void ds3_cleanup(void);

// provided helpers
LIBRARY_API size_t ds3_write_to_file(void* buffer, size_t size, size_t nmemb, void* user_data);
LIBRARY_API size_t ds3_read_from_file(void* buffer, size_t size, size_t nmemb, void* user_data);
LIBRARY_API size_t ds3_write_to_fd(void* buffer, size_t size, size_t nmemb, void* user_data);
LIBRARY_API size_t ds3_read_from_fd(void* buffer, size_t size, size_t nmemb, void* user_data);

LIBRARY_API ds3_bulk_object_list_response* ds3_convert_file_list(const char** file_list, uint64_t num_files);
LIBRARY_API ds3_bulk_object_list_response* ds3_convert_file_list_with_basepath(const char** file_list, uint64_t num_files, const char* base_path);
LIBRARY_API ds3_bulk_object_list_response* ds3_convert_object_list(const ds3_contents_response** objects, uint64_t num_objects);
LIBRARY_API ds3_bulk_object_list_response* ds3_convert_object_list_from_strings(const char** objects, uint64_t num_objects);
/*
 * Init a single ds3_bulk_object_list_response object containing no ds3_bulk_object_response objects
 */
LIBRARY_API ds3_bulk_object_list_response* ds3_init_bulk_object_list();
/*
 * Init a single ds3_bulk_object_list_response object containing num_objects ds3_bulk_object_response objects.
 *   each ds3_bulk_object_response object will need its properties (name, bucket, etc) to be set with ds3_str_init("name"),
 *   and physical_placement will be null.
 */
LIBRARY_API ds3_bulk_object_list_response* ds3_init_bulk_object_list_with_size(size_t num_objects);

/**
 * Retrieves the size of the specified file. If the file does not exist, 0 is returned.
 */
LIBRARY_API uint64_t ds3_get_file_size(const char* file_path);

#ifdef __cplusplus
}
#endif
#endif

