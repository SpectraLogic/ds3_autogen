<#include "../copyright.ftl"/>

package ${packageName};

import com.spectralogic.ds3client.networking.HttpVerb;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import com.spectralogic.ds3client.utils.Guard;
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
    public InputStream getStream() {
        if (Guard.isStringNullOrEmpty(requestPayload)) {
            return null;
        }
        final byte[] stringBytes = requestPayload.getBytes(Charset.forName("UTF-8"));
        this.size = stringBytes.length;
        return new ByteArrayInputStream(stringBytes);
    }

    @Override
    public long getSize() {
        return this.size;
    }

    <#include "common/getters.ftl"/>
}
