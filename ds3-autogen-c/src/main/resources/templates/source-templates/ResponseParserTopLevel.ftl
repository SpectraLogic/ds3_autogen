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

    error = _get_request_xml_nodes(xml_blob, &doc, &root, "${structEntry.getNameToMarshall()}");
    if (error != NULL) {
        return error;
    }

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

    xmlFreeDoc(doc);

    if (error == NULL) {
        *_response = response;
    } else {
        ${structEntry.getName()}_free(response);
    }

    return error;
}
