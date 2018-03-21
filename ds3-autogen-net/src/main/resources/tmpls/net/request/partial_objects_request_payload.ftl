<#include "../common/copyright.ftl" />

using Ds3.Calls.Util;
using Ds3.Models;
using System;
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

        public ${name}(string bucketName, IEnumerable<Ds3Object> ds3Objects)
            : this(bucketName, ds3Objects, Enumerable.Empty<Ds3PartialObject>())
        {
        }

        public ${name}(string bucketName, IEnumerable<string> objectNames)
            : this(bucketName, objectNames.Select(name => new Ds3Object(name, null)), Enumerable.Empty<Ds3PartialObject>())
        {
        }

        <#include "common/http_verb_and_path.ftl" />

        internal override Stream GetContentStream()
        {
            return RequestPayloadUtil.MarshalFullAndPartialObjects(this.FullObjects, this.PartialObjects);
        }

        internal override long GetContentLength()
        {
            return GetContentStream().Length;
        }
    }
}