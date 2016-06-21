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
        public string ETag { get; private set; }
        public long Length { get; private set; }

        public ${name}(long length, string eTag, IDictionary<string, string> metadata)
        {
            this.Length = length;
            this.ETag = eTag;
            this.Metadata = metadata;
        }
    }
}