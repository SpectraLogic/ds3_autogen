<#include "../common/copyright.ftl" />

package models

type ${name} Enum

const (
    <#list enumConstants as const>
    ${const} ${name} = 1 + iota
    </#list>
)

func (${name?uncap_first} *${name}) UnmarshalText(text []byte) error {
    var str string = string(bytes.ToUpper(text))
    switch str {
        <#list enumConstants as const>
        case "${const}": *${name?uncap_first} = ${const}
        </#list>
        default:
            *${name?uncap_first} = NONE
            return errors.New(fmt.Sprintf("Cannot marshal %s into ${name}", str))
    }
    return nil
}

func (${name?uncap_first} ${name}) String() (string, error) {
    switch ${name?uncap_first} {
        <#list enumConstants as const>
        case ${const}: return "${const}", nil
        </#list>
        case NONE: return "NONE", nil
        default: return "", errors.New(fmt.Sprintf("Invalid ${name} represented by: %d", ${name?uncap_first}))
    }
}
