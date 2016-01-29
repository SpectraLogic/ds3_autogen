<#-- ********************************************* -->
<#-- Generate all "TypedefStructs" from Structs    -->
<#--   Input: Header object                        -->
<#-- ********************************************* -->
<#list getStructs() as structEntry>
typedef struct {
${structEntry.getStructHelper().generateStructMembers(structEntry.getStructMembers())}
}${structEntry.getStructHelper().getResponseTypeName(structEntry.getName())};
</#list>
