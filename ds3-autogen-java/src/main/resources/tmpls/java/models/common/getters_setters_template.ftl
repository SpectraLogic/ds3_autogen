    <#list elements as elmt>
    public ${javaHelper.convertType(elmt)} get${elmt.getName()?cap_first}() {
        return this.${elmt.getInternalName()?uncap_first};
    }

    public void set${elmt.getName()?cap_first}(final ${javaHelper.convertType(elmt)} ${elmt.getInternalName()?uncap_first}) {
        this.${elmt.getInternalName()?uncap_first} = ${elmt.getInternalName()?uncap_first};
    }

    </#list>