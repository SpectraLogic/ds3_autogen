<#-- *************************************** -->
<#-- Generate a Request with request payload -->
<#--   Input: Request object                 -->
<#-- *************************************** -->
${requestHelper.generateRequestFunctionSignature(requestEntry)} {
    ds3_error* error;
    ds3_xml_send_buff send_buff;
    GByteArray* xml_blob;

${requestHelper.generateParameterValidationBlock(requestEntry)}

    xml_blob = g_byte_array_new();
    error = _internal_request_dispatcher(client, request, xml_blob, ds3_load_buffer, (void*) &send_buff, _ds3_send_xml_buff, NULL);

    // Clean up the data sent to the server
    xmlFree(send_buff.buff);
    g_byte_array_free(xml_blob, TRUE);

    return error;
}
