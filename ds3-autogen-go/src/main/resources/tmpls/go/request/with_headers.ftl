func (${name?uncap_first} *PutObjectRequest) WithMetaData(key string, values ...string) interface{} {
    if strings.HasPrefix(strings.ToLower(key), AMZ_META_HEADER) {
        ${name?uncap_first}.Metadata[key] = strings.Join(values, ",")
    } else {
        ${name?uncap_first}.Metadata[strings.ToLower(AMZ_META_HEADER + key)] = strings.Join(values, ",")
    }
    return ${name?uncap_first}
}
