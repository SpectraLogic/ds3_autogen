<#include "CopyrightHeader.ftl"/>

typedef enum {
${getChelper().getTypeHelper().getTypeElementsList(getElements())}
}${getChelper().getTypeHelper().getResponseTypeName(name)};
