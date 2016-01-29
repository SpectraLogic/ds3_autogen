<#-- ********************************************* -->
<#-- Generate all "TypedefStructs" from Structs    -->
<#--   Input: Header object                        -->
<#-- ********************************************* -->
<#list getStructs() as structEntry>
typedef struct {
${structEntry.getStructHelper().generateStructMembers(structEntry.getVariables())}
}${structEntry.getStructHelper().getResponseTypeName(structEntry.getName())};
</#list>
