<#-- ******************************************************** -->
<#-- Generate all "TypedefStructResponseParsers" from Structs -->
<#--   Input: Header object                                   -->
<#-- ******************************************************** -->
static ds3_error* _parse_${structEntry.getName()}(const ds3_client* client, const ds3_request* request, const ${structEntry.getName()}** _response) {
    xmlDocPtr doc;
    xmlNodePtr root;
    xmlNodePtr child_node;
    ${structEntry.getName()}* response;
    <#if structEntry.isTopLevel() || structEntry.getStructHelper().hasComplexMembers(structEntry)>
    ds3_error* error;
    </#if>

    <#if structEntry.isTopLevel()>
    error = _get_request_xml_nodes(client, request, &doc, &root, "${structEntry.getNameToMarshall()}");
    if (error != NULL) {
        return error;
    }
    </#if>

    response = g_new0(${structEntry.getName()}, 1);
    for (child_node = root_node->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {
${structEntry.getStructHelper().generateResponseParser(structEntry.getName(), structEntry.getStructMembers())}
    }

    <#if structEntry.isTopLevel()>
    xmlFreeDoc(doc);
    </#if>

    <#if structEntry.isTopLevel() || structEntry.getStructHelper().hasComplexMembers(structEntry)>
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

