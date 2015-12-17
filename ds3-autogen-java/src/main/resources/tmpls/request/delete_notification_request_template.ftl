<#include "../copyright.ftl"/>

package ${packageName};

<#if javaHelper.isSpectraDs3(packageName)>
import com.spectralogic.ds3client.commands.notifications.AbstractDeleteNotificationRequest;
</#if>
import java.util.UUID;

public class ${name} extends AbstractDeleteNotificationRequest {

    //Variables
<#include "common/notification_variables.ftl"/>

    //Constructor
<#include "common/notification_constructor.ftl"/>

    @Override
    public String getPath() {
        return ${path};
    }

<#include "common/getters.ftl"/>
}