<#include "../common/copyright.ftl" />

using Ds3.Models;
using System;
using System.IO;

namespace Ds3.Calls
{
    public class ${name} : Ds3Request
    {
        public string RequestPayload { get; private set; }

        <#include "common/required_args.ftl" />

        <#include "common/optional_args.ftl" />

        <#include "common/constructor.ftl" />

        <#include "common/http_verb_and_path.ftl" />

        internal override Stream GetContentStream()
        {
            byte[] byteArray = System.Text.Encoding.UTF8.GetBytes(RequestPayload);
            return new MemoryStream(byteArray);
        }
    }
}