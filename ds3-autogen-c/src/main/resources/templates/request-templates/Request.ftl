<#-- ********************************** -->
<#-- Generate Request                   -->
<#--   Input: Request object            -->
<#-- ********************************** -->
${requestHelper.generateRequestFunctionSignature(requestEntry)} {

${requestHelper.generateParameterCheckingBlock(requestEntry)}

    <#if requestEntry.hasResponsePayload()>
    return _parse_${requestEntry.getResponseType()}(client, response);
    <#else>
    return _internal_request_dispatcher(client, request, NULL, NULL, NULL, NULL, NULL);
    </#if>
}