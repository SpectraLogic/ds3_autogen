    public ${name}(${javaHelper.constructorArgs(
                         helper.removeVoidArguments(requiredArguments))}) {
        <#list helper.removeVoidArguments(requiredArguments) as arg>
        this.${arg.getName()?uncap_first} = ${arg.getName()?uncap_first};
        </#list>
        <#if operation??>
        this.getQueryParams().put("operation", "${operation.toString()?lower_case}");
        </#if>
<#include "constructor_get_query_params.ftl"/>

    }