
<#include "CopyrightHeader.ftl"/>

static ds3_${getChelper().getTypeHelper().getNameUnderscores(name)} _match_ds3_${getChelper().getTypeHelper().getNameUnderscores(name)}(const ds3_log* log, const xmlChar* text) {
${getChelper().getTypeHelper().generateMatcher(getEnumConstants())}
}