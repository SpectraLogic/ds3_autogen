<#-- ******************************************************* -->
<#-- Generate all "TypedefStructFreePrototypes" from Structs -->
<#--   Input: Header object                                  -->
<#-- ******************************************************* -->
<#list getStructs() as structEntry>
void ${structEntry.getStructHelper().getFreeFunctionName(structEntry.getName())}(${structEntry.getStructHelper().getResponseTypeName(structEntry.getName())}* response_data);
</#list>
