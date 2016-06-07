<#-- ******************************************************** -->
<#-- Generate "TypedefStructResponseParser" from Structs      -->
<#--   Input: Struct object                                   -->
<#-- ******************************************************** -->
static ds3_error* _parse_${structEntry.getName()}(const ds3_client* client, const xmlDocPtr doc, const xmlNodePtr root, ${structEntry.getName()}** _response) {
<#if structHelper.hasAttributes(structEntry)>
    struct _xmlAttr* attribute;
</#if>
<#if structHelper.hasChildNodes(structEntry)>
    xmlNodePtr child_node;
</#if>
    ${structEntry.getName()}* response;
    ds3_error* error = NULL;

    response = g_new0(${structEntry.getName()}, 1);

<#if structHelper.hasAttributes(structEntry)>
    for (attribute = root->properties; attribute != NULL; attribute = attribute->next) {
${structMemberHelper.generateResponseAttributesParser(structEntry.getStructMembers())}
    }
</#if>

<#if structHelper.hasChildNodes(structEntry)>
    for (child_node = root->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {
${structMemberHelper.generateResponseParser(structEntry.getStructMembers())}
    }
</#if>

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

