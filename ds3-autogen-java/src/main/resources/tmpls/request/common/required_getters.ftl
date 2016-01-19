    <#list constructorArguments as arg>
    public ${javaHelper.getType(arg)} get${arg.getName()?cap_first}() {
        return this.${arg.getName()?uncap_first};
    }

    </#list>