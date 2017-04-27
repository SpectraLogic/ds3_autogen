<#include "../copyright.ftl"/>

package ${packageName};

<#include "../imports.ftl"/>

public class ${name} extends ${parentClass} {

    // Variables
<#include "common/variables.ftl"/>

    <#list constructors as constructor>
    ${constructor.documentation}
    public ${name}(${javaHelper.constructorArgs(constructor.getParameters())}) {
        super(notificationEndPoint);

        <#list constructor.getAssignments() as arg>
        this.${arg.getName()?uncap_first} = ${arg.getName()?uncap_first};
        </#list>
        <#include "common/add_query_params.ftl"/>

    }
    </#list>

<#include "common/with_constructors.ftl"/>

    @Override
    public String getPath() {
        return ${path};
    }

<#include "common/getters.ftl">
}