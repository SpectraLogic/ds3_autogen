<#-- ******************************************************** -->
<#-- Generate all "TypedefStructResponseParsers" from Structs -->
<#--   Input: Source object                                   -->
<#-- ******************************************************** -->
static ds3_error* _parse_top_level_${structEntry.getName()}(const ds3_client* client, const ds3_request* request, ${structEntry.getName()}** _response, GByteArray* xml_blob) {
    xmlDocPtr doc;
    xmlNodePtr root;
<#if structHelper.hasChildNodes(structEntry)>
    xmlNodePtr child_node;
</#if>
<#if structHelper.hasAttributes(structEntry)>
    struct _xmlAttr* attribute;
</#if>
    ${structEntry.getName()}* response;
    ds3_error* error = NULL;
<#if structHelper.hasUnwrappedChildNodes(structEntry)>
<#list structMemberHelper.getUnwrappedListChildNodes(structEntry.getStructMembers()) as unwrappedChildNode>
    GPtrArray* ${unwrappedChildNode.getName()}_array = g_ptr_array_new();
</#list>
</#if>

    error = _get_request_xml_nodes(xml_blob, &doc, &root, "${structEntry.getNameToMarshall()}");
    if (error != NULL) {
        return error;
    }

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

    xmlFreeDoc(doc);

    if (error == NULL) {
        *_response = response;
    } else {
        ${structEntry.getName()}_free(response);
    }

    return error;
}
