        public ${name}(${netHelper.constructor(constructorArgs)}) {
            <#list constructorArgs as arg>
            this.${arg.getName()?cap_first} = ${arg.getName()?uncap_first};
            </#list>
            <#if operation??>
            this.QueryParams.Add("operation", "${operation.toString()?lower_case}");
            </#if>
        }
