<#include "CopyrightHeader.ftl"/>

typedef enum {
${getTypeHelper().getTypeElementsList(getElements())}
}${getTypeHelper().getResponseTypeName(name)};
