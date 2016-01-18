
<#include "CopyrightHeader.ftl"/>

static ds3_${getTypeHelper().getNameUnderscores(name)} _match_ds3_${getTypeHelper().getNameUnderscores(name)}(const ds3_log* log, const xmlChar* text) {
${getTypeHelper().generateMatcher(getEnumConstants())}
}