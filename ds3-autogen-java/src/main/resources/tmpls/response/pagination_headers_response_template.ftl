<#include "../copyright.ftl"/>

package ${packageName};

<#include "common/response_imports.ftl"/>

public class ${name} extends AbstractResponse {

${javaHelper.createAllResponseResultClassVars(responseCodes)}
    private Integer pagingTruncated;
    private Integer pagingTotalResultCount;

<#include "common/response_constructor.ftl"/>

    @Override
    protected void processResponse() throws IOException {
        try {
            this.checkStatusCode(${helper.getResponseCodes(responseCodes)});

            switch (this.getStatusCode()) {
            <#list responseCodes as responseCode>
            case ${responseCode.getCode()}:
                ${javaHelper.processPaginationResponseCodeLines(responseCode, 4)}
            </#list>
            default:
                assert false : "checkStatusCode should have made it impossible to reach this line.";
            }
        } finally {
            this.getResponse().close();
        }
    }

    public Integer getPagingTruncated() {
        return pagingTruncated;
    }

    public Integer getPagingTotalResultCount() {
        return pagingTotalResultCount;
    }

${javaHelper.createAllResponseResultGetters(responseCodes)}
}