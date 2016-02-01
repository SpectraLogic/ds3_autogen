<#include "../copyright.ftl"/>

package ${packageName};

import com.spectralogic.ds3client.networking.WebResponse;
import java.io.IOException;
<#include "../imports.ftl"/>

public class ${name} extends BulkResponse {
<#include "common/response_constructor.ftl"/>
}