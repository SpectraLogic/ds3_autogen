type ${name} Enum

const (
    <#list enumConstants as const>
    ${enumPrefix}${const} ${name} = 1 + iota
    </#list>
)

<#include "enum_unmarshal_to_string.ftl" />
