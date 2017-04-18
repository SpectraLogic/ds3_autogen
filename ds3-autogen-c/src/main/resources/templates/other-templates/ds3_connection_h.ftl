
<#include "../CopyrightHeader.ftl"/>

#ifndef __DS3_CONNECTION_H__
#define __DS3_CONNECTION_H__

#ifdef __cplusplus
extern "C" {
#endif

#include <curl/curl.h>
#include <glib.h>

#define CONNECTION_POOL_SIZE 100

typedef GMutex ds3_mutex;
typedef GCond ds3_condition;

typedef CURL ds3_connection;

//-- Opaque struct
struct _ds3_connection_pool{
    ds3_connection* connections[CONNECTION_POOL_SIZE];
    int head;
    int tail;
    ds3_mutex mutex;
    ds3_condition available_connections;
};

ds3_connection_pool* ds3_connection_pool_init(void);
void ds3_connection_pool_clear(ds3_connection_pool* pool);

ds3_connection* ds3_connection_acquire(ds3_connection_pool* pool);
void ds3_connection_release(ds3_connection_pool* pool, ds3_connection* handle);

#ifdef __cplusplus
}
#endif
#endif
