    public ${name}(${javaHelper.constructorArgs(
                     helper.addArgument(constructorArguments, "NotificationId", "UUID"))}) {
        super(notificationId);

        <#list constructorArguments as arg>
        this.${arg.getName()?uncap_first} = ${arg.getName()?uncap_first};
        </#list>
<#include "constructor_get_query_params.ftl"/>
    }
