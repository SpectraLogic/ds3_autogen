<#include "../common/copyright.ftl" />

using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

using Ds3.Models;
using Ds3.Runtime;

namespace Ds3.Calls
{
    public class ${name} : Ds3Request
    {
        private readonly Stream RequestPayload;

        <#include "common/required_args.ftl" />

        <#include "common/optional_checksum.ftl" />

        <#include "common/optional_metadata.ftl" />

        <#include "common/optional_args.ftl" />

        <#include "common/constructor.ftl" />

        <#include "common/http_verb_and_path.ftl" />

        internal override Stream GetContentStream()
        {
            return RequestPayload;
        }
    }
}