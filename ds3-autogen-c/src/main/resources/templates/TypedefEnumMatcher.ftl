<#-- ************************************************ -->
<#-- Generate all "TypedefEnumMatchers" from Enums    -->
<#--   Input: Header object                           -->
<#-- ************************************************ -->
<#list getEnums() as enumEntry>
static ${enumEntry.getName()} _match_${enumEntry.getName()}(const ds3_log* log, const xmlChar* text) {
${enumEntry.getEnumHelper().generateMatcher(enumEntry.getValues())}
}
</#list>
