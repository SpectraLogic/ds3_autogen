<#include "request_struct.ftl" />

func New${name}(${constructor.constructorParams}) *${name} {
    queryParams := &url.Values{}
    <#list constructor.queryParams as param>
    queryParams.Set("${param.name}", ${param.assignment})
    </#list>

    return &${name}{
        <#list constructor.structParams as param>
        ${param.name}: ${param.assignment},
        </#list>
        queryParams: queryParams,
    }
}

<#include "with_constructors_verb_path.ftl" />
