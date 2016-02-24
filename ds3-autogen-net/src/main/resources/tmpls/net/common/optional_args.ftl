        <#list optionalArgs as arg>
        private ${netHelper.getNullableType(arg)} _${arg.getName()?uncap_first};
        public ${netHelper.getNullableType(arg)} ${arg.getName()?cap_first}
        {
            get { return _${arg.getName()?uncap_first}; }
            set { With${arg.getName()?cap_first}(value); }
        }

        public ${name} With${arg.getName()?cap_first}(${netHelper.getNullableType(arg)} ${arg.getName()?uncap_first})
        {
            this._${arg.getName()?uncap_first} = ${arg.getName()?uncap_first};
            if (${arg.getName()?uncap_first} != null) {
                this.QueryParams.Add("${netHelper.camelToHyphen(arg.getName())}", ${netHelper.argToString(arg)});
            }
            else
            {
                this.QueryParams.Remove("${netHelper.camelToHyphen(arg.getName())}");
            }
            return this;
        }
        </#list>