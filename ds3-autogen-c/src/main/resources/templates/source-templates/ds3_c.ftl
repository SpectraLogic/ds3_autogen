<#include "../CopyrightHeader.ftl"/>

#include <glib.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/stat.h>
#include <libxml/parser.h>
#include <errno.h>

#include "ds3.h"
#include "ds3_net.h"
#include "ds3_connection.h"
#include "ds3_request.h"
#include "ds3_string_multimap_impl.h"
#include "ds3_utils.h"

#ifdef _WIN32
#include <io.h>
#else
#include <unistd.h>
#endif

#ifndef S_ISDIR
#define S_ISDIR(mode)  (((mode) & S_IFMT) == S_IFDIR)
#endif

<#include "client.ftl"/>

<#include "file_utils.ftl"/>

<#include "free_custom_types.ftl"/>

<#-- *********************************************** -->
<#-- Generate all "StructFreeFunctions" from Structs -->
<#list getStructs() as structEntry>
    <#include "FreeStruct.ftl">
</#list>
