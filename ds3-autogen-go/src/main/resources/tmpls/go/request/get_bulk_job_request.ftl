<#include "request_header.ftl" />

<#include "request_struct.ftl" />

func New${name}(${constructor.constructorParams}, objectNames []string) *${name} {
    queryParams := &url.Values{}
    <#list constructor.queryParams as param>
    queryParams.Set("${param.name}", ${param.assignment})
    </#list>

    return &${name}{
        <#list constructor.structParams as param>
        ${param.name}: ${param.assignment},
        </#list>
        content: buildDs3ObjectStreamFromNames(objectNames),
        queryParams: queryParams,
    }
}

func New${name}WithPartialObjects(${constructor.constructorParams}, objects []Ds3GetObject) *${name} {
    queryParams := &url.Values{}
    <#list constructor.queryParams as param>
    queryParams.Set("${param.name}", ${param.assignment})
    </#list>

    return &${name}{
        <#list constructor.structParams as param>
        ${param.name}: ${param.assignment},
        </#list>
        content: buildDs3GetObjectListStream(objects),
        queryParams: queryParams,
    }
}

<#include "with_constructors_verb_path.ftl" />

<#include "no_checksum.ftl" />

<#include "no_headers.ftl" />

<#include "stream_with_content.ftl">
