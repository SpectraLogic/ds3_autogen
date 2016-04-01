<#include "../common/copyright.ftl" />


using System;

namespace Ds3.Models
{
    public class ${name}
    {
        <#list elements as elmt>
        public ${elmt.getType()} ${elmt.getName()} { get; set; }
        </#list>
    }
}
