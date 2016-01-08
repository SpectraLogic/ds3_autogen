<#include "CopyrightHeader.ftl"/>

void ${getResponseTypeName()}_free(${getResponseTypeName()}* response_data) {
    if (response_data == NULL) {
        return;
    }

${generateFreeTypeElementMembers()}

    g_free(response_data);
}