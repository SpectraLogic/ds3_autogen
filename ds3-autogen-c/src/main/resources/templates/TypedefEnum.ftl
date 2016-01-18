<#include "CopyrightHeader.ftl"/>

typedef enum {
${getTypeHelper().getEnumValues(enumConstants)}
}${getTypeHelper().getDs3Type(name)};
