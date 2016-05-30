<#-- ******************************************************** -->
<#-- Generate all "TypedefStructResponseParsers" from Structs -->
<#--   Input: Source object                                   -->
<#-- ******************************************************** -->
static ds3_error* _parse_top_level_${structEntry.getName()}(const ds3_client* client, const ds3_request* request, ${structEntry.getName()}** _response, GByteArray* xml_blob) {
    xmlDocPtr doc;
    xmlNodePtr root;
    xmlNodePtr child_node;
    ${structEntry.getName()}* response;
    ds3_error* error = NULL;

    error = _get_request_xml_nodes(xml_blob, &doc, &root, "${structEntry.getNameToMarshall()}");
    if (error != NULL) {
        return error;
    }

    response = g_new0(${structEntry.getName()}, 1);
    for (child_node = root->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {
${structHelper.generateResponseParser(structEntry.getStructMembers(), false)}
    }

    xmlFreeDoc(doc);

    if (error == NULL) {
        *_response = response;
    } else {
        ${structEntry.getName()}_free(response);
    }

    return error;
}
