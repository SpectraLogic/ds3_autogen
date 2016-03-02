<#-- ********************************** -->
<#-- Generate "SpectraS3 Get Request" -->
<#--   Input: Source object             -->
<#-- ********************************** -->
<#list getRequests() as requestEntry>
    <#if (requestEntry.getClassification().toString() == "spectrads3") && (requestEntry.getAction().toString() == "GET")>
ds3_error* ${requestEntry.getRequestHelper().getNameRootUnderscores(requestEntry.getName())}(const ds3_client* client, const ds3_request* request) { <#-- add response type param -->
    xmlDocPtr doc;
    xmlNodePtr root;
    ds3_error* error;

    <#if requestEntry.isResourceIdRequired()>
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

    error = _get_request_xml_nodes(client, request, &doc, &root, "ListAllMyBucketsResult"); <#-- determine result type using NameToMarshal Type property -->
    if (error != NULL) {
        return error;
    }

    *_response = _parse_get_service_response(client->log, doc, root); <#-- get response type -->
    xmlFreeDoc(doc);
    return NULL;
}
    </#if>
</#list>
