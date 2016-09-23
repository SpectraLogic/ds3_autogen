<#include "../copyright.ftl"/>

package ${packageName};

import com.spectralogic.ds3client.commands.interfaces.MetadataImpl;
<#include "../imports.ftl"/>

public class ${name} extends ${parentClass}<${responseName}> {
    private final int[] expectedStatusCodes = new int[]{${expectedStatusCodes}};

    @Override
    public ${responseName} parseXmlResponse(final WebResponse response, final ReadableByteChannel blockingByteChannel) throws IOException {
        final int statusCode = response.statusCode();
        if (ResponseParserUtils.validateStatusCode(statusCode, expectedStatusCodes)) {
            final Metadata metadata = new MetadataImpl(response.getHeaders());
            final long objectSize = getSizeFromHeaders(response.getHeaders());
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
}