<#-- ********************************** -->
<#-- Generate "Delete Request" -->
<#--   Input: Source object             -->
<#-- ********************************** -->
<#list getRequests() as requestEntry>
${requestEntry.getRequestHelper().generateRequestFunctionSignature(requestEntry)} {

${requestEntry.getRequestHelper().generateParameterCheckingBlock(requestEntry)}

    return _internal_request_dispatcher(client, request, NULL, NULL, NULL, NULL, NULL);
}
</#list>
