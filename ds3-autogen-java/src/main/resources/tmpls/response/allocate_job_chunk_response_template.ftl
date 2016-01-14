<#include "../copyright.ftl"/>

package ${packageName};

<#include "common/response_imports.ftl"/>
import com.spectralogic.ds3client.commands.RetryAfterExpectedException;

public class ${name} extends AbstractResponse {

${javaHelper.createAllResponseResultClassVars(
  javaHelper.removeErrorResponseCodes(responseCodes))}

    public enum Status {
        ALLOCATED, RETRYLATER
    }

<#include "common/allocated_retrylater_status_types_getters.ftl"/>

<#include "common/response_constructor.ftl"/>

    @Override
    protected void processResponse() throws IOException {
        try (final WebResponse response = this.getResponse()) {
            this.checkStatusCode(200, 403);

            switch (this.getStatusCode()) {
            case 200:
                try (final InputStream content = response.getResponseStream()) {
                    this.jobChunkContainerApiBeanResult = XmlOutput.fromXml(content, JobChunkContainerApiBean.class);
                    this.status = Status.ALLOCATED;
                }
                break;
            case 403:
                this.status = Status.RETRYLATER;
                this.retryAfterSeconds = parseRetryAfter(response);
                break;
            default:
                assert false : "checkStatusCode should have made it impossible to reach this line.";
            }
        } finally {
            this.getResponse().close();
        }
    }

<#include "common/parse_retry_after.ftl"/>

${javaHelper.createAllResponseResultGetters(
  javaHelper.removeErrorResponseCodes(responseCodes))}

}