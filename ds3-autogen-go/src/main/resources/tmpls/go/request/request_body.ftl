<#include "request_struct.ftl" />

<#assign maxConstNameLen = 0>
<#list constructor.structParams as param>
	<#if param.name?cap_first?length gt maxConstNameLen>
		<#assign maxConstNameLen = param.name?cap_first?length>
	</#if>
</#list>
func New${name}(${constructor.constructorParams}) *${name} {
<#if constructor.structParams?has_content>
	return &${name}{
<#list constructor.structParams as param>
		${(param.name?cap_first + ":")?right_pad(maxConstNameLen + 1)} ${param.assignment},
</#list>
	}
<#else>
	return &${name}{}
</#if>
}
<#include "with_constructors.ftl" />
