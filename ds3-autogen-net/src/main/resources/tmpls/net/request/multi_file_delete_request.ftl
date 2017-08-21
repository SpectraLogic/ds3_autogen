<#include "../common/copyright.ftl" />

using Ds3.Calls.Util;
using Ds3.Models;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace Ds3.Calls
{
    public class ${name} : Ds3Request
    {
        <#include "common/required_args.ftl" />

        <#include "common/optional_args.ftl" />

        <#include "common/constructor.ftl" />

        internal override Stream GetContentStream()
        {
            return RequestPayloadUtil.MarshalDeleteObjectNames(this.Objects);
        }

        internal override long GetContentLength()
        {
            return GetContentStream().Length;
        }

        internal override ChecksumType ChecksumValue
        {
            get { return ChecksumType.Compute; }
        }

        <#include "common/http_verb_and_path.ftl" />
    }
}