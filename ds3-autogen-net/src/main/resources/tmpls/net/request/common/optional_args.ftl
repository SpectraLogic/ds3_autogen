        <#list optionalArgs as arg>
        private ${arg.getNetType()} _${arg.getName()?uncap_first};
        public ${arg.getNetType()} ${arg.getName()?cap_first}
        {
            get { return _${arg.getName()?uncap_first}; }
            set { With${arg.getName()?cap_first}(value); }
        }
        </#list>

        <#list withConstructors as arg>
        ${arg.documentation}
        public ${name} With${arg.getName()?cap_first}(${arg.getNetType()} ${arg.getName()?uncap_first})
        {
            this._${arg.getName()?uncap_first} = ${netHelper.paramAssignmentRightValue(arg)};
            if (${arg.getName()?uncap_first} != null)
            {
                this.QueryParams.Add("${netHelper.camelToUnderscore(arg.getName())}", ${netHelper.argToString(arg)});
            }
            else
            {
                this.QueryParams.Remove("${netHelper.camelToUnderscore(arg.getName())}");
            }
            return this;
        }

        </#list>