        <#list requiredArgs as arg>
        public ${netHelper.getType(arg)} ${arg.getName()?cap_first} { get; private set; }
        </#list>