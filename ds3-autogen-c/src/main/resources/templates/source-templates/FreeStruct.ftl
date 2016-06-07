<#-- ****************************************************** -->
<#-- Generate all "TypedefStructFreeFunctions" from Structs -->
<#--   Input: Source object                                 -->
<#-- ****************************************************** -->
void ${structEntry.getName()}_free(${structEntry.getName()}* response) {
    if (response == NULL) {
        return;
    }

<#if structEntry.hasArrayMembers()>
    size_t index;
</#if>
${structMemberHelper.generateFreeStructMembers(structEntry.getStructMembers())}
    g_free(response);
}
