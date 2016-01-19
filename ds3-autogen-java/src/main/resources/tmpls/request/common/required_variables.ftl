    <#list constructorArguments as arg>
    private final ${javaHelper.getType(arg)} ${arg.getName()?uncap_first};
    </#list>