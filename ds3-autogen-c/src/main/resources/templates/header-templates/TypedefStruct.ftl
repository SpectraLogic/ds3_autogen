<#-- ********************************************* -->
<#-- Generate all "TypedefStructs" from Structs    -->
<#--   Input: Header object                        -->
<#-- ********************************************* -->
typedef struct {
${structHelper.generateStructMembers(structEntry.getStructMembers())}}${structEntry.getName()};
