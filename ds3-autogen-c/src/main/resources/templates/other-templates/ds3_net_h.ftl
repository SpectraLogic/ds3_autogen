
<#include "../CopyrightHeader.ftl"/>

#ifndef __DS3_NET_H__
#define __DS3_NET_H__

#ifdef __cplusplus
extern "C" {
#endif

#include "ds3.h"
#include "ds3_string_multimap.h"

char* escape_url(const char* url);
char* escape_url_extended(const char* url, const char** delimiters, uint32_t num_delimiters);
char* escape_url_object_name(const char* url);
char* escape_url_range_header(const char* url);

ds3_error* net_process_request(
   const ds3_client* client,
   const ds3_request* _request,
   void* read_user_struct,
   size_t (*read_handler_func)(void*, size_t, size_t, void*),
   void* write_user_struct,
   size_t (*write_handler_func)(void*, size_t, size_t, void*),
   ds3_string_multimap** return_headers);

void net_cleanup(void);

#ifdef __cplusplus
}
#endif
#endif
