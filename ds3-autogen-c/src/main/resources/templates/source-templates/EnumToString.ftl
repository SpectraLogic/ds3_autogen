<#-- ************************************************ -->
<#-- Generate "_get_{enum_name}_str" from Enums,      -->
<#--          only for enums used as query params     -->
<#--   Input: Source object                           -->
<#-- ************************************************ -->
static char* _get_${enumEntry.getName()}_str(${enumEntry.getName()} input) {
${enumHelper.generateToString(enumEntry.getValues())}
}
