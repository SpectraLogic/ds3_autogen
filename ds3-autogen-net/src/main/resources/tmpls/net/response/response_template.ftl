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
        public ${responseType} ResponsePayload { get; private set; }

        public ${name}(${responseType} responsePayload)
        {
            this.ResponsePayload = responsePayload;
        }
    }
}
