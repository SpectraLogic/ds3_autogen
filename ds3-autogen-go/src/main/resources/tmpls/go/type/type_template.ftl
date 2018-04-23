type ${name} struct {
    <#list structElements as element>
    ${element.name} ${element.type}
    </#list>
}

