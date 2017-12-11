func (${name?uncap_first} *${name}) UnmarshalText(text []byte) error {
    var str string = string(bytes.ToUpper(text))
    switch str {
        case "": *${name?uncap_first} = UNDEFINED
        <#list enumConstants as const>
        case "${const}": *${name?uncap_first} = ${enumPrefix}${const}
        </#list>
        default:
            *${name?uncap_first} = UNDEFINED
            return errors.New(fmt.Sprintf("Cannot marshal '%s' into ${name}", str))
    }
    return nil
}

func (${name?uncap_first} ${name}) String() string {
    switch ${name?uncap_first} {
        <#list enumConstants as const>
        case ${enumPrefix}${const}: return "${const}"
        </#list>
        default:
            log.Printf("Error: invalid ${name} represented by '%d'", ${name?uncap_first})
            return ""
    }
}
