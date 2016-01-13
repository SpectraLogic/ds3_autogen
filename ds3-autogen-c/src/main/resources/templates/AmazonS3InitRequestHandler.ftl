<#include "CopyrightHeader.ftl"/>

#include "ds3.h"

ds3_request* ds3_init_${getRequestHelper().getNameRootUnderscores(name)}(const char* bucket_name) {
    return (ds3_request*) _common_request_init(HTTP_${getVerb()}, _build_path(${getPath()}));
}
