<#-- *************************************** -->
<#-- Generate a Request with request payload -->
<#--   Input: Request object                 -->
<#-- *************************************** -->
${requestHelper.generateRequestFunctionSignature(requestEntry)} {
<#if requestHelper.getRequestObjectListType(requestEntry.getName()) != "DATA">
    ds3_error* error;
    ds3_xml_send_buff send_buff;
</#if>

${requestHelper.generateParameterValidationBlock(requestEntry)}

<#if requestHelper.getRequestObjectListType(requestEntry.getName()) == "DATA">
    return _internal_request_dispatcher(client, request, NULL, NULL, user_data, callback, NULL);
<#else>
    error = _init_request_payload(request, &send_buff, ${requestHelper.getRequestObjectListType(requestEntry.getName())});
    if (error != NULL) return error;

    error = _internal_request_dispatcher(client, request, NULL, NULL, (void*) &send_buff, _ds3_send_xml_buff, NULL);

    // Clean up the data sent to the server
    xmlFree(send_buff.buff);

    return error;
</#if>
}
