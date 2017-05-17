func (${name?uncap_first} *${name}) WithMetaData(key string, value string) *${name} {
    if strings.HasPrefix(strings.ToLower(key), AMZ_META_HEADER) {
        ${name?uncap_first}.headers.Add(strings.ToLower(key), value)
    } else {
        ${name?uncap_first}.headers.Add(strings.ToLower(AMZ_META_HEADER + key), value)
    }
    return ${name?uncap_first}
}

func (${name?uncap_first} *${name}) Header() *http.Header {
    return ${name?uncap_first}.headers
}
