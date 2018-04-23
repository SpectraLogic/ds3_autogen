type Range struct {
    Start int64
    End int64
}

<#include "request_body.ftl" />

<#include "with_checksum.ftl" />

func (${name?uncap_first} *${name}) WithRanges(ranges ...Range) *${name} {
    var rangeElements []string
    for _, cur := range ranges {
        rangeElements = append(rangeElements, fmt.Sprintf("%d-%d", cur.Start, cur.End))
    }
    ${name?uncap_first}.Metadata["Range"] = fmt.Sprintf("bytes=%s", strings.Join(rangeElements[:], ","))
    return ${name?uncap_first}
}
