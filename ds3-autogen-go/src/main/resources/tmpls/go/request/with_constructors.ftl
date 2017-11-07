<#list withConstructors as const>
func (${name?uncap_first} *${name}) With${const.name?cap_first}(${const.constructorParams}) *${name} {
    ${name?uncap_first}.${const.name?cap_first} = ${const.assignment}
    return ${name?uncap_first}
}

</#list>
