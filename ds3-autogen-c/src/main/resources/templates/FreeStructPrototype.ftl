<#-- ******************************************************* -->
<#-- Generate all "TypedefStructFreePrototypes" from Structs -->
<#--   Input: Header object                                  -->
<#-- ******************************************************* -->
<#list getStructs() as structEntry>
void ${structEntry.getName()}_free(${structEntry.getName()}* response_data);
</#list>
