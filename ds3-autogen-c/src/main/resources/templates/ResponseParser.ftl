static ${getStructHelper().getResponseTypeName(name)}* ${getStructHelper().getParserFunctionName(name)}(const ds3_log* log, const xmlDocPtr doc, const xmlNodePtr root_node) {
    xmlNodePtr child_node;
    ${getStructHelper().getResponseTypeName(name)}* ${getStructHelper().getResponseTypeName(name)} = g_new0(${getStructHelper().getResponseTypeName(name)}, 1);

    for (child_node = root_node->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {
${getStructHelper().generateResponseParser(getName(), getVariables())}
    }

    return ${getStructHelper().getResponseTypeName(name)};
}
