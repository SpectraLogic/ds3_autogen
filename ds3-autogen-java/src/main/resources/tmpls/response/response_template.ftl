<#include "../copyright.ftl"/>

package ${packageName};

<#include "common/response_imports.ftl"/>

public class ${name} extends ${parentClass} {

${javaHelper.createAllResponseResultClassVars(responseCodes)}

<#include "common/response_constructor.ftl"/>

    @Override
    protected void processResponse() throws IOException {
        try {
            this.checkStatusCode(${helper.getResponseCodes(responseCodes)});

            switch (this.getStatusCode()) {
            <#list responseCodes as responseCode>
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

${javaHelper.createAllResponseResultGetters(responseCodes)}
}