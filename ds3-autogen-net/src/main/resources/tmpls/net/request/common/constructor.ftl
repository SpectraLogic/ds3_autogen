        <#list constructors as constructor>
        ${constructor.documentation}
        public ${name}(${netHelper.constructor(constructor.constructorArgs)})
        {
            <#list constructor.constructorArgs as arg>
            this.${arg.getName()?cap_first} = ${netHelper.paramAssignmentRightValue(arg)};
            </#list>
            <#if constructor.operation??>
            this.QueryParams.Add("operation", "${constructor.operation.toString()?lower_case}");
            </#if>
            <#include "add_query_params.ftl" />

        }
        </#list>
