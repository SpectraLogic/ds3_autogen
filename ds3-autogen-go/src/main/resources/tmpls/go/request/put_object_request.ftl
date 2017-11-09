<#include "request_header.ftl" />

import (
    "strings"
)

const ( AMZ_META_HEADER = "x-amz-meta-" )

<#include "request_body.ftl" />

<#include "with_checksum.ftl" />

<#include "with_headers.ftl" />
