<#include "../common/copyright.ftl" />

using Ds3.Models;
using Ds3.Runtime;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Xml.Linq;

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

        <#include "common/objects_constructor_get_stream.ftl" />

        <#include "common/http_verb_and_path.ftl" />
    }
}