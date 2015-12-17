        public ${name}(${netHelper.constructor(requiredArgs)}) {
            <#list requiredArgs as arg>
            this.${arg.getName()?cap_first} = ${arg.getName()?uncap_first};
            </#list>
        }
