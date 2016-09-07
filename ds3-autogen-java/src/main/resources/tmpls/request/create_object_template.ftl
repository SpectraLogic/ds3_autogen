<#include "../copyright.ftl"/>

package ${packageName};

import com.spectralogic.ds3client.networking.HttpVerb;
import java.io.InputStream;
import java.nio.channels.SeekableByteChannel;
import com.spectralogic.ds3client.utils.SeekableByteChannelInputStream;
import static com.spectralogic.ds3client.utils.Guard.isStringNullOrEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
<#include "../imports.ftl"/>
<#include "common/checksum_import.ftl"/>

public class ${name} extends ${parentClass} {

    final static private Logger LOG = LoggerFactory.getLogger(PutObjectRequest.class);

    // Variables
    public final static String AMZ_META_HEADER = "x-amz-meta-";

    private final InputStream stream;
    <#include "common/variables.ftl"/>
    private SeekableByteChannel channel;
<#include "common/checksum_variables.ftl"/>

    // Constructor
    <#list constructors as constructor>
    <#if constructor.isDeprecated() == true>
    /** @deprecated use {@link #${name}(String, String, SeekableByteChannel, UUID, long, long)} instead */
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

    public ${name} withMetaData(final String key, final String value) {
        if (isStringNullOrEmpty(value)) {
            LOG.warn("Key has not been added to metadata because value was null or empty: {}", key);
            return this;
        }
        final String modifiedKey;
        if (!key.toLowerCase().startsWith(AMZ_META_HEADER)){
            modifiedKey = AMZ_META_HEADER + key;
        } else {
            modifiedKey = key;
        }
        this.getHeaders().put(modifiedKey, value);
        return this;
    }

    <#include "common/getters_verb_path.ftl"/>

    @Override
    ${javaHelper.createGetter("Size", "long")}

    @Override
    ${javaHelper.createGetter("Stream", "InputStream")}

    ${javaHelper.createGetter("Channel", "SeekableByteChannel")}

    <#list javaHelper.removeVariable(classVariables, "Size") as var>
    public ${javaHelper.getType(var)} get${var.getName()?cap_first}() {
        return this.${var.getName()?uncap_first};
    }

    </#list>

}