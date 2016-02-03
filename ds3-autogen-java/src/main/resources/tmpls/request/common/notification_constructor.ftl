    <#list constructors as constructor>
    public ${name}(${javaHelper.constructorArgs(constructor.getParameters())}) {
        super(notificationId);

        <#list constructor.getAssignments() as arg>
        this.${arg.getName()?uncap_first} = ${arg.getName()?uncap_first};
        </#list>
<#include "add_query_params.ftl"/>

    }
    </#list>
