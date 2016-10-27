<#include "../copyright.ftl"/>

package ${packageName};

<#include "../imports.ftl"/>

public class ${name} extends ${parentClass}<${responseName}> {
    private final int[] expectedStatusCodes = new int[]{${expectedStatusCodes}};

    private final WritableByteChannel destinationChannel;
    private final int bufferSize;
    private final String objName;

    public ${name}(final WritableByteChannel destinationChannel,
                                   final int bufferSize,
                                   final String objName) {
        this.destinationChannel = destinationChannel;
        this.bufferSize = bufferSize;
        this.objName = objName;
    }

    @Override
    public ${responseName} parseXmlResponse(final WebResponse response) throws IOException {
        final int statusCode = response.getStatusCode();
        if (ResponseParserUtils.validateStatusCode(statusCode, expectedStatusCodes)) {
            final Metadata metadata = new MetadataImpl(this.getResponse().getHeaders());
            final  long objectSize = getSizeFromHeaders(this.getResponse().getHeaders());
            download(objectSize, this.getResponse());
            return new GetObjectResponse(metadata, objectSize, this.getChecksum(), this.getChecksumType());
        }

        throw ResponseParserUtils.createFailedRequest(response, expectedStatusCodes);
    }

    protected void download(final long objectSize, final WebResponse response) throws IOException {
        try (final InputStream responseStream = response.getResponseStream()) {
            final long startTime = PerformanceUtils.getCurrentTime();
            final long totalBytes = IOUtils.copy(responseStream, destinationChannel, bufferSize, objName, false);
            destinationChannel.close();
            final long endTime = PerformanceUtils.getCurrentTime();

            if (objectSize != -1 && totalBytes != objectSize) {
                throw new ContentLengthNotMatchException(objName, objectSize, totalBytes);
            }

            PerformanceUtils.logMbps(startTime, endTime, totalBytes, objName, false);
        }
    }
}