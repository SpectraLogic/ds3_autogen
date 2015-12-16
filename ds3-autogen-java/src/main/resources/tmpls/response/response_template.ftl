<#include "../copyright.ftl"/>

package ${packageName};

<#if javaHelper.isSpectraDs3OrNotification(packageName)>
import com.spectralogic.ds3client.commands.AbstractResponse;
</#if>
import com.spectralogic.ds3client.networking.WebResponse;
import java.io.IOException;
import java.io.InputStream;

public class ${name} extends AbstractResponse {

    private String result;

<#include "common/response_constructor.ftl"/>

    @Override
    protected void processResponse() throws IOException {
        try {
            this.checkStatusCode(${helper.getResponseCodes(responseCodes)});
            try (final InputStream content = getResponse().getResponseStream()) {
                this.result = content.toString();
            }
        } finally {
            this.getResponse().close();
        }
    }

    public String getResult() {
        return result;
    }
}