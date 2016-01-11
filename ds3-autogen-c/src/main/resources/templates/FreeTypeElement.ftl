<#include "CopyrightHeader.ftl"/>

void ${getFreeFunctionName()}(${getResponseTypeName()}* response_data) {
    if (response_data == NULL) {
        return;
    }

${generateFreeTypeElementMembers()}

    g_free(response_data);
}