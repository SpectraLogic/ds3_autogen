<#-- ******************************************************** -->
<#-- Generate all "TypedefStructResponseParsers" from Structs -->
<#--   Input: Source object                                   -->
<#-- ******************************************************** -->
<#if structEntry.isTopLevel()>
static ds3_error* _parse_${structEntry.getName()}(const ds3_client* client, const ds3_request* request, const ${structEntry.getName()}** _response, const GByteArray* xml_blob) {
    xmlDocPtr doc;
    xmlNodePtr root;
<#else>
static ds3_error* _parse_${structEntry.getName()}(const ds3_client* client, const xmlDocPtr doc, const xmlNodePtr root, const ${structEntry.getName()}** _response) {
</#if>
    xmlNodePtr child_node;
    ${structEntry.getName()}* response;
    ds3_error* error = NULL;

    <#if structEntry.isTopLevel()>
    error = _get_request_xml_nodes(client, request, &doc, &root, "${structEntry.getNameToMarshall()}");
    if (error != NULL) {
        return error;
    }
    </#if>

    response = g_new0(${structEntry.getName()}, 1);
    for (child_node = root->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {
${structHelper.generateResponseParser(structEntry.getName(), structEntry.getStructMembers())}
    }

    <#if structEntry.isTopLevel()>
    xmlFreeDoc(doc);
    </#if>

    <#if structEntry.isTopLevel() || structHelper.hasComplexMembers(structEntry)>
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

