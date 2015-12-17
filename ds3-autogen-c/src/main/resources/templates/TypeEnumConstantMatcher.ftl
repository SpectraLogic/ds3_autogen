
<#include "CopyrightHeader.ftl"/>

static ds3_${getNameUnderscores()} _match_ds3_${getNameUnderscores()}(const ds3_log* log, const xmlChar* text) {
${generateMatcher()}
}