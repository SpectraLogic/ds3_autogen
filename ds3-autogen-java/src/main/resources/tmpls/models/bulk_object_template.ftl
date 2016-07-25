<#include "../copyright.ftl"/>

package ${packageName};

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
<#include "../imports.ftl"/>

<#if javaHelper.stringHasContent(nameToMarshal)>
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

        return nullableEquals(this.getId(), bulkObject.getId())
                && nullableEquals(this.getInCache(), bulkObject.getInCache())
                && this.getLatest() == bulkObject.getLatest()
                && this.getLength() == bulkObject.getLength()
                && nullableEquals(this.getName(), bulkObject.getName())
                && this.getOffset() == bulkObject.getOffset()
                && this.getPhysicalPlacement() == bulkObject.getPhysicalPlacement()
                && this.getVersion() == bulkObject.getVersion();
    }

    /**
     * Tests if two objects are equal, and handles the case if either or both objects are null
     */
    protected static boolean nullableEquals(final Object obj1, final Object obj2) {
        return obj1 == null && obj2 == null || obj1 != null && obj2 != null && obj1.equals(obj2);
    }

    @Override
    public String toString() {
        return String.format("name = %s, offset = %d, length %d", name, offset, length);
    }
}