<#-- ************************************************ -->
<#-- Generate all "TypedefEnumMatchers" from Enums    -->
<#--   Input: Header object                           -->
<#-- ************************************************ -->
<#list getEnums() as enumEntry>
static ds3_${enumEntry.getEnumHelper().getNameUnderscores(enumEntry.getName())} _match_ds3_${enumEntry.getEnumHelper().getNameUnderscores(enumEntry.getName())}(const ds3_log* log, const xmlChar* text) {
${enumEntry.getEnumHelper().generateMatcher(enumEntry.getValues())}
}
</#list>
