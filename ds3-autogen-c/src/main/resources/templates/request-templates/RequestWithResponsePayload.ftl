<#-- **************************************** -->
<#-- Generate a Request with response payload -->
<#--   Input: Request object                  -->
<#-- **************************************** -->
${requestHelper.generateRequestFunctionSignature(requestEntry)} {
    ds3_error* error;
    GByteArray* xml_blob;
${requestHelper.generateParameterValidationBlock(requestEntry)}

    xml_blob = g_byte_array_new();
    error = _internal_request_dispatcher(client, request, xml_blob, ds3_load_buffer, NULL, NULL, NULL);
    if (error != NULL) {
        g_byte_array_free(xml_blob, TRUE);
        return error;
    }

    return _parse_get_service_response(client, request, _response, xml_blob);
}
