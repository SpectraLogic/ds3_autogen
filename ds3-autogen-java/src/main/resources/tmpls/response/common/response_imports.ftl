<#if javaHelper.isSpectraDs3OrNotification(packageName)>
import com.spectralogic.ds3client.commands.AbstractResponse;
</#if>
import com.spectralogic.ds3client.networking.WebResponse;
import java.io.IOException;
import java.io.InputStream;
<#include "../../imports.ftl"/>