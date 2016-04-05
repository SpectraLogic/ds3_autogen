<#-- ********************************** -->
<#-- Generate "Get Request" -->
<#--   Input: Source object             -->
<#-- ********************************** -->
<#list getRequests() as requestEntry>
${requestEntry.getRequestHelper().generateRequestFunctionSignature(requestEntry)} {

${requestEntry.getRequestHelper().generateParameterCheckingBlock(requestEntry)}

    <#if requestEntry.hasResponsePayload()>
    return _parse_${requestEntry.getResponseType()}(client->log, response);
    <#else>
    return _internal_request_dispatcher(client, request, NULL, NULL, NULL, NULL);
    </#if>
}
</#list>
