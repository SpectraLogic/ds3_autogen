<#-- ***************************************** -->
<#-- Generate all "InitRequests"               -->
<#--   Input: Request object                   -->
<#-- ***************************************** -->
${requestHelper.generateInitRequestFunctionSignature(requestEntry)} {
    struct _ds3_request* request = _common_request_init(HTTP_${requestEntry.getVerb()}, _build_path(${requestEntry.getBuildPathArgs()}));
<#list requestEntry.getRequiredQueryParams() as reqParam>
${requestHelper.generateInitParamBlock(reqParam, true)}
</#list>
<#list requestEntry.getOptionalQueryParams() as reqParam>
${requestHelper.generateInitParamBlock(reqParam, false)}
</#list>

    return (ds3_request*) request;
}