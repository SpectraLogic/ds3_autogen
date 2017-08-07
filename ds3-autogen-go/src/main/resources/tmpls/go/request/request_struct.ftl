type ${name} struct {
    <#list structParams as param>
    ${param.name?uncap_first} ${param.type}
    </#list>
    queryParams *url.Values
}
