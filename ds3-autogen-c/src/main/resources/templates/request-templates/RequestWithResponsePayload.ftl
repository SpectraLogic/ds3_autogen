<#-- **************************************** -->
<#-- Generate a Request with response payload -->
<#--   Input: Request object                  -->
<#-- **************************************** -->
${requestHelper.generateRequestFunctionSignature(requestEntry)} {
    ds3_error* error;
    GByteArray* xml_blob;
<#if requestEntry.hasResponsePayload() && requestEntry.getResponseType() == "ds3_str">
    ds3_str* _response;
</#if>

${requestHelper.generateParameterValidationBlock(requestEntry)}

    xml_blob = g_byte_array_new();
    error = _internal_request_dispatcher(client, request, xml_blob, ds3_load_buffer, NULL, NULL, NULL);
    if (error != NULL) {
        g_byte_array_free(xml_blob, TRUE);
        return error;
    }

<#if requestEntry.hasResponsePayload() && requestEntry.getResponseType() == "ds3_str">
    _response = ds3_str_init_with_size((char*)xml_blob->data, xml_blob->len);
    g_byte_array_free(xml_blob, TRUE);

    *response = _response;
    return error;
<#else>
    return _parse_top_level_${requestEntry.getResponseType()}(client, request, response, xml_blob);
</#if>
}
