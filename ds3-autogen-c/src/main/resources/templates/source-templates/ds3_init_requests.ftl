<#include "../CopyrightHeader.ftl"/>

#include <glib.h>
#include <stdlib.h>
#include <stdio.h>
#include <libxml/parser.h>
#include <inttypes.h>

#include "ds3.h"
#include "ds3_request.h"
#include "ds3_net.h"

#ifdef _WIN32
  #include <io.h>
  #ifndef PRIu64
    #define PRIu64 "I64u"
  #endif
#else
  #include <inttypes.h>
#endif

//The max size of an uint32_t is 10 characters + NULL
//The max size of an uint64_t is 20 characters + NULL
#define STRING_BUFFER_SIZE 32

<#-- ********************************************* -->
<#-- Generate "_get_enum_str()"                    -->
<#list getQueryParamEnums() as enumEntry>
    <#include "EnumToString.ftl">
</#list>

<#-- ******************************************************** -->
<#-- Generate all Optional query param init-request modifiers -->
<#include "modify_request.ftl"/>

<#-- ***************************************** -->
<#-- Generate all "InitRequests" from Requests -->
<#list getRequests() as requestEntry>
    <#include "../request-templates/InitRequest.ftl">
</#list>
