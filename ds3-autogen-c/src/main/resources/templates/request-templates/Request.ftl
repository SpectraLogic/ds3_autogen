<#-- ********************************** -->
<#-- Generate Request                   -->
<#--   Input: Request object            -->
<#-- ********************************** -->
${requestHelper.generateRequestFunctionSignature(requestEntry)} {

${requestHelper.generateParameterValidationBlock(requestEntry)}

    return _internal_request_dispatcher(client, request, NULL, NULL, NULL, NULL, NULL);
}
