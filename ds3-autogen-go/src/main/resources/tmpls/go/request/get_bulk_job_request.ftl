<#include "request_header.ftl" />

<#include "request_struct.ftl" />

func New${name}(${constructor.constructorParams}, objectNames []string) *${name} {

    return &${name}{
        <#list constructor.structParams as param>
        ${param.name?cap_first}: ${param.assignment},
        </#list>
        Objects: buildDs3GetObjectSliceFromNames(objectNames),
    }
}

func New${name}WithPartialObjects(${constructor.constructorParams}, objects []Ds3GetObject) *${name} {

    return &${name}{
        <#list constructor.structParams as param>
        ${param.name?cap_first}: ${param.assignment},
        </#list>
        Objects: objects,
    }
}

<#include "with_constructors.ftl" />
