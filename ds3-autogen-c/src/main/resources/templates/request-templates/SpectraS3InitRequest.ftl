<#-- ***************************************** -->
<#-- Generate all "InitRequests" from Requests -->
<#--   Input: Source object                    -->
<#-- ***************************************** -->
<#list getRequests() as requestEntry>
ds3_request* init_${requestEntry.getRequestHelper().getNameRootUnderscores(requestEntry.getName())}(${requestEntry.getRequestHelper().getSpectraS3InitParams(requestEntry.isResourceIdRequired())}) {
    return (ds3_request*) _common_request_init(HTTP_${requestEntry.getVerb()}, _build_path(${requestEntry.getBuildPathArgs()}));
}
</#list>
