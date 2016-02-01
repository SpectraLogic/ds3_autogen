<#include "../copyright.ftl"/>

package ${packageName};

<#include "../imports.ftl"/>

public class ${name} extends AbstractCreateNotificationRequest {

    // Variables
<#include "common/variables.ftl"/>

    public ${name}(${javaHelper.constructorArgs(constructorArguments)}) {
        super(notificationEndPoint);

        <#list helper.removeArgument(constructorArguments, "NotificationEndPoint") as arg>
        this.${arg.getName()?uncap_first} = ${arg.getName()?uncap_first};
        </#list>
<#include "common/constructor_get_query_params.ftl"/>

    }

<#include "common/with_constructors.ftl"/>

    @Override
    public String getPath() {
        return ${path};
    }

<#include "common/getters.ftl">
}