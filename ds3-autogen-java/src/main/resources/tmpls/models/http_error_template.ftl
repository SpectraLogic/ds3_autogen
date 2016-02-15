<#include "../copyright.ftl"/>

package ${packageName};

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
<#include "../imports.ftl"/>

<#if nameToMarshal??>
@JacksonXmlRootElement(namespace = "${nameToMarshal}")
</#if>
@JsonIgnoreProperties(ignoreUnknown = true)
public class ${name} {

    // Variables
<#list elements as elmt>
${javaHelper.getModelVariable(elmt)}

</#list>
    // Constructor
    public ${name}() {
        //pass
    }

    // Getters and Setters
<#include "common/getters_setters_template.ftl"/>

    @Override
    public String toString() {
        return "Status Code (" + code + ") Message: " + message;
    }
}