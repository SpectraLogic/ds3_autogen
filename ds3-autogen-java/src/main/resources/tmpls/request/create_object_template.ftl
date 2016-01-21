<#include "../copyright.ftl"/>

package ${packageName};

import com.spectralogic.ds3client.HttpVerb;
import java.io.InputStream;
import java.nio.channels.SeekableByteChannel;
<#include "../imports.ftl"/>
<#include "common/checksum_import.ftl"/>

public class ${name} extends AbstractRequest {

    // Variables
    public final static String AMZ_META_HEADER = "x-amz-meta-";

    private final InputStream stream;
    <#include "common/variables.ftl"/>
    private SeekableByteChannel channel;
<#include "common/checksum_variables.ftl"/>

    // Constructor

    /**
     * @deprecated use {@link #${name}(${javaHelper.argTypeList(
                                         helper.addArgument(
                                         helper.addArgument(constructorArguments, optionalArguments), "Channel", "SeekableByteChannel"))}) instead
     */
    @Deprecated
    public ${name}(${javaHelper.constructorArgs(
                     helper.addArgument(constructorArguments, "Channel", "SeekableByteChannel"))}) {
        <#list constructorArguments as arg>
        this.${arg.getName()?uncap_first} = ${arg.getName()?uncap_first};
        </#list>
        this.channel = channel;
        this.stream = new SeekableByteChannelInputStream(channel);
    }

    public ${name}(${javaHelper.constructorArgs(
                     helper.addArgument(
                     helper.addArgument(constructorArguments, optionalArguments), "Channel", "SeekableByteChannel"))}) {
        this(${javaHelper.modifiedArgNameList(
               helper.addArgument(
               helper.addArgument(
                   constructorArguments, optionalArguments), "Stream", "InputStream"), "Stream", "new SeekableByteChannelInputStream(channel)")});

        this.channel = channel;
    }

    public ${name}(${javaHelper.constructorArgs(
                     helper.addArgument(
                     helper.addArgument(
                         constructorArguments, optionalArguments), "Stream", "InputStream"))}) {
            <#list constructorArguments as arg>
            this.${arg.getName()?uncap_first} = ${arg.getName()?uncap_first};
            </#list>
            <#list optionalArguments as arg>
            this.${arg.getName()?uncap_first} = ${arg.getName()?uncap_first};
            </#list>
            this.stream = stream;

            <#list optionalArguments as arg>
            ${javaHelper.putQueryParamLine(arg)}
            </#list>
        }

    <#include "common/with_constructors.ftl"/>

<#include "common/checksum_constructor_getter.ftl"/>

	public ${name} withMetaData(final String key, final String value) {
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