<#-- ***************************************** -->
<#-- Generate all "TypedefEnums" from Enums    -->
<#--   Input: Header object                    -->
<#-- ***************************************** -->
typedef enum {
${enumHelper.getEnumValues(enumEntry.getValues())}
}${enumEntry.getName()};
