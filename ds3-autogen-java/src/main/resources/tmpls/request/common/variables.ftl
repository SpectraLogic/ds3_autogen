<#include "required_variables.ftl"/>

    <#list optionalArguments as arg>
    private ${javaHelper.getType(arg)} ${arg.getName()?uncap_first};
    </#list>