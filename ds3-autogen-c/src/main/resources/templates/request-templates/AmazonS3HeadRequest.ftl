<#-- ******************************** -->
<#-- Generate "AmazonS3 HEAD Request" -->
<#--   Input: Source object           -->
<#-- ******************************** -->
<#list getRequests() as requestEntry>
    <#if (requestEntry.getClassification().toString() == "amazons3") && (requestEntry.getVerb().toString() == "HEAD")>
ds3_error* s3_${requestEntry.getRequestHelper().getNameRootUnderscores(requestEntry.getName())}(const ds3_client* client, const ds3_request* request) {
    ds3_error* error = NULL;
    ds3_string_multimap* return_headers = NULL;
    ds3_metadata* metadata = NULL;

    <#if requestEntry.getRequestHelper().containsKey(requestEntry.getRequiredArguments(), "objectName")>
    <#--
    <#if requestEntry.getRequiredArguments().containsKey("objectName")>
    -->
    if (num_slashes < 2 || ((num_slashes == 2) && ('/' == request->path->value[request->path->size-1]))) {
        return ds3_create_error(DS3_ERROR_MISSING_ARGS, "The object name parameter is required.");
    } else if (g_ascii_strncasecmp(request->path->value, "//", 2) == 0) {
        return ds3_create_error(DS3_ERROR_MISSING_ARGS, "The bucket name parameter is required.");
    }
    <#elseif requestEntry.getRequestHelper().containsKey(requestEntry.getRequiredArguments(), "bucketName")>
    <#--
    <#elseif requestEntry.getRequiredArguments().containsKey("bucketName")>
    -->
    if (g_ascii_strncasecmp(request->path->value, "//", 2) == 0) {
        return ds3_create_error(DS3_ERROR_MISSING_ARGS, "The bucket name parameter is required.");
    }
    </#if>

    error = _internal_request_dispatcher(client, request, NULL, NULL, NULL, NULL, &return_headers);
    if (error == NULL) {
        metadata = _init_metadata(return_headers);
        *_metadata = metadata;
        ds3_string_multimap_free(return_headers);
    }

    return error;
}
    </#if>
</#list>

