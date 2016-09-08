    <#list constructors as constructor>
    ${constructor.documentation}
    public ${name}(${javaHelper.constructorArgs(constructor.getParameters())}) {
        <#list constructor.getAssignments() as arg>
        this.${arg.getName()?uncap_first} = ${javaHelper.paramAssignmentRHS(arg)};
        </#list>
<#include "add_query_params.ftl"/>
        <#list constructor.getAdditionalLines() as line>
        ${line}
        </#list>
    }
    </#list>