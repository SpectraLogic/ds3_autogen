<#include "../CopyrightHeader.ftl"/>

#include <glib.h>
#include <stdlib.h>
#include <stdbool.h>
#include <libxml/parser.h>

#include "ds3.h"
#include "ds3_net.h"
#include "ds3_request.h"
#include "ds3_string_multimap_impl.h"
#include "ds3_utils.h"

#ifdef _WIN32
#include <io.h>
#else
#include <inttypes.h>
#endif


//The max size of an uint32_t should be 10 characters + NULL
static const char UNSIGNED_LONG_BASE_10[] = "4294967296";
static const unsigned int UNSIGNED_LONG_BASE_10_STR_LEN = sizeof(UNSIGNED_LONG_BASE_10) + 1;
//The max size of an uint64_t should be 20 characters + NULL
static const char UNSIGNED_LONG_LONG_BASE_10[] = "18446744073709551615";
static const unsigned int UNSIGNED_LONG_LONG_BASE_10_STR_LEN = sizeof(UNSIGNED_LONG_LONG_BASE_10) + 1;


<#include "metadata.ftl"/>
<#include "xml_helpers.ftl"/>
<#include "internal_request_processors.ftl"/>

<#-- ******************************************* -->
<#-- Generate all "EnumMatchers" from Enums      -->
<#list getEnums() as enumEntry>
    <#if enumEntry.requiresMatcher()>
        <#include "TypedefEnumMatcher.ftl">
    </#if>
</#list>

//************ STRUCT PARSERS **************
<#list getStructs() as structEntry>
    <#if structEntry.isEmbedded()>
        <#include "ResponseParser.ftl">
    </#if>
    <#if structEntry.isArrayMember()>
        <#include "StructArrayParser.ftl">
    </#if>
</#list>

//************ TOP LEVEL STRUCT PARSERS **************
<#list getStructs() as structEntry>
    <#if structEntry.isTopLevel()>
        <#include "ResponseParserTopLevel.ftl">
    </#if>
</#list>
<#include "parse_paging_headers.ftl">


<#-- ********************************************* -->
<#-- Generate all "RequestFunctions" from Requests -->
<#list getRequests() as requestEntry>
    <#if requestEntry.getName() == "ds3_head_bucket_request">
        <#include "../request-templates/HeadBucketRequest.ftl"/>
    <#elseif requestEntry.getName() == "ds3_head_object_request">
        <#include "../request-templates/HeadObjectRequest.ftl"/>
    <#elseif requestEntry.getName() == "ds3_get_object_request">
        <#include "../request-templates/GetObjectRequest.ftl"/>
        <#include "../request-templates/GetObjectWithMetadataRequest.ftl"/>
    <#elseif (requestEntry.hasRequestPayload() == true)
          && (requestEntry.hasResponsePayload() == true)>
        <#include "../request-templates/RequestWithRequestAndResponsePayload.ftl"/>
    <#elseif requestEntry.hasRequestPayload()>
        <#include "../request-templates/RequestWithRequestPayload.ftl"/>
    <#elseif requestEntry.hasResponsePayload()>
        <#include "../request-templates/RequestWithResponsePayload.ftl"/>
    <#else>
        <#include "../request-templates/Request.ftl"/>
    </#if>
</#list>
