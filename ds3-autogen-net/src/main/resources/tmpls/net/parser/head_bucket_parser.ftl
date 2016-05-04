<#include "../common/copyright.ftl" />


using System;
using System.Net;

using Ds3.Calls;
using Ds3.Models;
using Ds3.Runtime;
using HeadBucketStatus = Ds3.Calls.${responseName}.StatusType;

namespace Ds3.ResponseParsers
{
    internal class ${name} : IResponseParser<${requestName}, ${responseName}>
    {
        public ${responseName} Parse(${requestName} request, IWebResponse response)
        {
            using (response)
            {
                ResponseParseUtilities.HandleStatusCode(response, HttpStatusCode.OK, HttpStatusCode.Forbidden, HttpStatusCode.NotFound);
                HeadBucketStatus status;
                switch (response.StatusCode)
                {
                    case HttpStatusCode.OK: status = HeadBucketStatus.Exists; break;
                    case HttpStatusCode.Forbidden: status = HeadBucketStatus.NotAuthorized; break;
                    case HttpStatusCode.NotFound: status = HeadBucketStatus.DoesntExist; break;
                    default: throw new NotSupportedException(Resources.InvalidEnumValueException);
                }
                return new ${responseName}(status: status);
            }
        }
    }
}