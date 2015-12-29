<#include "../copyright.ftl"/>

package ${packageName};

<#include "common/import_abstract_request.ftl"/>
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3client.HttpVerb;
import com.spectralogic.ds3client.models.Range;
import org.apache.http.entity.ContentType;
import java.nio.channels.WritableByteChannel;
import java.util.Collection;
<#include "../imports.ftl"/>
<#include "common/checksum_import.ftl"/>

public class ${name} extends AbstractRequest {

    // Variables
    <#include "common/variables.ftl"/>
    private final WritableByteChannel channel;
    private ImmutableCollection<Range> byteRanges = null;
<#include "common/checksum_variables.ftl"/>

    // Constructor

<#if helper.containsArgument(optionalArguments, "Job")>
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
</#if>

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

        <#if helper.containsArgument(optionalArguments, "Job")>
        this.getQueryParams().put("job", job.toString());
        </#if>
        <#if helper.containsArgument(optionalArguments, "Offset")>
        this.getQueryParams().put("offset", Long.toString(offset));
        </#if>
    }

    <#include "common/with_constructors.ftl"/>

<#include "common/checksum_constructor_getter.ftl"/>

    /**
     * Sets a Range of bytes that should be retrieved from the object in the
     * format: 'Range: bytes=[start]-[end],...'.
     */
    public ${name} withByteRanges(final Range... byteRanges) {
        if (byteRanges != null) {
            this.setRanges(ImmutableList.copyOf(byteRanges));
        }
        return this;
    }

    public ${name} withByteRanges(final Collection<Range> byteRanges) {
        if (byteRanges != null) {
            this.setRanges(ImmutableList.copyOf(byteRanges));
        }
        return this;
    }

    private void setRanges(final ImmutableList<Range> byteRanges) {
        this.byteRanges = byteRanges;
        if (this.getHeaders().containsKey("Range")) {
            this.getHeaders().removeAll("Range");
        }
        this.getHeaders().put("Range", buildRangeHeaderText(byteRanges));
    }

    private static String buildRangeHeaderText(final ImmutableList<Range> byteRanges) {
        final ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (final Range range : byteRanges) {
            builder.add(String.format("%d-%d", range.getStart(), range.getEnd()));
        }
        final Joiner stringJoiner = Joiner.on(",");
        return "bytes=" + stringJoiner.join(builder.build());
    }

    <#include "common/getters_verb_path.ftl"/>

    @Override
    public String getContentType() {
        return ContentType.APPLICATION_OCTET_STREAM.toString();
    }

    <#include "common/getters.ftl"/>

    ${javaHelper.createGetter("ByteRanges", "Collection<Range>")}

    ${javaHelper.createGetter("Channel", "WritableByteChannel")}
}