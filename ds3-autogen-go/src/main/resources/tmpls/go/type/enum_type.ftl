type ${name} Enum

const (
    <#list enumConstants as const>
    ${enumPrefix}${const} ${name} = 1 + iota
    </#list>
)

<#include "enum_unmarshal_to_string.ftl" />

func new${name}FromContent(content []byte, aggErr *AggregateError) *${name} {
    if len(content) == 0 {
        // no value
        return nil
    }
    result := new(${name})
    parseEnum(content, result, aggErr)
    return result
}
