<#include "../copyright.ftl"/>

package ${packageName};

<#include "common/import_abstract_request.ftl"/>
import com.spectralogic.ds3client.HttpVerb;
import com.spectralogic.ds3client.models.bulk.Ds3Object;
import com.spectralogic.ds3client.models.bulk.Ds3ObjectList;
import com.spectralogic.ds3client.serializer.XmlOutput;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
<#include "../imports.ftl"/>

public class ${name} extends AbstractRequest {

    // Variables
    private final List<Ds3Object> objects;
    <#include "common/variables.ftl"/>

    // Constructor
    public ${name}(${javaHelper.constructorArgs(
                     helper.addArgument(constructorArguments, "Objects", "List<Ds3Object>"))}) {
        this.objects = objects;
        <#list constructorArguments as arg>
        this.${arg.getName()?uncap_first} = ${arg.getName()?uncap_first};
        </#list>
        <#if operation??>
        this.getQueryParams().put("operation", "${operation.toString()?lower_case}");
        </#if>
<#include "common/constructor_get_query_params.ftl"/>
    }

    <#include "common/with_constructors.ftl"/>

    public InputStream getContentStream() {
        final Ds3ObjectList objects = new Ds3ObjectList();
        objects.setObjects(this.objects);

        ${javaHelper.toXmlLine("xmlOutput", "objects", operation)}

        final byte[] stringBytes = xmlOutput.getBytes();
        return new ByteArrayInputStream(stringBytes);
    }

    <#include "common/getters_verb_path.ftl"/>

    <#include "common/getters.ftl"/>

    ${javaHelper.createGetter("Objects", "List<Ds3Object>")}
}