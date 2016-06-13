<#-- ********************************************* -->
<#-- Generate all "TypedefStructs" from Structs    -->
<#--   Input: Header object                        -->
<#-- ********************************************* -->
typedef struct {
${structMemberHelper.generateStructMembers(structEntry.getStructMembers())}}${structEntry.getName()};
