<#include "../copyright.ftl"/>

package ${packageName};

import com.spectralogic.ds3client.networking.HttpVerb;
<#include "../imports.ftl"/>

import java.util.List;

public class ${name} extends ${parentClass} {

    // Variables
    <#include "common/variables.ftl"/>

    // Constructor
    <#list constructors as constructor>
    ${constructor.documentation}
    public ${name}(final List<String> ids) {
        super(ids);
        <#list constructor.getAssignments() as arg>
        this.${arg.getName()?uncap_first} = ${javaHelper.paramAssignmentRHS(arg)};
        </#list>
<#include "common/add_query_params.ftl"/>
        <#list constructor.getAdditionalLines() as line>
        ${line}
        </#list>
    }
    </#list>

    <#include "common/with_constructors.ftl"/>

    <#include "common/getters_verb_path.ftl"/>

    <#include "common/getters.ftl"/>
}