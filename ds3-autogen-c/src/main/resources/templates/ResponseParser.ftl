<#include "CopyrightHeader.ftl"/>

static ${getChelper().getTypeHelper().getResponseTypeName(name)}* ${getChelper().getTypeHelper().getParserFunctionName(name)}(const ds3_log* log, const xmlDocPtr doc, const xmlNodePtr root_node) {
    xmlNodePtr child_node;
    ${getChelper().getTypeHelper().getResponseTypeName(name)}* ${getChelper().getTypeHelper().getResponseTypeName(name)} = g_new0(${getChelper().getTypeHelper().getResponseTypeName(name)}, 1);

    for (child_node = root_node->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {
        ${getChelper().getTypeHelper().generateResponseParser(getName(), getElements())}
    }

    return ${getChelper().getTypeHelper().getResponseTypeName(name)};
}
