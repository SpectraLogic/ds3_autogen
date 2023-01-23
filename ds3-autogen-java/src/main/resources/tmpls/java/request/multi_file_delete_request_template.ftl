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
import java.nio.charset.StandardCharsets;
<#include "../imports.ftl"/>

public class ${name} extends ${parentClass} {

    // Variables
    private final List<DeleteObject> objects;
<#include "common/variables.ftl"/>
    private boolean quiet = false;
    private long size;

    // Constructor
    <#include "common/constructor.ftl"/>

    private static List<DeleteObject> contentsToDeleteObjects(final Iterable<Contents> objects) {
        final List<DeleteObject> objectsToDelete = new ArrayList<>();
        for (final Contents obj : objects) {
            objectsToDelete.add(new DeleteObject(obj.getKey(), obj.getVersionId()));
        }
        return objectsToDelete;
    }

    private static List<DeleteObject> namesToDeleteObjects(final Iterable<String> objNames) {
        final List<DeleteObject> objectsToDelete = new ArrayList<>();
        for (final String objName : objNames) {
            objectsToDelete.add(new DeleteObject(objName));
        }
        return objectsToDelete;
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
        delete.setDeleteObjectList(objects);

        final String xmlOutput = XmlOutput.toXml(delete);
        final byte[] stringBytes = xmlOutput.getBytes(StandardCharsets.UTF_8);
        this.size = stringBytes.length;

        return new ByteArrayInputStream(stringBytes);
    }

    <#include "common/getters_verb_path.ftl"/>

    ${javaHelper.createGetter("Objects", "List<DeleteObject>")}

    ${javaHelper.createGetter("Quiet", "boolean")}

<#include "common/getters.ftl"/>

    @Override
    ${javaHelper.createGetter("Size", "long")}
}