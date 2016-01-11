<#include "CopyrightHeader.ftl"/>

static ${getResponseTypeName()}* _parse_${getResponseTypeName()}(const ds3_log* log, const xmlDocPtr doc, const xmlNodePtr root_node) {
    xmlNodePtr child_node;
    ${getResponseTypeName()}* ${getResponseTypeName()} = g_new0(${getResponseTypeName()}, 1);

    for (child_node = root_node->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {
        ${generateResponseParser()}
    }

    return ${getResponseTypeName()};
}
