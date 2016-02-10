<#-- *********************************************** -->
<#-- Generate all "Amazon S3 Requests" from Requests -->
<#--   Input: Source object                          -->
<#-- *********************************************** -->
<#list getRequests() as requestEntry>
ds3_error* s3_${requestEntry.getRequestHelper().getNameRootUnderscores(requestEntry.getName())}(const ds3_client* client, const ds3_request* request) {
    <#if helper.containsArgument(requestEntry.getRequiredArguments(), "bucketName")>
    if(g_ascii_strncasecmp(request->path->value, "//", 2) == 0){
        return ds3_create_error(DS3_ERROR_MISSING_ARGS, "The bucket name parameter is required.");
    }
    </#if>
    return _internal_request_dispatcher(client, request, NULL, NULL, NULL, NULL);
}
</#list>
