<#include "../CopyrightHeader.ftl"/>

<#include "source_includes.ftl"/>

<#-- ********************************************* -->
<#-- Generate "_get_enum_str()"                    -->
<#list getQueryParamEnums() as enumEntry>
    <#include "EnumToString.ftl">
</#list>

<#include "metadata.ftl"/>
<#include "client.ftl"/>
<#include "modify_request.ftl"/>

<#-- ***************************************** -->
<#-- Generate all "InitRequests" from Requests -->
<#list getRequests() as requestEntry>
    <#include "../request-templates/InitRequest.ftl">
</#list>

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
    <#if structEntry.isArrayMember()>
        <#include "ResponseParser.ftl">
        <#include "StructArrayParser.ftl">
    <#elseif structEntry.isEmbedded()>
        <#include "ResponseParser.ftl">
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

<#include "free_custom_types.ftl"/>

<#-- *********************************************** -->
<#-- Generate all "StructFreeFunctions" from Structs -->
<#list getStructs() as structEntry>
    <#include "FreeStruct.ftl">
</#list>

<#include "file_utils.ftl"/>
