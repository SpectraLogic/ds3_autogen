<#include "../common/copyright.ftl" />


using System;
using System.Collections.Generic;

namespace Ds3.Models
{
    public class ${name}
    {
        <#list elements as elmt>
        public ${elmt.getNetType()} ${elmt.getName()} { get; set; }
        </#list>
    }
}
