<#-- ***************************************** -->
<#-- Generate all "InitRequests"               -->
<#--   Input: Request object                   -->
<#-- ***************************************** -->
${requestHelper.generateInitRequestFunctionSignature(requestEntry)} {
    struct _ds3_request* request = _common_request_init(HTTP_${requestEntry.getVerb()}, _build_path(${requestEntry.getBuildPathArgs()}));
<#list requestEntry.getRequiredQueryParams() as reqParam>
${parameterHelper.generateInitParamBlock(reqParam)}
</#list>
<#list requestEntry.getOptionalQueryParams() as reqParam>
${parameterHelper.generateInitParamBlock(reqParam)}
</#list>

    return (ds3_request*) request;
}