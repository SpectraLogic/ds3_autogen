<#-- ****************************************************** -->
<#-- Generate all "TypedefStructFreeFunctions" from Structs -->
<#--   Input: Source object                                 -->
<#-- ****************************************************** -->
<#list getStructs() as structEntry>
void ${structEntry.getStructHelper().getFreeFunctionName(structEntry.getName())}(${structEntry.getStructHelper().getResponseTypeName(structEntry.getName())}* response_data) {
    if (response_data == NULL) {
        return;
    }

${structEntry.getStructHelper().generateFreeStructMembers(structEntry.getStructMembers())}

    g_free(response_data);
}
</#list>

