<#include "../CopyrightHeader.ftl"/>

#include <glib.h>
#include <stdlib.h>
#include <stdio.h>
#include <libxml/parser.h>
#include <inttypes.h>

#include "ds3.h"
#include "ds3_request.h"
#include "ds3_net.h"

//The max size of an uint32_t should be 10 characters + NULL
static const char UNSIGNED_LONG_BASE_10[] = "4294967296";
static const unsigned int UNSIGNED_LONG_BASE_10_STR_LEN = sizeof(UNSIGNED_LONG_BASE_10) + 1;
//The max size of an uint64_t should be 20 characters + NULL
static const char UNSIGNED_LONG_LONG_BASE_10[] = "18446744073709551615";
static const unsigned int UNSIGNED_LONG_LONG_BASE_10_STR_LEN = sizeof(UNSIGNED_LONG_LONG_BASE_10) + 1;

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
