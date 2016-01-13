<#include "CopyrightHeader.ftl"/>

void ${getChelper().getTypeHelper().getFreeFunctionName(name)}(${getChelper().getTypeHelper().getResponseTypeName(name)}* response_data) {
    if (response_data == NULL) {
        return;
    }

${getChelper().getTypeHelper().generateFreeTypeElementMembers(getElements())}

    g_free(response_data);
}