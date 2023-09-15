    <#list classVariables as var>
    private <#if var.isRequired() == true>final </#if>${javaHelper.getType(var)} ${var.getInternalName()?uncap_first};
    </#list>