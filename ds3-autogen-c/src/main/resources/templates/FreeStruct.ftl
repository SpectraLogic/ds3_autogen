<#include "CopyrightHeader.ftl"/>

void ${getTypeHelper().getFreeFunctionName(name)}(${getTypeHelper().getResponseTypeName(name)}* response_data) {
    if (response_data == NULL) {
        return;
    }

${getTypeHelper().generateFreeTypeElementMembers(getElements())}

    g_free(response_data);
}