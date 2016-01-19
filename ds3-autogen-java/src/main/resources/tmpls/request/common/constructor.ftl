    public ${name}(${javaHelper.constructorArgs(constructorArguments)}) {
        <#list constructorArguments as arg>
        this.${arg.getName()?uncap_first} = ${arg.getName()?uncap_first};
        </#list>
        <#if operation??>
        this.getQueryParams().put("operation", "${operation.toString()?lower_case}");
        </#if>
<#include "constructor_get_query_params.ftl"/>

    }