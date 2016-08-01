<#include "../common/copyright.ftl" />

using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

using Ds3.Models;
using Ds3.Runtime;
using System.Diagnostics;

namespace Ds3.Calls
{
    public class ${name} : Ds3Request
    {
        private readonly Stream RequestPayload;
        private readonly long Length;

        <#include "common/required_args.ftl" />

        <#include "common/optional_checksum.ftl" />

        <#include "common/optional_metadata.ftl" />

        <#include "common/optional_args.ftl" />

        <#include "common/constructor.ftl" />

        public ${name}(string bucketName, string objectName, Stream requestPayload)
            : this(bucketName, objectName, requestPayload.Length, requestPayload)
        {
        }

        <#include "common/http_verb_and_path.ftl" />

        internal override Stream GetContentStream()
        {
            return RequestPayload;
        }

        internal override long GetContentLength()
        {
            return Length;
        }
    }
}