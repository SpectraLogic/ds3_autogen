<#include "../CopyrightHeader.ftl"/>


#ifndef __DS3_HEADER__
#define __DS3_HEADER__

#include <stdint.h>
#include <string.h>
#include <curl/curl.h>
#include "ds3_string.h"
#include "ds3_string_multimap.h"

#ifdef __cplusplus
extern "C" {
#endif

// For windows DLL symbol exports.
#ifdef _WIN32
#    ifdef LIBRARY_EXPORTS
#        define LIBRARY_API __declspec(dllexport)
#    else
#        define LIBRARY_API __declspec(dllimport)
#    endif
#else
#    define LIBRARY_API
#endif

#define DS3_READFUNC_ABORT CURL_READFUNC_ABORT

typedef struct _ds3_request ds3_request;

typedef enum {
    False, True
}ds3_bool;

typedef enum {
    HTTP_GET,
    HTTP_PUT,
    HTTP_POST,
    HTTP_DELETE,
    HTTP_HEAD
}http_verb;

typedef enum {
    DATA,
    FOLDER
}ds3_object_type;

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

<#--
typedef struct {
    uint64_t  status_code;
    ds3_str*  status_message;
    ds3_str*  error_body;
}ds3_error_response;
-->

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
}ds3_client;

<#-- **************************************** -->
<#-- Generate all Models                      -->
<#list getEnums() as enumEntry>
    <#include "TypedefEnum.ftl">
</#list>
<#-- **************************************** -->

<#-- **************************************** -->
<#-- Generate all Structs                     -->
<#list getStructs() as structEntry>
    <#include "TypedefStruct.ftl">
</#list>
<#-- **************************************** -->

<#-- ********************************************* -->
<#-- Generate all "FreeStruct" functions prototypes -->
<#list getStructs() as structEntry>
    <#include "FreeStructPrototype.ftl">
</#list>
<#-- ********************************************* -->


LIBRARY_API ds3_metadata_entry* ds3_metadata_get_entry(const ds3_metadata* metadata, const char* name);
LIBRARY_API unsigned int ds3_metadata_size(const ds3_metadata* metadata);
LIBRARY_API ds3_metadata_keys_result* ds3_metadata_keys(const ds3_metadata* metadata);

LIBRARY_API ds3_creds*  ds3_create_creds(const char* access_id, const char* secret_key);
LIBRARY_API ds3_client* ds3_create_client(const char* endpoint, ds3_creds* creds);
LIBRARY_API ds3_error*  ds3_create_client_from_env(ds3_client** client);
LIBRARY_API void        ds3_client_register_logging(ds3_client* client, ds3_log_lvl log_lvl, void (* log_callback)(const char* log_message, void* user_data), void* user_data);
LIBRARY_API void        ds3_client_register_net(ds3_client* client, ds3_error* (* net_callback)(const ds3_client* client,
                                                                                                const ds3_request* _request,
                                                                                                void* read_user_struct,
                                                                                                size_t (*read_handler_func)(void*, size_t, size_t, void*),
                                                                                                void* write_user_struct,
                                                                                                size_t (*write_handler_func)(void*, size_t, size_t, void*),
                                                                                                ds3_string_multimap** return_headers));
LIBRARY_API void ds3_client_proxy(ds3_client* client, const char* proxy);


<#-- **************************************** -->
<#-- Generate all "RequestPrototypes"         -->
<#list getRequests() as requestEntry>
    <#include "RequestPrototype.ftl">
</#list>
<#-- **************************************** -->


LIBRARY_API void ds3_cleanup(void);
LIBRARY_API void ds3_print_request(const ds3_request* request);

// provided helpers
LIBRARY_API size_t ds3_write_to_file(void* buffer, size_t size, size_t nmemb, void* user_data);
LIBRARY_API size_t ds3_read_from_file(void* buffer, size_t size, size_t nmemb, void* user_data);
LIBRARY_API size_t ds3_write_to_fd(void* buffer, size_t size, size_t nmemb, void* user_data);
LIBRARY_API size_t ds3_read_from_fd(void* buffer, size_t size, size_t nmemb, void* user_data);

LIBRARY_API ds3_bulk_object_list* ds3_convert_file_list(const char** file_list, uint64_t num_files);
LIBRARY_API ds3_bulk_object_list* ds3_convert_file_list_with_basepath(const char** file_list, uint64_t num_files, const char* base_path);
LIBRARY_API ds3_bulk_object_list* ds3_convert_object_list(const ds3_object* objects, uint64_t num_objects);
LIBRARY_API ds3_bulk_object_list* ds3_init_bulk_object_list(uint64_t num_files);


#ifdef __cplusplus
}
#endif
#endif

