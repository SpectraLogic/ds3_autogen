<#-- ******************************************************** -->
<#-- Generate "TypedefStructArrayResponseParser"              -->
<#--   Input: Struct object                                   -->
<#-- ******************************************************** -->
static ds3_error* _parse_${structEntry.getName()}_array(const ds3_client* client, const xmlDocPtr doc, const xmlNodePtr root, GPtrArray** _response) {
    ds3_error* error = NULL;
    xmlNodePtr child_node;
    GPtrArray* ${structEntry.getName()}_array = g_ptr_array_new();

    for (child_node = root->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {
        ${structEntry.getName()}* response = NULL;
        error = _parse_${structEntry.getName()}(client, doc, child_node, &response);
        g_ptr_array_add(${structEntry.getName()}_array, response);

        if (error != NULL) {
            break;
        }
    }

    *_response = ${structEntry.getName()}_array;

    return error;
}
