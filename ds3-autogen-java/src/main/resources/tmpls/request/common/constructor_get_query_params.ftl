        <#list requiredArguments as arg>
        ${javaHelper.putQueryParamLine(arg)}
        </#list>