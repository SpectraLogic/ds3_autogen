<#include "../../common/copyright.ftl" />


using Ds3.Calls;
using Ds3.Runtime;
using System;
using System.Net;

namespace Ds3.ResponseParsers
{
    internal class ${name} : IResponseParser<${requestName}, ${responseName}>
    {
        public ${responseName} Parse(${requestName} request, IWebResponse response)
        {
            using (response)
            {
                ResponseParseUtilities.HandleStatusCode(response, HttpStatusCode.OK, HttpStatusCode.ServiceUnavailable, HttpStatusCode.NotFound);
                using (var stream = response.GetResponseStream())
                {
                    switch (response.StatusCode)
                    {
                        case HttpStatusCode.OK:
                            return ${responseName}.Success(
                                ModelParsers.${modelParserName}(
                                    XmlExtensions.ReadDocument(stream).ElementOrThrow("${nameToMarshal}"))
                            );

                        case HttpStatusCode.ServiceUnavailable:
                            return ${responseName}.RetryAfter(TimeSpan.FromSeconds(int.Parse(response.Headers["retry-after"])));

                        case HttpStatusCode.NotFound:
                            return ${responseName}.ChunkGone;

                        default:
                            throw new NotSupportedException(Resources.InvalidEnumValueException);
                    }
                }
            }
        }
    }
}