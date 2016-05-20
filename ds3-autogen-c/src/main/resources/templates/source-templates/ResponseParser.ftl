<#-- ******************************************************** -->
<#-- Generate "TypedefStructResponseParser" from Structs      -->
<#--   Input: Source object                                   -->
<#-- ******************************************************** -->
static ds3_error* _parse_${structEntry.getName()}(const ds3_client* client, const xmlDocPtr doc, const xmlNodePtr root, ${structEntry.getName()}** _response) {
    xmlNodePtr child_node;
    ${structEntry.getName()}* response;
    ds3_error* error = NULL;

    response = g_new0(${structEntry.getName()}, 1);
    for (child_node = root->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {
${structHelper.generateResponseParser(structEntry.getStructMembers(), false)}
    }

    <#if structHelper.hasComplexMembers(structEntry)>
    if (error == NULL) {
        *_response = response;
    } else {
        ${structEntry.getName()}_free(response);
    }
    <#else>
    *_response = response;
    </#if>

    return error;
}

