<#include "CopyrightHeader.ftl"/>

typedef enum {
${getChelper().getTypeHelper().getEnumValues(enumConstants)}
}${getChelper().getTypeHelper().getDs3Type(name)};
