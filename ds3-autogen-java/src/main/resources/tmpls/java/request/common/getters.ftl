    <#list classVariables as var>
    public ${javaHelper.getType(var)} get${var.getName()?cap_first}() {
        return this.${var.getInternalName()?uncap_first};
    }

    </#list>