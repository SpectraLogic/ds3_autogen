<#include "../CopyrightHeader.ftl"/>

#ifndef __DS3_REQUEST_H__
#define __DS3_REQUEST_H__

#ifdef __cplusplus
extern "C" {
#endif

#include <glib.h>
#include "ds3.h"

//---------- Define opaque struct ----------//
struct _ds3_request{
    http_verb         verb;
    ds3_str*          path;
    uint64_t          length;
    ds3_str*          checksum;
    ds3_checksum_type checksum_type;
    GHashTable*       headers;
    GHashTable*       query_params;

    //These next few elements are for request payload
    ds3_bulk_object_list_response*                  object_list;
    ds3_job_chunk_client_processing_order_guarantee chunk_ordering;

    ds3_complete_multipart_upload_response*          mpu_list;

    ds3_delete_objects_response*                    delete_objects;
};

typedef struct {
    // These attributes are used when processing a response header
    uint64_t status_code;
    ds3_str* status_message;
    size_t header_count;
    ds3_string_multimap* headers;

    // These attributes are used when processing a response body
    GByteArray* body; // this will only be used when getting errors
    void* user_data;
    size_t (*user_func)(void*, size_t, size_t, void*);
}ds3_response_data;


#ifdef __cplusplus
}
#endif
#endif
