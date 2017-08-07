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
