<#assign maxFieldNameLen = "Headers"?length>
<#list payloadFields as field>
	<#if field.name?length gt maxFieldNameLen>
		<#assign maxFieldNameLen = field.name?length>
	</#if>
</#list>

type ${name} struct {
<#list payloadFields as field>
	${field.name?right_pad(maxFieldNameLen)} ${field.type}
</#list>
	${"Headers"?right_pad(maxFieldNameLen)} *http.Header
}
<#if parseResponseMethod?has_content>

${parseResponseMethod}
</#if>

func New${name}(webResponse WebResponse) (*${name}, error) {
	expectedStatusCodes := []int{${expectedCodes}}

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
