<#include "../common/copyright.ftl" />

package models

import (
    "errors"
    "fmt"
    "bytes"
    "log"
)

type ${name} Enum

const (
    <#list enumConstants as const>
    ${enumPrefix}${const} ${name} = 1 + iota
    </#list>
    NONE ChecksumType = 1 + iota
)

<#include "enum_unmarshal_to_string.ftl" />
