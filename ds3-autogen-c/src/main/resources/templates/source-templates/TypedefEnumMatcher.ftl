<#-- ************************************************ -->
<#-- Generate all "TypedefEnumMatchers" from Enums    -->
<#--   Input: Header object                           -->
<#-- ************************************************ -->
static ${enumEntry.getName()} _match_${enumEntry.getName()}(const ds3_log* log, const xmlChar* text) {
${enumHelper.generateMatcher(enumEntry.getValues())}
}
