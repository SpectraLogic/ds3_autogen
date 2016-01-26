<#-- ***************************************** -->
<#-- Generate all "InitRequests" from Requests -->
<#--   Input: Source object                    -->
<#-- ***************************************** -->
<#list getRequests() as requestEntry>
ds3_request* ds3_init_${requestEntry.getRequestHelper().getNameRootUnderscores(requestEntry.getName())}(const char* bucket_name) {
    return (ds3_request*) _common_request_init(HTTP_${requestEntry.getVerb()}, _build_path(${requestEntry.getPath()}));
}
</#list>
