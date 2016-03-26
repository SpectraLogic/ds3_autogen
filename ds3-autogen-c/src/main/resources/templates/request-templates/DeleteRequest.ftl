<#-- ********************************** -->
<#-- Generate "Delete Request" -->
<#--   Input: Delete Request object     -->
<#-- ********************************** -->
${requestEntry.getRequestHelper().generateRequestFunctionSignature(requestEntry)} {

${requestEntry.getRequestHelper().generateParameterCheckingBlock(requestEntry)}

    return _internal_request_dispatcher(client, request, NULL, NULL, NULL, NULL, NULL);
}
