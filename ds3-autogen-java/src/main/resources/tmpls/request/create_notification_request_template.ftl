<#include "../copyright.ftl"/>

package ${packageName};

<#if javaHelper.isSpectraDs3(packageName)>
import com.spectralogic.ds3client.commands.notifications.AbstractCreateNotificationRequest;
</#if>
<#include "../imports.ftl"/>

public class ${name} extends AbstractCreateNotificationRequest {

    // Variables
<#include "common/notification_variables.ftl"/>

    public ${name}(${javaHelper.constructorArgs(constructorArguments)}) {
        super(notificationEndPoint);

        <#list helper.removeArgument(constructorArguments, "NotificationEndPoint") as arg>
        this.${arg.getName()?uncap_first} = ${arg.getName()?uncap_first};
        </#list>
<#include "common/constructor_get_query_params.ftl"/>
    }

    <#list helper.removeArgument(optionalArguments, "NotificationEndPoint") as arg>
${javaHelper.createWithConstructor(arg, name)}
    </#list>

    @Override
    public String getPath() {
        return ${path};
    }

    <#list optionalArguments as arg>
    public ${javaHelper.getType(arg)} get${arg.getName()?cap_first}() {
        return this.${arg.getName()?uncap_first};
    }

    </#list>

    <#list helper.removeArgument(constructorArguments, "NotificationEndPoint") as arg>
    public ${javaHelper.getType(arg)} get${arg.getName()?cap_first}() {
        return this.${arg.getName()?uncap_first};
    }

    </#list>
}