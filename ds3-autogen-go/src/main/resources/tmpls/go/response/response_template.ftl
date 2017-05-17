<#include "../common/copyright.ftl" />

package models

import (
    "ds3/networking"
    <#list imports as import>
    "${import}"
    </#list>
)

type ${name} struct {
    ${payloadStruct}
}

func New${name}(webResponse networking.WebResponse) (*${name}, error) {
    expectedStatusCodes := []int { ${expectedCodes} }

    switch code := webResponse.StatusCode(); code {
    <#list responseCodes as code>
    case ${code.code}:
        ${code.parseResponse}
    </#list>
    default:
        return nil, buildBadStatusCodeError(webResponse, expectedStatusCodes)
    }
}
