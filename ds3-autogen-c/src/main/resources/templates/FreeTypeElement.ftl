<#include "CopyrightHeader.ftl"/>

void ds3_free_${getNameUnderscores()}(${getResponseTypeName()}* response_data) {
    if (response_data == NULL) {
        return;
    }

${generateFreeTypeElementMembers()}

    g_free(response_data);
}