<#include "../common/copyright.ftl" />


using Ds3.Calls;
using Ds3.Runtime;
using System;
using System.Linq;
using System.Net;

namespace Ds3.ResponseParsers
{
    internal class ${name} : IResponseParser<${requestName}, ${responseName}>
    {
        public ${responseName} Parse(${requestName} request, IWebResponse response)
        {
            using (response)
            {
                ResponseParseUtilities.HandleStatusCode(response, HttpStatusCode.OK, HttpStatusCode.NotFound);
                using (var stream = response.GetResponseStream())
                {
                    switch (response.StatusCode)
                    {
                        case HttpStatusCode.OK:
                            var jobResponse = ModelParsers.${modelParserName}(
                                XmlExtensions.ReadDocument(stream).ElementOrThrow("${nameToMarshal}"));

                            if (jobResponse.Objects.Any())
                            {
                                return ${responseName}.Success(RetryAfterHeader(response), jobResponse);
                            }
                            return ${responseName}.RetryAfter(RetryAfterHeader(response));

                        case HttpStatusCode.NotFound:
                            return ${responseName}.JobGone;

                        default:
                            throw new NotSupportedException(Resources.InvalidEnumValueException);
                    }
                }
            }
        }

        private static TimeSpan RetryAfterHeader(IWebResponse response)
        {
            return TimeSpan.FromSeconds(int.Parse(response.Headers["retry-after"]));
        }
    }
}