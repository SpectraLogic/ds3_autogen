<#include "../copyright.tmpl"/>

package ${packageName};

<#if javaHelper.isSpectraDs3(packageName)>
import com.spectralogic.ds3client.commands.notifications.AbstractDeleteNotificationRequest;
</#if>
import java.util.UUID;

public class ${name} extends AbstractDeleteNotificationRequest {

    //Variables
<#include "common/notification_variables.tmpl"/>

    //Constructor
<#include "common/notification_constructor.tmpl"/>

    @Override
    public String getPath() {
        return ${path};
    }

<#include "common/getters.tmpl"/>
}