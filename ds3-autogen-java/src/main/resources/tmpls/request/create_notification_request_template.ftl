<#include "../copyright.tmpl"/>

package ${packageName};

<#if javaHelper.isSpectraDs3(packageName)>
import com.spectralogic.ds3client.commands.notifications.AbstractCreateNotificationRequest;
</#if>
<#include "../imports.tmpl"/>

public class ${name} extends AbstractCreateNotificationRequest {

    // Variables
<#include "common/notification_variables.tmpl"/>

    public ${name}(${javaHelper.constructorArgs(
                          helper.removeVoidArguments(requiredArguments))}) {
        super(notificationEndPoint);

        <#list helper.removeVoidArguments(
                   helper.removeArgument(requiredArguments, "NotificationEndPoint")) as arg>
        this.${arg.getName()?uncap_first} = ${arg.getName()?uncap_first};
        </#list>
<#include "common/constructor_get_query_params.tmpl"/>
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

    <#list helper.removeVoidArguments(
               helper.removeArgument(requiredArguments, "NotificationEndPoint")) as arg>
    public ${javaHelper.getType(arg)} get${arg.getName()?cap_first}() {
        return this.${arg.getName()?uncap_first};
    }

    </#list>
}