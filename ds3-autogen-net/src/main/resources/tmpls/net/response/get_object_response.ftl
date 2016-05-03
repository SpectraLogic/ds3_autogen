<#include "../common/copyright.ftl" />


using System.Net;
using System.IO;
using System.Collections.Generic;

using Ds3.Runtime;

namespace Ds3.Calls
{
    public class ${name}
    {
        public IDictionary<string, string> Metadata { get; private set; }

        public ${name}(IDictionary<string, string> metadata)
        {
            this.Metadata = metadata;
        }
    }
}