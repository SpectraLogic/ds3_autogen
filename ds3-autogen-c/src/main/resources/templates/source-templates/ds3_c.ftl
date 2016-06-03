<#include "../CopyrightHeader.ftl"/>

<#include "source_includes.ftl"/>

<#-- ********************************************* -->
<#-- Generate "_get_enum_str()"                    -->
<#list getQueryParamEnums() as enumEntry>
    <#include "EnumToString.ftl">
</#list>

<#include "source_pre_init_functions.ftl"/>

<#-- ***************************************** -->
<#-- Generate all "InitRequests" from Requests -->
<#list getRequests() as requestEntry>
    <#include "../request-templates/InitRequest.ftl">
</#list>

<#include "source_post_init_pre_response_parsers.ftl"/>

<#-- ******************************************* -->
<#-- Generate all "EnumMatchers" from Enums      -->
<#list getEnums() as enumEntry>
    <#include "TypedefEnumMatcher.ftl">
</#list>

//************ STRUCT PARSERS **************
<#list getStructs() as structEntry>
    <#include "ResponseParser.ftl">
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

<#-- ********************************************* -->
<#-- Special cased requests                        -->
<#include "../request-templates/HeadBucketRequest.ftl"/>
<#include "../request-templates/HeadObjectRequest.ftl"/>
<#include "../request-templates/GetObjectWithMetadataRequest.ftl"/>

<#-- ********************************************* -->
<#-- Generate all "RequestFunctions" from Requests -->
<#list getRequests() as requestEntry>
    <#if (requestEntry.getClassification().toString() == "amazons3")
      && (requestEntry.getVerb().toString() == "HEAD")>
         #-- SKIP - HARD CODED SPECIAL CASES ABOVE -->
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

<#include "source_post_response_matchers.ftl"/>

<#-- *********************************************** -->
<#-- Generate all "StructFreeFunctions" from Structs -->
<#list getStructs() as structEntry>
    <#include "FreeStruct.ftl">
</#list>

