<#include "../copyright.ftl"/>

package ${packageName};

<#include "../imports.ftl"/>

public class ${name} extends ${parentClass}<${responseName}> {
    private final int[] expectedStatusCodes = new int[]{${expectedStatusCodes}};

    @Override
    public ${responseName} parseXmlResponse(final WebResponse response, final ReadableByteChannel blockingByteChannel) throws IOException {
        final int statusCode = response.statusCode();
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

    private static int parseRetryAfter(final WebResponse webResponse) {
        final String retryAfter = webResponse.getHeaders().get("Retry-After").get(0);
        if (retryAfter == null) {
            throw new RetryAfterExpectedException();
        }
        return Integer.parseInt(retryAfter);
    }
}