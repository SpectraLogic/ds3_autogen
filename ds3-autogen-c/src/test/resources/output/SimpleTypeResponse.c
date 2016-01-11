 static ds3_owner* _parse_owner(const ds3_log* log, const xmlDocPtr doc, const xmlNodePtr root_node) {
     xmlNodePtr child_node;
     ds3_owner* owner = g_new0(ds3_owner, 1);

     for (child_node = root_node->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {
         if (element_equal(child_node, "DisplayName")) {
             owner->name = xml_get_string(doc, child_node);
         } else if (element_equal(child_node, "ID")) {
             owner->id = xml_get_string(doc, child_node);
         } else {
             ds3_log_message(log, DS3_ERROR, "Unknown element: (%s)\n", child_node->name);
         }
     }

     return owner;
 }
