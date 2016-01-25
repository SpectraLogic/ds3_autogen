<#include "../copyright.ftl"/>

package ${packageName};

<#include "common/response_imports.ftl"/>
import com.spectralogic.ds3client.commands.RetryAfterExpectedException;

public class ${name} extends AbstractResponse {

${javaHelper.createAllResponseResultClassVars(responseCodes)}

    public enum Status {
        AVAILABLE, RETRYLATER
    }

<#include "common/allocated_retrylater_status_types_getters.ftl"/>

<#include "common/response_constructor.ftl"/>

    @Override
    protected void processResponse() throws IOException {
        try (final WebResponse webResponse = this.getResponse()) {
            this.checkStatusCode(200);

            switch (this.getStatusCode()) {
            case 200:
                try (final InputStream content = getResponse().getResponseStream()) {
                    this.jobWithChunksContainerApiBeanResult = XmlOutput.fromXml(content, JobWithChunksContainerApiBean.class);
                    if (this.jobWithChunksContainerApiBeanResult == null) {
                        this.status = Status.RETRYLATER;
                        this.retryAfterSeconds = parseRetryAfter(webResponse);
                    } else {
                        this.status = Status.AVAILABLE;
                    }
                }
                break;
            default:
                assert false : "checkStatusCode should have made it impossible to reach this line.";
            }
        } finally {
            this.getResponse().close();
        }
    }

<#include "common/parse_retry_after.ftl"/>

${javaHelper.createAllResponseResultGetters(responseCodes)}

}