<#include "../copyright.ftl"/>

package ${packageName};

import com.spectralogic.ds3client.BulkCommand;
import com.spectralogic.ds3client.models.bulk.Ds3Object;
<#include "../imports.ftl"/>

public class ${name} extends ${parentClass} {

    <#if helper.containsArgument(optionalArguments, "MaxUploadSize") == true>
    private static final String MAX_UPLOAD_SIZE_IN_BYTES = "100000000000";
    public static final int MIN_UPLOAD_SIZE_IN_BYTES = 10485760;
    </#if>

    <#include "common/variables.ftl"/>

    // Constructor
    <#list constructors as constructor>
    ${constructor.documentation}
    public ${name}(${javaHelper.constructorArgs(constructor.getParameters())}) {
        super(bucketName, objects);
        <#list constructor.getAssignments() as arg>
        this.${arg.getName()?uncap_first} = ${javaHelper.paramAssignmentRHS(arg)};
        </#list>
        <#include "common/add_query_params.ftl"/>
    }
    </#list>

    <#include "common/with_constructors.ftl"/>

    <#include "common/getters.ftl"/>

    @Override
    public BulkCommand getCommand() {
        return BulkCommand.${helper.getBulkVerb(operation)};
    }
}