<#-- ******************************** -->
<#-- Generate "AmazonS3 HEAD Request" -->
<#--   Input: Head Request object     -->
<#-- ******************************** -->
${requestHelper.generateRequestFunctionSignature(requestEntry)} {
    ds3_error* error = NULL;
    ds3_string_multimap* return_headers = NULL;
    ds3_metadata* metadata = NULL;

${requestHelper.generateParameterValidationBlock(requestEntry)}

    error = _internal_request_dispatcher(client, request, NULL, NULL, NULL, NULL, &return_headers);
    if (error == NULL) {
        metadata = _init_metadata(return_headers);
        *_response = metadata;
        ds3_string_multimap_free(return_headers);
    }

    return error;
}

