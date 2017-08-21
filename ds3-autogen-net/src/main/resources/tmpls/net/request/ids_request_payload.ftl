<#include "../common/copyright.ftl" />

using System.Collections.Generic;

namespace Ds3.Calls
{
    public class ${name} : AbstractIdsPayloadRequest
    {
        <#include "common/required_args.ftl" />

        <#include "common/optional_args.ftl" />

        <#list constructors as constructor>
        ${constructor.documentation}
        public ${name}(${netHelper.constructor(constructor.constructorArgs)}) : base(ids)
        {
            <#list constructor.constructorArgs as arg>
            <#if arg.getName() != "Ids">
            this.${arg.getName()?cap_first} = ${netHelper.paramAssignmentRightValue(arg)};
            </#if>
            </#list>
            <#if constructor.operation??>
            this.QueryParams.Add("operation", "${constructor.operation.toString()?lower_case}");
            </#if>
            <#include "common/add_query_params.ftl" />

        }
        </#list>

        <#include "common/http_verb_and_path.ftl" />
    }
}