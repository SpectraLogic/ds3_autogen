#include <glib.h>
#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <curl/curl.h>
#include <libxml/parser.h>
#include <libxml/xmlmemory.h>
#include <errno.h>
#include <inttypes.h>

#include "ds3.h"
#include "ds3_request.h"
#include "ds3_string_multimap.h"
#include "ds3_string_multimap_impl.h"
#include "ds3_net.h"
#include "ds3_utils.h"
#include "ds3_connection.h"

#ifdef _WIN32
#include <io.h>
#else
#include <unistd.h>
#endif


#ifndef S_ISDIR
#define S_ISDIR(mode)  (((mode) & S_IFMT) == S_IFDIR)
#endif

//The max size of an uint32_t should be 10 characters + NULL
static const char UNSIGNED_LONG_BASE_10[] = "4294967296";
static const unsigned int UNSIGNED_LONG_BASE_10_STR_LEN = sizeof(UNSIGNED_LONG_BASE_10) + 1;
//The max size of an uint64_t should be 20 characters + NULL
static const char UNSIGNED_LONG_LONG_BASE_10[] = "18446744073709551615";
static const unsigned int UNSIGNED_LONG_LONG_BASE_10_STR_LEN = sizeof(UNSIGNED_LONG_LONG_BASE_10) + 1;
