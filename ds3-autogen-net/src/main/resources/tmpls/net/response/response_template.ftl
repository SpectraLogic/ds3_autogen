<#include "../common/copyright.ftl" />

using System.Net;
using System.IO;
using System.Collections.Generic;
using Ds3.Models;

using Ds3.Runtime;
using System;

namespace Ds3.Calls
{
    public class ${name}
    {
        <#list arguments as arg>
        public ${netHelper.getType(arg)} ${arg.getName()?cap_first} { get; private set; }
        </#list>

        public ${name}(${netHelper.constructor(arguments)})
        {
            <#list arguments as arg>
            this.${arg.getName()?cap_first} = ${arg.getName()?uncap_first};
            </#list>
        }
    }
}
