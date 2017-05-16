<#include "request_header.ftl" />

type rangeHeader struct {
    start, end int
}

<#include "request_body.ftl" />

<#include "with_checksum.ftl" />

<#include "stream_no_content.ftl" />

func (${name?uncap_first} *${name}) WithRange(start, end int) *${name} {
    ${name?uncap_first}.rangeHeader = &rangeHeader{start, end}
    return ${name?uncap_first}
}

func (${name?uncap_first} *${name}) Header() *http.Header {
    if ${name?uncap_first}.rangeHeader == nil {
        return &http.Header{}
    } else {
        rng := fmt.Sprintf("bytes=%d-%d", ${name?uncap_first}.rangeHeader.start, ${name?uncap_first}.rangeHeader.end)
        return &http.Header{ "Range": []string{ rng } }
    }
}
