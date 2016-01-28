<#include "../copyright.ftl"/>

package ${packageName};

<#include "../imports.ftl"/>

public class ${name} extends AbstractDeleteNotificationRequest {

    //Variables
<#include "common/variables.ftl"/>

    //Constructor
<#include "common/notification_constructor.ftl"/>

    @Override
    public String getPath() {
        return ${path};
    }

<#include "common/getters.ftl"/>

}