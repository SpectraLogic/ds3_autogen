<#include "../copyright.ftl"/>

package ${packageName};

<#include "common/import_abstract_request.ftl"/>
import com.spectralogic.ds3client.HttpVerb;
import org.apache.http.entity.ContentType;
import java.nio.channels.WritableByteChannel;
<#include "../imports.ftl"/>

public class ${name} extends AbstractRequest {

    public static class Range {
        private final long start;
        private final long end;

        public Range(final long start, final long end) {
            this.start = start;
            this.end = end;
        }

        public long getStart() {
            return this.start;
        }

        public long getEnd() {
            return this.end;
        }
    }

    // Variables
    <#include "common/variables.ftl"/>
    private final WritableByteChannel channel;
    private Range byteRange = null;

    // Constructor

    /**
     * @deprecated use {@link #${name}(${javaHelper.argTypeList(
                                         helper.addArgument(
                                         helper.addArgument(requiredArguments, optionalArguments), "Channel", "WritableByteChannel"))})} instead
     */
    @Deprecated
    public ${name}(${javaHelper.constructorArgs(
                     helper.addArgument(requiredArguments, "Channel", "WritableByteChannel"))}) {
        <#list requiredArguments as arg>
        this.${arg.getName()?uncap_first} = ${arg.getName()?uncap_first};
        </#list>
        this.channel = channel;
    }

    public ${name}(${javaHelper.constructorArgs(
                     helper.addArgument(
                     helper.addArgument(requiredArguments, optionalArguments), "Channel", "WritableByteChannel"))}) {
        <#list requiredArguments as arg>
        this.${arg.getName()?uncap_first} = ${arg.getName()?uncap_first};
        </#list>
        <#list optionalArguments as arg>
        this.${arg.getName()?uncap_first} = ${arg.getName()?uncap_first};
        </#list>
        this.channel = channel;

        this.getQueryParams().put("job", job.toString());
        this.getQueryParams().put("offset", Long.toString(offset));
    }

    <#include "common/with_constructors.ftl"/>

    public ${name} withByteRange(final Range byteRange) {
        this.byteRange = byteRange;
        if (byteRange != null) {
            this.getHeaders().put("Range", buildRangeHeaderText(byteRange));
        }
        return this;
    }

    private static String buildRangeHeaderText(final Range byteRange) {
        return String.format("bytes=%d-%d", byteRange.getStart(), byteRange.getEnd());
    }

    <#include "common/getters_verb_path.ftl"/>

    @Override
    public String getContentType() {
        return ContentType.APPLICATION_OCTET_STREAM.toString();
    }

    <#include "common/getters.ftl"/>

    ${javaHelper.createGetter("ByteRange", "Range")}

    ${javaHelper.createGetter("Channel", "WritableByteChannel")}
}