<#include "../copyright.ftl"/>

package ${packageName};

<#include "common/response_imports.ftl"/>
import com.spectralogic.ds3client.commands.interfaces.MetadataImpl;
import com.spectralogic.ds3client.networking.Metadata;
import java.nio.channels.WritableByteChannel;
import com.spectralogic.ds3client.utils.IOUtils;
import com.spectralogic.ds3client.utils.PerformanceUtils;
import com.spectralogic.ds3client.exceptions.ContentLengthNotMatchException;

public class ${name} extends AbstractResponse {

    private Metadata metadata;
    private long objectSize;

    public ${name}(final WebResponse response, final WritableByteChannel destinationChannel, final int bufferSize, final String objName) throws IOException {
        super(response);
        download(destinationChannel, bufferSize, objName);
    }

    @Override
    protected void processResponse() throws IOException {
        this.checkStatusCode(${helper.getResponseCodes(responseCodes)});
        this.metadata = new MetadataImpl(this.getResponse().getHeaders());
        this.objectSize = getSizeFromHeaders(this.getResponse().getHeaders());
    }

    protected void download(final WritableByteChannel destinationChannel, final int bufferSize, final String objName) throws IOException {
        try (
                final WebResponse response = this.getResponse();
                final InputStream responseStream = response.getResponseStream()) {
            final long startTime = PerformanceUtils.getCurrentTime();
            final long totalBytes = IOUtils.copy(responseStream, destinationChannel, bufferSize);
            destinationChannel.close();
            final long endTime = PerformanceUtils.getCurrentTime();

            if (this.objectSize != -1 && totalBytes != this.objectSize) {
                throw new ContentLengthNotMatchException(objName, objectSize, totalBytes);
            }

            PerformanceUtils.logMbps(startTime, endTime, totalBytes, objName, false);
        }
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public long getObjectSize() {
        return objectSize;
    }
}