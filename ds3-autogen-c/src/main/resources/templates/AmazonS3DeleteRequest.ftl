<#-- ********************************** -->
<#-- Generate "AmazonS3 Delete Request" -->
<#--   Input: Source object             -->
<#-- ********************************** -->
<#list getRequests() as requestEntry>
ds3_error* s3_${requestEntry.getRequestHelper().getNameRootUnderscores(requestEntry.getName())}(const ds3_client* client, const ds3_request* request) {
    ds3_error* error = NULL;

    <#if requestEntry.isResourceIdRequired()>
    if (num_slashes < 2 || ((num_slashes == 2) && ('/' == request->path->value[request->path->size-1]))) {
        return ds3_create_error(DS3_ERROR_MISSING_ARGS, "The object name parameter is required.");
    } else if (g_ascii_strncasecmp(request->path->value, "//", 2) == 0) {
        return ds3_create_error(DS3_ERROR_MISSING_ARGS, "The bucket name parameter is required.");
    }
    <#elseif requestEntry.isResourceRequired()>
    if (g_ascii_strncasecmp(request->path->value, "//", 2) == 0) {
        return ds3_create_error(DS3_ERROR_MISSING_ARGS, "The bucket name parameter is required.");
    }
    </#if>

    error = _internal_request_dispatcher(client, request, NULL, NULL, NULL, NULL, NULL);

    return error;
}
</#list>
