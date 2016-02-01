<#include "../copyright.ftl"/>

package ${packageName};

import java.util.List;
import com.spectralogic.ds3client.BulkCommand;
import com.spectralogic.ds3client.models.bulk.Ds3Object;
import com.spectralogic.ds3client.serializer.XmlProcessingException;
<#include "../imports.ftl"/>

public class ${name} extends BulkRequest {

    <#if helper.containsArgument(optionalArguments, "MaxUploadSize") == true>
    private static final String MAX_UPLOAD_SIZE_IN_BYTES = "100000000000";
    public static final int MIN_UPLOAD_SIZE_IN_BYTES = 10485760;
    </#if>

    <#include "common/variables.ftl"/>

    // Constructor
    <#list constructors as constructor>
    public ${name}(${javaHelper.constructorArgs(constructor.getParameters())}) throws XmlProcessingException {
        super(bucketName, objects);
        <#list constructor.getAssignments() as arg>
        this.${arg.getName()?uncap_first} = ${arg.getName()?uncap_first};
        </#list>
        <#include "common/add_query_params.ftl"/>
    }
    </#list>

    <#list optionalArguments as arg>
${javaHelper.createWithConstructorBulk(arg, name)}
    </#list>

    <#include "common/getters.ftl"/>

    @Override
    public BulkCommand getCommand() {
        return BulkCommand.${helper.getBulkVerb(operation)};
    }
}