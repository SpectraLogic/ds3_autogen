<#include "../copyright.ftl"/>

package ${packageName};

<#include "common/response_imports.ftl"/>
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ${name} extends AbstractResponse {

    private static final Logger LOG = LoggerFactory.getLogger(${name}.class);
    private Status status;

    public enum Status {
        EXISTS, DOESNTEXIST, NOTAUTHORIZED, UNKNOWN
    }

    ${javaHelper.createGetter("Status", "Status")}

${javaHelper.createAllResponseResultClassVars(responseCodes)}

<#include "common/response_constructor.ftl"/>

    @Override
    protected void processResponse() throws IOException {
        try {
            this.checkStatusCode(200, 403, 404);
            this.setStatus(this.getStatusCode());
        } finally {
            this.getResponse().close();
        }
    }

    private void setStatus(final int statusCode) {
        switch(statusCode) {
            case 200: this.status = Status.EXISTS; break;
            case 403: this.status = Status.NOTAUTHORIZED; break;
            case 404: this.status = Status.DOESNTEXIST; break;
            default: {
                LOG.error("Unexpected status code: {}", Integer.toString(statusCode));
                this.status = Status.UNKNOWN;
                break;
            }
        }
    }

${javaHelper.createAllResponseResultGetters(responseCodes)}
}