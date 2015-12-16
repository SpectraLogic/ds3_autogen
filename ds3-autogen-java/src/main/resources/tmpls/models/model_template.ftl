<#include "../copyright.ftl"/>

package ${packageName};

import com.fasterxml.jackson.annotation.JsonProperty;
<#include "../imports.ftl"/>

public class ${name} {

    // Variables
<#list elements as elmt>
${javaHelper.getModelVariable(elmt)}

</#list>
    // Constructor
    public ${name}(${javaHelper.getModelConstructorArgs(elements)}) {
        <#list elements as elmt>
        this.${elmt.getName()?uncap_first} = ${elmt.getName()?uncap_first};
        </#list>
    }

    // Getters and Setters
<#include "common/getters_setters_template.ftl"/>
}