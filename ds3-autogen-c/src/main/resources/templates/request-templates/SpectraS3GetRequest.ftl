<#-- ********************************** -->
<#-- Generate "SpectraS3 Get Request" -->
<#--   Input: Source object             -->
<#-- ********************************** -->
<#list getRequests() as requestEntry>
    <#if (requestEntry.getClassification().toString() == "spectrads3") && (requestEntry.getVerb().toString() == "GET")>
ds3_error* ${requestEntry.getRequestHelper().getNameRootUnderscores(requestEntry.getName())}(const ds3_client* client, const ds3_request* request, const ${requestEntry.getResponseType()}** response) { <#-- add response type param -->
    <#if requestEntry.isResourceIdRequired()>
    int num_slashes = num_chars_in_ds3_str(request->path, '/');
    if (num_slashes < 2 || ((num_slashes == 2) && ('/' == request->path->value[request->path->size-1]))) {
        return ds3_create_error(DS3_ERROR_MISSING_ARGS, "The resource id parameter is required.");
    } else if (g_ascii_strncasecmp(request->path->value, "//", 2) == 0) {
        return ds3_create_error(DS3_ERROR_MISSING_ARGS, "The resource type parameter is required.");
    }
    <#elseif requestEntry.isResourceRequired()>
    if (g_ascii_strncasecmp(request->path->value, "//", 2) == 0) {
        return ds3_create_error(DS3_ERROR_MISSING_ARGS, "The resource type parameter is required.");
    }
    </#if>


    <#if requestEntry.hasResponsePayload()>
    return _parse_${requestEntry.getResponseType()}(client->log, response);
    <#else>
    return _internal_request_dispatcher(client, request, NULL, NULL, NULL, NULL);
    </#if>
}
    </#if>
</#list>
