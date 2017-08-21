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
        public long? MaxUploadSize { get; private set; }

        public ${name} WithMaxUploadSize(long maxUploadSize)
        {
            this.MaxUploadSize = maxUploadSize;
            this.QueryParams["max_upload_size"] = maxUploadSize.ToString("D");
            return this;
        }

        <#include "common/optional_args.ftl" />

        <#include "common/constructor.ftl" />

        <#include "common/http_verb_and_path.ftl" />

        internal override Stream GetContentStream()
        {
            return RequestPayloadUtil.MarshalDs3ObjectNameAndSize(this.Objects);
        }

        internal override long GetContentLength()
        {
            return GetContentStream().Length;
        }
    }
}