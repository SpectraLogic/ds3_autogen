type ${name} struct {
    <#list structParams as param>
    ${param.name?cap_first} ${param.type}
    </#list>
}
