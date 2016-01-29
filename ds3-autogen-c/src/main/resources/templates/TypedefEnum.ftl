<#-- ***************************************** -->
<#-- Generate all "TypedefEnums" from Enums    -->
<#--   Input: Header object                    -->
<#-- ***************************************** -->
<#list getEnums() as enumEntry>
typedef enum {
${enumEntry.getEnumHelper().getEnumValues(enumEntry.getValues())}
}${enumEntry.getEnumHelper().getDs3Type(enumEntry.getName())};
</#list>
