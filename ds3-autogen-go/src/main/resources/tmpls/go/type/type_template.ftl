<#include "../common/copyright.ftl" />

package models

type ${name} struct {
    <#list structElements as element>
    ${element.name} ${element.type} `${element.xmlNotation}`
    </#list>
}