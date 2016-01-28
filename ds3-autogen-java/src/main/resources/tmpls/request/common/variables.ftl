    <#list classVariables as var>
    private <#if var.isRequired() == true>final </#if>${javaHelper.getType(var)} ${var.getName()?uncap_first};
    </#list>