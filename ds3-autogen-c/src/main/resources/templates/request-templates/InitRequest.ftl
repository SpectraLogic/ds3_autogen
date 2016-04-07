<#-- ***************************************** -->
<#-- Generate all "InitRequests" from Requests -->
<#--   Input: Source object                    -->
<#-- ***************************************** -->
${requestHelper.generateInitRequestFunctionSignature(requestEntry)} {
    return (ds3_request*) _common_request_init(HTTP_${requestEntry.getVerb()}, _build_path(${requestEntry.getBuildPathArgs()}));
}

