<#include "../copyright.ftl"/>

package ${packageName};

<#if javaHelper.isSpectraDs3OrNotification(packageName)>
import com.spectralogic.ds3client.commands.BulkResponse;
</#if>
import com.spectralogic.ds3client.networking.WebResponse;
import java.io.IOException;

public class ${name} extends BulkResponse {
<#include "common/response_constructor.ftl"/>
}