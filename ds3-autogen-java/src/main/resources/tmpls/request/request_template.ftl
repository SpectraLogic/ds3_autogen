<#include "../copyright.ftl"/>

package ${packageName};

<#include "common/import_abstract_request.ftl"/>
import com.spectralogic.ds3client.HttpVerb;
<#include "../imports.ftl"/>

public class ${name} extends AbstractRequest {

    // Variables
    <#include "common/variables.ftl"/>

    // Constructor
    <#include "common/constructor.ftl"/>

    <#include "common/with_constructors.ftl"/>

    <#include "common/getters_verb_path.ftl"/>

    <#include "common/getters.ftl"/>
}