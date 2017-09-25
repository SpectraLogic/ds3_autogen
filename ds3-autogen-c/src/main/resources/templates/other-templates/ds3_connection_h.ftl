
<#include "../CopyrightHeader.ftl"/>

#ifndef __DS3_CONNECTION_H__
#define __DS3_CONNECTION_H__

#ifdef __cplusplus
extern "C" {
#endif

#include <curl/curl.h>
#include <glib.h>
#include "ds3.h"

#define DEFAULT_CONNECTION_POOL_SIZE 10

typedef GMutex ds3_mutex;
typedef GCond ds3_condition;

typedef CURL ds3_connection;

typedef struct _ds3_connection_pool ds3_connection_pool;

ds3_connection_pool* ds3_connection_pool_init(void);
ds3_connection_pool* ds3_connection_pool_init_with_size(uint16_t pool_size);
void ds3_connection_pool_clear(ds3_connection_pool* pool, ds3_bool already_locked);

ds3_connection* ds3_connection_acquire(ds3_connection_pool* pool);
void ds3_connection_release(ds3_connection_pool* pool, ds3_connection* handle);

void ds3_connection_pool_inc_ref(ds3_connection_pool* pool);
void ds3_connection_pool_dec_ref(ds3_connection_pool* pool);

#ifdef __cplusplus
}
#endif
#endif
