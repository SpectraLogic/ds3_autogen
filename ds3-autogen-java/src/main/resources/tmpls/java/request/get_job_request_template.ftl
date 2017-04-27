<#include "../copyright.ftl"/>

package ${packageName};

import com.spectralogic.ds3client.networking.HttpVerb;
<#include "../imports.ftl"/>

public class ${name} extends ${parentClass} {

    // Variables
    <#include "common/variables.ftl"/>

    // Constructor
    <#include "common/constructor.ftl"/>

    <#include "common/with_constructors.ftl"/>

    <#include "common/getters_verb_path.ftl"/>

    <#include "common/getters.ftl"/>

    @Override
    public int hashCode() {
        return jobId.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (! (obj instanceof ${name})) return false;
        final ${name} other = (${name}) obj;
        return other.getJobId().equals(this.getJobId());
    }
}