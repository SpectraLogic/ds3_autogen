<#assign maxNameLen = 0>
<#list structParams as param>
	<#if param.name?cap_first?length gt maxNameLen>
		<#assign maxNameLen = param.name?cap_first?length>
	</#if>
</#list>

type ${name} struct {
<#list structParams as param>
	${param.name?cap_first?right_pad(maxNameLen)} ${param.type}
</#list>
}
