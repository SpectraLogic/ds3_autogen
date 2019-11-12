type ${name} struct {
    ${payloadStruct}
    Headers *http.Header
}

${parseResponseMethod}

func New${name}(webResponse WebResponse) (*${name}, error) {
    expectedStatusCodes := []int { ${expectedCodes} }

    switch code := webResponse.StatusCode(); code {
    <#list responseCodes as code>
    case ${code.code}:
        ${code.parseResponse}
    </#list>
    default:
        defer webResponse.Body().Close()
        return nil, buildBadStatusCodeError(webResponse, expectedStatusCodes)
    }
}

