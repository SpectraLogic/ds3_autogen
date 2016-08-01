<#include "../common/copyright.ftl" />

using Ds3.Models;
using System;
using System.IO;

namespace Ds3.Calls
{
    public class ${name} : Ds3Request
    {
        private readonly Stream RequestPayload;

        <#include "common/required_args.ftl" />

        <#include "common/optional_args.ftl" />

        <#include "common/constructor.ftl" />

        <#include "common/http_verb_and_path.ftl" />

        internal override Stream GetContentStream()
        {
            return RequestPayload;
        }

        internal override long GetContentLength()
        {
            return RequestPayload.Length;
        }
    }
}