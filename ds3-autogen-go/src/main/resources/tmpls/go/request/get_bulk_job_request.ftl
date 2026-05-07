<#include "request_struct.ftl" />

<#assign maxConstNameLen = 0>
<#list constructor.structParams as param>
	<#if param.name?cap_first?length gt maxConstNameLen>
		<#assign maxConstNameLen = param.name?cap_first?length>
	</#if>
</#list>
<#if "Objects"?length gt maxConstNameLen>
	<#assign maxConstNameLen = "Objects"?length>
</#if>
func New${name}(${constructor.constructorParams}, objectNames []string) *${name} {
	return &${name}{
<#list constructor.structParams as param>
		${(param.name?cap_first + ":")?right_pad(maxConstNameLen + 1)} ${param.assignment},
</#list>
		${("Objects:")?right_pad(maxConstNameLen + 1)} buildDs3GetObjectSliceFromNames(objectNames),
	}
}

func New${name}WithPartialObjects(${constructor.constructorParams}, objects []Ds3GetObject) *${name} {
	return &${name}{
<#list constructor.structParams as param>
		${(param.name?cap_first + ":")?right_pad(maxConstNameLen + 1)} ${param.assignment},
</#list>
		${("Objects:")?right_pad(maxConstNameLen + 1)} objects,
	}
}
<#include "with_constructors.ftl" />
