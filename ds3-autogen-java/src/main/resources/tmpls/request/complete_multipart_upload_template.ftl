<#include "../copyright.ftl"/>

package ${packageName};

import com.spectralogic.ds3client.networking.HttpVerb;
import com.spectralogic.ds3client.models.multipart.CompleteMultipartUpload;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import com.spectralogic.ds3client.serializer.XmlOutput;
<#include "../imports.ftl"/>

public class ${name} extends ${parentClass} {

    // Variables
    <#include "common/variables.ftl"/>
    private long size = 0;

    // Constructor
    <#include "common/constructor.ftl"/>

    <#include "common/with_constructors.ftl"/>

    <#include "common/getters_verb_path.ftl"/>

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public InputStream getStream() {
        if (requestPayload == null) {
            return null;
        }

        final String xmlOutput = XmlOutput.toXml(requestPayload);

        final byte[] stringBytes = xmlOutput.getBytes();
        this.size = stringBytes.length;
        return new ByteArrayInputStream(stringBytes);
    }

    <#include "common/getters.ftl"/>
}