<#-- ***************************************** -->
<#-- Generate all "TypedefEnums" from Enums    -->
<#--   Input: Header object                    -->
<#-- ***************************************** -->
typedef enum {
${enumEntry.getEnumHelper().getEnumValues(enumEntry.getValues())}
}${enumEntry.getName()};
