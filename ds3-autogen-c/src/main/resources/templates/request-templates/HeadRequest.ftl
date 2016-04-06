<#-- ******************************** -->
<#-- Generate "AmazonS3 HEAD Request" -->
<#--   Input: Head Request object     -->
<#-- ******************************** -->
${requestEntry.getRequestHelper().generateRequestFunctionSignature(requestEntry)} {
    ds3_error* error = NULL;
    ds3_string_multimap* return_headers = NULL;
    ds3_metadata* metadata = NULL;

${requestEntry.getRequestHelper().generateParameterCheckingBlock(requestEntry)}

    error = _internal_request_dispatcher(client, request, NULL, NULL, NULL, NULL, &return_headers);
    if (error == NULL) {
        metadata = _init_metadata(return_headers);
        *_metadata = metadata;
        ds3_string_multimap_free(return_headers);
    }

    return error;
}
