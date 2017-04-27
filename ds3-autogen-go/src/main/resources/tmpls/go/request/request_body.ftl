<#include "../common/copyright.ftl" />

package models

import (
    "net/url"
    "net/http"
    "ds3/networking"
)

type ${name} struct {
    <#list structParams as param>
    ${param.name?uncap_first} ${param.type}
    </#list>
    queryParams *url.Values
}

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

<#list withConstructors as const>
func (${name?uncap_first} *${name}) With${const.name?cap_first}(${const.name?uncap_first} ${const.type}) *${name} {
    ${name?uncap_first}.${const.name?uncap_first} = ${const.name?uncap_first}
    ${name?uncap_first}.queryParams.Set("${const.key}", ${const.assignment})
    return ${name?uncap_first}
}
</#list>

<#list nullableWithConstructors as const>
func (${name?uncap_first} *${name}) With${const.name?cap_first}(${const.name?uncap_first} ${const.type}) *${name} {
    ${name?uncap_first}.${const.name?uncap_first} = ${const.name?uncap_first}
    if ${const.name?uncap_first} != nil {
        ${name?uncap_first}.queryParams.Set("${const.key}", ${const.assignment})
    } else {
        ${name?uncap_first}.queryParams.Set("${const.key}", "")
    }
    return ${name?uncap_first}
}
</#list>

<#list voidWithConstructors as const>
func (${name?uncap_first} *${name}) With${const.name?cap_first}() *${name} {
    ${name?uncap_first}.queryParams.Set("${const.key}", "")
    return ${name?uncap_first}
}
</#list>

func (${name}) Verb() networking.HttpVerb {
    return networking.${httpVerb}
}

func (${name?uncap_first} *${name}) Path() string {
    return ${path}
}

func (${name?uncap_first} *${name}) QueryParams() *url.Values {
    return ${name?uncap_first}.queryParams
}

func (${name}) Header() *http.Header {
    return &http.Header{}
}

func (${name}) GetChecksum() networking.Checksum {
    return networking.NewNoneChecksum()
}
