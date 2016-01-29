<#include "CopyrightHeader.ftl"/>

<#include "source_includes.ftl"/>

<#include "source_pre_init_functions.ftl"/>

<#-- ***************************************** -->
<#-- Generate all "InitRequests" from Requests -->
<#-- Not fully implemented yet
<#list getRequests() as requestEntry>
    <#include "AmazonS3InitRequestHandler.ftl">
</#list>
-->
// SKIPPING GENERATION OF REQUESTS
<#-- ***************************************** -->

<#include "source_post_init_pre_response_parsers.ftl"/>

<#-- ******************************************* -->
<#-- Generate all "ResponseParsers" from Structs -->
<#list getStructs() as structEntry>
    <#include "ResponseParser.ftl">
</#list>
<#-- ******************************************* -->

<#-- ********************************************* -->
<#-- Generate all "RequestFunctions" from Requests -->
<#-- Not fully implemented yet
<#list getRequests() as requestEntry>
    <#include "ResponseParser.ftl">
</#list>
-->
// SKIPPING GENERATION OF REQUESTS
<#-- ********************************************* -->

<#-- ******************************************* -->
<#-- Generate all "EnumMatchers" from Enums      -->
<#list getEnums() as enumEntry>
    <#include "TypedefEnumMatcher.ftl">
</#list>
<#-- ******************************************* -->

<#include "source_post_response_matchers.ftl"/>

<#-- *********************************************** -->
<#-- Generate all "StructFreeFunctions" from Structs -->
<#list getStructs() as structEntry>
    <#include "FreeStruct.ftl">
</#list>
<#-- *********************************************** -->
