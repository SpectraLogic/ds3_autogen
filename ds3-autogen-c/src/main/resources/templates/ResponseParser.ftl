<#-- ******************************************************** -->
<#-- Generate all "TypedefStructResponseParsers" from Structs -->
<#--   Input: Header object                                   -->
<#-- ******************************************************** -->
<#list getStructs() as structEntry>

    <#list structEntry.getStructMembers() as structMember>
        <#if structMember.getType()?contains("**")>
            <#include "ArrayStructMemberParser.ftl">
        </#if>
    </#list>

static ${structEntry.getStructHelper().getResponseTypeName(structEntry.getName())}* ${structEntry.getStructHelper().getParserFunctionName(structEntry.getName())}(const ds3_log* log, const xmlDocPtr doc, const xmlNodePtr root_node) {
    xmlNodePtr child_node;
    ${structEntry.getStructHelper().getResponseTypeName(structEntry.getName())}* response = g_new0(${structEntry.getStructHelper().getResponseTypeName(structEntry.getName())}, 1);

    for (child_node = root_node->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {
${structEntry.getStructHelper().generateResponseParser(structEntry.getName(), structEntry.getStructMembers())}
    }

    return response;
}
</#list>
