<#include "../CopyrightHeader.ftl"/>

<#include "source_includes.ftl"/>

<#include "source_pre_init_functions.ftl"/>

<#-- ***************************************** -->
<#-- Generate all "InitRequests" from Requests -->
<#list getRequests() as requestEntry>
    <#include "AmazonS3InitRequestHandler.ftl">
</#list>
<#-- ***************************************** -->

<#include "source_post_init_pre_response_parsers.ftl"/>

<#-- ******************************************* -->
/////////////////////////////////////////////////////////
// Generate all "StructParsers" from ArrayStructs
//////////////////////////////////////////////////////////
<#list getArrayStructs() as structEntry>
    <#include "../ResponseParser.ftl">
</#list>
<#-- ******************************************* -->

<#-- ******************************************* -->
/////////////////////////////////////////////////////////
// Generate all "ArrayStructMemberParsers" from Structs
//////////////////////////////////////////////////////////
<#list getArrayTypes() as arrayType>
${arrayType.getcTypeHelper().generateArrayMemberParser(arrayType)}
</#list>
<#-- ******************************************* -->

<#-- ******************************************* -->
/////////////////////////////////////////////////////////
// Generate all "ResponseParsers" from Structs
//////////////////////////////////////////////////////////
<#list getStructs() as structEntry>
    <#include "../ResponseParser.ftl">
</#list>
<#-- ******************************************* -->

<#-- ********************************************* -->
<#-- Generate all "RequestFunctions" from Requests -->
<#include "../request-templates/HeadRequest.ftl"/>
<#include "../request-templates/GetRequest.ftl"/>
<#include "../request-templates/DeleteRequest.ftl"/>
<#-- ********************************************* -->

<#-- ******************************************* -->
<#-- Generate all "EnumMatchers" from Enums      -->
<#list getEnums() as enumEntry>
    <#include "../TypedefEnumMatcher.ftl">
</#list>
<#-- ******************************************* -->

<#include "source_post_response_matchers.ftl"/>

<#-- *********************************************** -->
<#-- Generate all "StructFreeFunctions" from Structs -->
<#list getStructs() as structEntry>
    <#include "../FreeStruct.ftl">
</#list>
<#-- *********************************************** -->
