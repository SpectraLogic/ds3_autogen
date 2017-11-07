<#include "request_struct.ftl" />

func New${name}(${constructor.constructorParams}) *${name} {
    return &${name}{
        <#list constructor.structParams as param>
        ${param.name?cap_first}: ${param.assignment},
        </#list>
    }
}

<#include "with_constructors.ftl" />
