<#include "../copyright.tmpl"/>

package ${packageName};

<#include "common/import_abstract_request.tmpl"/>
import com.spectralogic.ds3client.HttpVerb;
import com.spectralogic.ds3client.models.bulk.Ds3Object;
import com.spectralogic.ds3client.models.bulk.Ds3ObjectList;
import com.spectralogic.ds3client.serializer.XmlOutput;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
<#include "../imports.tmpl"/>

public class ${name} extends AbstractRequest {

    // Variables
    private final List<Ds3Object> objects;
    <#include "common/variables.tmpl"/>

    // Constructor
    public ${name}(${javaHelper.constructorArgs(
                     helper.addArgument(
                     helper.removeVoidArguments(requiredArguments), "Objects", "List<Ds3Object>"))}) {
        this.objects = objects;
        <#list helper.removeVoidArguments(requiredArguments) as arg>
        this.${arg.getName()?uncap_first} = ${arg.getName()?uncap_first};
        </#list>
        <#if operation??>
        this.getQueryParams().put("operation", "${operation.toString()?lower_case}");
        </#if>
<#include "common/constructor_get_query_params.tmpl"/>
    }

    <#include "common/with_constructors.tmpl"/>

    public InputStream getContentStream() {
        final Ds3ObjectList objects = new Ds3ObjectList();
        objects.setObjects(this.objects);

        ${javaHelper.toXmlLine("xmlOutput", "objects", operation)}

        final byte[] stringBytes = xmlOutput.getBytes();
        return new ByteArrayInputStream(stringBytes);
    }

    <#include "common/getters_verb_path.tmpl"/>

    <#include "common/getters.tmpl"/>

    ${javaHelper.createGetter("Objects", "List<Ds3Object>")}
}