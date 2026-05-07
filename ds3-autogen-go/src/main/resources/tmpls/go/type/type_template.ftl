<#assign maxNameLen = 0>
<#list structElements as element>
	<#if element.name?length gt maxNameLen>
		<#assign maxNameLen = element.name?length>
	</#if>
</#list>

type ${name} struct {
<#list structElements as element>
	${element.name?right_pad(maxNameLen)} ${element.type}
</#list>
}
