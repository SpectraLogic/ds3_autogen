<#include "../copyright.ftl"/>

package ${packageName};

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
<#include "../imports.ftl"/>

<#if nameToMarshal??>
@JacksonXmlRootElement(namespace = "${nameToMarshal}")
</#if>
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
    public int hashCode() {
        return java.util.Objects.hash(id, inCache, latest, length, name, offset, physicalPlacement, version);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof ${name})) {
            return false;
        }

        final ${name} bulkObject = (${name}) obj;

        return (((this.getId() == null) && (bulkObject.getId() == null))
                || ((this.getId() != null) && (bulkObject.getId() != null) && this.getId().equals(bulkObject.getId())))
                && this.getInCache().equals(bulkObject.getInCache())
                && this.getLatest() == bulkObject.getLatest()
                && this.getLength() == bulkObject.getLength()
                && this.getName().equals(bulkObject.getName())
                && this.getOffset() == bulkObject.getOffset()
                && this.getPhysicalPlacement() == bulkObject.getPhysicalPlacement()
                && this.getVersion() == bulkObject.getVersion();
    }
}