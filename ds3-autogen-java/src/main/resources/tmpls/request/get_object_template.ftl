<#include "../copyright.ftl"/>

package ${packageName};

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3client.networking.HttpVerb;
import com.spectralogic.ds3client.models.common.Range;
import org.apache.http.entity.ContentType;
import java.nio.channels.WritableByteChannel;
import java.util.Collection;
<#include "../imports.ftl"/>
<#include "common/checksum_import.ftl"/>

public class ${name} extends AbstractRequest {

    // Variables
    <#include "common/variables.ftl"/>
    private ImmutableCollection<Range> byteRanges = null;
<#include "common/checksum_variables.ftl"/>

    // Constructor
    <#list constructors as constructor>
    <#if constructor.isDeprecated() == true>
    /** @deprecated use {@link #GetObjectRequest(String, String, WritableByteChannel, UUID, long)} instead */
    @Deprecated
    </#if>
    public ${name}(${javaHelper.constructorArgs(constructor.getParameters())}) {
        <#list constructor.getAssignments() as arg>
        this.${arg.getName()?uncap_first} = ${javaHelper.paramAssignmentRHS(arg)};
        </#list>
        <#list constructor.getAdditionalLines() as line>
        ${line}
        </#list>
        <#include "common/add_query_params.ftl"/>

    }

    </#list>

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

}