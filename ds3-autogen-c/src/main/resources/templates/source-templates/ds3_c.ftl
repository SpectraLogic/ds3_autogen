<#include "../CopyrightHeader.ftl"/>

<#include "source_includes.ftl"/>

<#include "source_pre_init_functions.ftl"/>

<#-- ***************************************** -->
<#-- Generate all "InitRequests" from Requests -->
<#list getRequests() as requestEntry>
    <#include "../request-templates/InitRequest.ftl">
</#list>
<#-- ***************************************** -->

<#include "source_post_init_pre_response_parsers.ftl"/>

<#-- ******************************************* -->
<#-- Generate all "EnumMatchers" from Enums      -->
<#list getEnums() as enumEntry>
    <#include "TypedefEnumMatcher.ftl">
</#list>
<#-- ******************************************* -->

<#-- ********************************************* -->
<#-- Generate all "ResponseParsers" that are used by arrayParsers -->
<#list getArrayStructs() as structEntry>
    <#include "ResponseParser.ftl">
</#list>

<#-- ********************************************* -->
<#-- Generate all "ArrayTypeParsers"               -->
<#list getArrayTypes() as arrayType>
${arrayType.getcTypeHelper().generateArrayMemberParser(arrayType)}
</#list>

<#-- ********************************************* -->
<#-- Generate remaining "ResponseParsers"          -->
<#list getStructs() as structEntry>
    <#include "ResponseParser.ftl">
</#list>

<#-- ********************************************* -->
<#-- Generate all "RequestFunctions" from Requests -->
<#list getRequests() as requestEntry>
    <#if (requestEntry.getClassification().toString() == "amazons3") && (requestEntry.getVerb().toString() == "HEAD")>
        <#include "../request-templates/HeadRequest.ftl"/>
    <#elseif requestEntry.getVerb().toString() == "POST">
        <#include "../request-templates/Request.ftl"/>
    <#elseif requestEntry.getVerb().toString() == "PUT">
        <#include "../request-templates/Request.ftl"/>
    <#elseif requestEntry.getVerb().toString() == "GET">
        <#include "../request-templates/Request.ftl"/>
    <#elseif requestEntry.getVerb().toString() == "DELETE">
        <#include "../request-templates/Request.ftl"/>
    </#if>
</#list>
<#-- ********************************************* -->

<#include "source_post_response_matchers.ftl"/>

<#-- *********************************************** -->
<#-- Generate all "StructFreeFunctions" from Structs -->
<#list getStructs() as structEntry>
    <#include "FreeStruct.ftl">
</#list>
<#-- *********************************************** -->
