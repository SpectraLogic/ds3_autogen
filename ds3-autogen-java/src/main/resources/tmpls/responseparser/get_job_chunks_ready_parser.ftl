<#include "../copyright.ftl"/>

package ${packageName};

import com.spectralogic.ds3client.exceptions.RetryAfterExpectedException;
<#include "../imports.ftl"/>

import static com.spectralogic.ds3client.utils.Guard.isNullOrEmpty;

public class ${name} extends ${parentClass}<${responseName}> {
    private final int[] expectedStatusCodes = new int[]{${expectedStatusCodes}};

    @Override
    public ${responseName} parseXmlResponse(final WebResponse response, final ReadableByteChannel blockingByteChannel) throws IOException {
        final int statusCode = response.getStatusCode();
        if (ResponseParserUtils.validateStatusCode(statusCode, expectedStatusCodes)) {
            switch (statusCode) {
            <#list responseCodes as responseCode>
            case ${responseCode.code}:
                ${responseCode.processingCode}
            </#list>
            default:
                assert false: "validateStatusCode should have made it impossible to reach this line";
            }
        }

        throw ResponseParserUtils.createFailedRequest(response, blockingByteChannel, expectedStatusCodes);
    }

<#include "common/parse_retry_after.ftl"/>
}