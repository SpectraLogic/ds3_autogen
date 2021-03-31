type ${name} struct {
    ${payloadStruct}
    Headers *http.Header
}

${parseResponseMethod}

func New${name}(webResponse WebResponse) (*${name}, error) {
    defer webResponse.Body().Close()
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

