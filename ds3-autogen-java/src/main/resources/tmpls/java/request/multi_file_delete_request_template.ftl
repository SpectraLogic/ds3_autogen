<#include "../copyright.ftl"/>

package ${packageName};

import com.spectralogic.ds3client.networking.HttpVerb;
import com.spectralogic.ds3client.models.Contents;
import com.spectralogic.ds3client.models.delete.Delete;
import com.spectralogic.ds3client.models.delete.DeleteObject;
import com.spectralogic.ds3client.serializer.XmlOutput;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.Charset;
<#include "../imports.ftl"/>

public class ${name} extends ${parentClass} {

    // Variables
    private final List<String> objects;
<#include "common/variables.ftl"/>
    private boolean quiet = false;
    private long size;

    // Constructor
    <#include "common/constructor.ftl"/>

    private static List<String> contentsToString(final Iterable<Contents> objs) {
        final List<String> objKeyList = new ArrayList<>();
        for (final Contents obj : objs) {
            objKeyList.add(obj.getKey());
        }
        return objKeyList;
    }

    <#include "common/with_constructors.ftl"/>

    public ${name} withQuiet(final boolean quiet) {
        this.quiet = quiet;
        return this;
    }

    @Override
    public InputStream getStream() {

        final Delete delete = new Delete();
        delete.setQuiet(quiet);
        final List<DeleteObject> deleteObjects = new ArrayList<>();

        for(final String objName : objects) {
            deleteObjects.add(new DeleteObject(objName));
        }

        delete.setDeleteObjectList(deleteObjects);

        final String xmlOutput = XmlOutput.toXml(delete);
        final byte[] stringBytes = xmlOutput.getBytes(Charset.forName("UTF-8"));
        this.size = stringBytes.length;

        return new ByteArrayInputStream(stringBytes);
    }

    <#include "common/getters_verb_path.ftl"/>

    ${javaHelper.createGetter("Objects", "List<String>")}

    ${javaHelper.createGetter("Quiet", "boolean")}

<#include "common/getters.ftl"/>

    @Override
    ${javaHelper.createGetter("Size", "long")}
}