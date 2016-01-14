<#include "CopyrightHeader.ftl"/>

static ${getTypeHelper().getResponseTypeName(name)}* ${getTypeHelper().getParserFunctionName(name)}(const ds3_log* log, const xmlDocPtr doc, const xmlNodePtr root_node) {
    xmlNodePtr child_node;
    ${getTypeHelper().getResponseTypeName(name)}* ${getTypeHelper().getResponseTypeName(name)} = g_new0(${getTypeHelper().getResponseTypeName(name)}, 1);

    for (child_node = root_node->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {
        ${getTypeHelper().generateResponseParser(getName(), getElements())}
    }

    return ${getTypeHelper().getResponseTypeName(name)};
}
