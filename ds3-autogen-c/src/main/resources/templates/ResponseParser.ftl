<#-- ******************************************************** -->
<#-- Generate all "TypedefStructResponseParsers" from Structs -->
<#--   Input: Header object                                   -->
<#-- ******************************************************** -->
<#list getStructs() as structEntry>

    <#list structEntry.getStructMembers() as structMember>
        <#if structMember.getType().isArray()>
            <#include "ArrayStructMemberParser.ftl">
        </#if>
    </#list>

static ${structEntry.getName()}* _parse_${structEntry.getName()}(const ds3_log* log, const xmlDocPtr doc, const xmlNodePtr root_node) {
    xmlNodePtr child_node;
    ${structEntry.getName()}* response = g_new0(${structEntry.getName()}, 1);

    for (child_node = root_node->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {
${structEntry.getStructHelper().generateResponseParser(structEntry.getName(), structEntry.getStructMembers())}
    }

    return response;
}
</#list>
