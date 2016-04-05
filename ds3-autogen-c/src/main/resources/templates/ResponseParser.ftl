<#-- ******************************************************** -->
<#-- Generate all "TypedefStructResponseParsers" from Structs -->
<#--   Input: Header object                                   -->
<#-- ******************************************************** -->
static ds3_error* _parse_${structEntry.getName()}(const ds3_log* log, const ${structEntry.getName()}** response) {
    xmlDocPtr doc;
    xmlNodePtr root;
    xmlNodePtr child_node;
    ds3_error* error;
    ${structEntry.getName()}* _response = *response;

    error = _get_request_xml_nodes(client, request, &doc, &root, "${structEntry.getNameToMarshall()}");
    if (error != NULL) {
        return error;
    }

    for (child_node = root_node->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {
${structEntry.getStructHelper().generateResponseParser(structEntry.getName(), structEntry.getStructMembers())}
    }

    xmlFreeDoc(doc);
    return NULL;
}

