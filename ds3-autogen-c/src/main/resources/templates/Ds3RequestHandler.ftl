<#include "CopyrightHeader.ftl"/>

 #include "ds3.h"

 ds3_error* ds3_${request.name}(const ds3_client* client, const ds3_request* request<#list request.ds3ResponseCodes><#items as ds3ResponseCode><#if ds3ResponseCode.type != "null">, ds3_<$request.name>_response** _response</#if></#items></#list> ) {
 
 }
