<#include "../copyright.ftl"/>

package ${packageName};

<#include "../imports.ftl"/>

public class ${name} extends ${parentClass} {

    public enum Status { ALLOCATED, RETRYLATER }
<#include "common/constructor_and_params.ftl"/>
}