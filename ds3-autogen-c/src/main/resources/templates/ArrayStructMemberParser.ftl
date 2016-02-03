

static GPtrArray* _parse_${structEntry.getName()}_array(const ds3_log* log, xmlDocPtr doc, xmlNodePtr root) {
    xmlNodePtr child_node;

    GPtrArray* ${structMember.getName()}_array = g_ptr_array_new();

    for (child_node = root->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {
        g_ptr_array_add(${structMember.getName()}_array, _parse_${structMember.getType().getTypeRoot()}(log, doc, child_node)); <#-- TODO, Hackish; look for better solution -->
    }

    return ${structMember.getName()}_array;
}
