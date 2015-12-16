<#include "../copyright.tmpl"/>

package ${packageName};

<#if javaHelper.isSpectraDs3(packageName)>
import com.spectralogic.ds3client.commands.notifications.AbstractGetNotificationRequest;
</#if>
import java.util.UUID;
<#list imports as import>
<#if import != "java.util.UUID">
import ${import};
</#if>
</#list>

public class ${name} extends AbstractGetNotificationRequest {

    // Variables
    <#include "common/variables.tmpl"/>

    // Constructor
<#include "common/notification_constructor.tmpl"/>

    <#include "common/with_constructors.tmpl"/>

    @Override
    public String getPath() {
        return ${path};
    }

    <#include "common/getters.tmpl"/>
}