<#include "../common/copyright.ftl" />

using Ds3.Models;
using System;
using System.Collections.Generic;
using System.IO;
using Ds3.Calls.Util;
using System.Linq;

namespace Ds3.Calls
{
    public class ${name} : Ds3Request
    {
        public IEnumerable<Part> Parts { get; private set; }
        <#include "common/required_args.ftl" />

        <#include "common/optional_args.ftl" />

        <#include "common/constructor.ftl" />

        <#include "common/http_verb_and_path.ftl" />

        internal override Stream GetContentStream()
        {
            return RequestPayloadUtil.MarshalPartsToStream(Parts);
        }

        internal override long GetContentLength()
        {
            return GetContentStream().Length;
        }
    }
}