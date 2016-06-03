<#-- ************************************************** -->
<#-- Generate a Request with request & response payload -->
<#--   Input: Request object                            -->
<#-- ************************************************** -->
${requestHelper.generateRequestFunctionSignature(requestEntry)} {
    ds3_error* error;
    ds3_xml_send_buff send_buff;
    GByteArray* xml_blob;

${requestHelper.generateParameterValidationBlock(requestEntry)}

    error = _init_request_payload(request, &send_buff, ${requestHelper.getRequestObjectListType(requestEntry.getName())});
    if (error != NULL) return error;

    xml_blob = g_byte_array_new();
    error = _internal_request_dispatcher(client, request, xml_blob, ds3_load_buffer, (void*) &send_buff, _ds3_send_xml_buff, NULL);

    // Clean up the data sent to the server
    xmlFree(send_buff.buff);

    if (error != NULL) {
        g_byte_array_free(xml_blob, TRUE);
        return error;
    }

<#if requestEntry.hasResponsePayload() && requestEntry.getResponseType() == "ds3_str">
    response->value = (char*)xml_blob->data;
    response->size = xml_blob->len;
    g_byte_array_free(xml_blob, FALSE);
    return error;
<#else>
    return _parse_top_level_${requestEntry.getResponseType()}(client, request, response, xml_blob);
</#if>
}
