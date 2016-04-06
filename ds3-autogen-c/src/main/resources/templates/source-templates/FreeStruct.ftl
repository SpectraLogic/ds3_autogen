<#-- ****************************************************** -->
<#-- Generate all "TypedefStructFreeFunctions" from Structs -->
<#--   Input: Source object                                 -->
<#-- ****************************************************** -->
void ${structEntry.getName()}_free(${structEntry.getName()}* response) {
    if (response == NULL) {
        return;
    }

${structEntry.getStructHelper().generateFreeStructMembers(structEntry.getStructMembers())}
    g_free(response);
}

