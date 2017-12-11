<#include "request_header.ftl" />

import (
    "fmt"
)

type rangeHeader struct {
    start, end int
}

<#include "request_body.ftl" />

<#include "with_checksum.ftl" />

func (${name?uncap_first} *${name}) WithRange(start, end int) *${name} {
    ${name?uncap_first}.Metadata["Range"] = fmt.Sprintf("bytes=%d-%d", start, end)
    return ${name?uncap_first}
}
