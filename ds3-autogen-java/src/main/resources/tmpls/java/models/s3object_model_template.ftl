<#include "../copyright.ftl"/>

package ${packageName};

import java.util.Objects;
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
        return Objects.hash(bucketId, name, id, latest, creationDate, type);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof ${name})) {
            return false;
        }

        final ${name} s3obj = (${name})obj;

        return this.bucketId.equals(s3obj.getBucketId())
                && this.creationDate.equals(s3obj.getCreationDate())
                && this.id.equals(s3obj.getId())
                && this.latest == s3obj.getLatest()
                && this.name.equals(s3obj.getName())
                && this.type == s3obj.getType();
    }
}