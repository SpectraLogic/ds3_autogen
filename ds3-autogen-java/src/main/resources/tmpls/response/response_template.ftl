<#include "../copyright.ftl"/>

package ${packageName};

<#if javaHelper.isSpectraDs3OrNotification(packageName)>
import com.spectralogic.ds3client.commands.AbstractResponse;
</#if>
import com.spectralogic.ds3client.networking.WebResponse;
import java.io.IOException;
import java.io.InputStream;

public class ${name} extends AbstractResponse {

${javaHelper.createAllResponseResultClassVars(
  javaHelper.removeErrorResponseCodes(responseCodes))}

<#include "common/response_constructor.ftl"/>

    @Override
    protected void processResponse() throws IOException {
        try {
            this.checkStatusCode(${helper.getResponseCodes(
                                   javaHelper.removeErrorResponseCodes(responseCodes))});

            switch (this.getStatusCode()) {
            <#list javaHelper.removeErrorResponseCodes(responseCodes) as responseCode>
            case ${responseCode.getCode()}:
                ${javaHelper.processResponseCodeLines(responseCode, 4)}
            </#list>
            default:
                assert false : "checkStatusCode should have made it impossible to reach this line.";
            }
        } finally {
            this.getResponse().close();
        }
    }

    public String getResult() {
        return result;
    }
}