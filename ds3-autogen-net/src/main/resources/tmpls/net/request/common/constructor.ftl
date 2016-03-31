        public ${name}(${netHelper.constructor(constructorArgs)}) {
            <#list constructorArgs as arg>
            this.${arg.getName()?cap_first} = ${netHelper.paramAssignmentRightValue(arg)};
            </#list>
            <#if operation??>
            this.QueryParams.Add("operation", "${operation.toString()?lower_case}");
            </#if>
            <#include "add_query_params.ftl" />

        }
