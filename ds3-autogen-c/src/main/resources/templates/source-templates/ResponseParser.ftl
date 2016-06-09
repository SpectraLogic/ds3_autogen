<#-- ******************************************************** -->
<#-- Generate "TypedefStructResponseParser" from Structs      -->
<#--   Input: Struct object                                   -->
<#-- ******************************************************** -->
static ds3_error* _parse_${structEntry.getName()}(const ds3_client* client, const xmlDocPtr doc, const xmlNodePtr root, ${structEntry.getName()}** _response) {
    ${structEntry.getName()}* response;
<#if structHelper.hasAttributes(structEntry)>
    struct _xmlAttr* attribute;
</#if>
<#if structHelper.hasChildNodes(structEntry)>
    xmlNodePtr child_node;
</#if>
    ds3_error* error = NULL;
<#if structHelper.hasUnwrappedChildNodes(structEntry)>
<#list structMemberHelper.getUnwrappedListChildNodes(structEntry.getStructMembers()) as unwrappedChildNode>
    GPtrArray* ${unwrappedChildNode.getName()}_array = g_ptr_array_new();
</#list>
</#if>

    response = g_new0(${structEntry.getName()}, 1);

<#if structHelper.hasAttributes(structEntry)>
    for (attribute = root->properties; attribute != NULL; attribute = attribute->next) {
${structHelper.generateResponseAttributesParser(structEntry.getName(), structEntry.getStructMembers())}
    }
</#if>

<#if structHelper.hasChildNodes(structEntry)>
    for (child_node = root->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {
${structHelper.generateResponseParser(structEntry.getName(), structEntry.getStructMembers())}
    }
</#if>

<#if structHelper.hasUnwrappedChildNodes(structEntry)>
<#list structMemberHelper.getUnwrappedListChildNodes(structEntry.getStructMembers()) as unwrappedChildNode>
    response->${unwrappedChildNode.getName()} = (${unwrappedChildNode.getType().getTypeName()}**)${unwrappedChildNode.getName()}_array->pdata;
    response->num_${unwrappedChildNode.getName()} = ${unwrappedChildNode.getName()}_array->len;
    g_ptr_array_free(${unwrappedChildNode.getName()}_array, FALSE);
</#list>
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

