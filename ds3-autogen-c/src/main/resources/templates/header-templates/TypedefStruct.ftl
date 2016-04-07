<#-- ********************************************* -->
<#-- Generate all "TypedefStructs" from Structs    -->
<#--   Input: Header object                        -->
<#-- ********************************************* -->
<#list getStructs() as structEntry>
typedef struct {
${structHelper.generateStructMembers(structEntry.getStructMembers())}
}${structEntry.getName()};

</#list>
