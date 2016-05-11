<#include "../../common/copyright.ftl" />


using Ds3.Calls;
using Ds3.Runtime;
using System.Net;

namespace Ds3.ResponseParsers
{
    internal class ${name} : IResponseParser<${requestName}, ${responseName}>
    {
        public ${responseName} Parse(${requestName} request, IWebResponse response)
        {
            using (response)
            {
                ResponseParseUtilities.HandleStatusCode(response, (HttpStatusCode)${responseCode}, HttpStatusCode.ServiceUnavailable);
                switch (response.StatusCode)
                {
                    case (HttpStatusCode)${responseCode}:
                        using (var stream = response.GetResponseStream())
                        {
                            return new ${responseName}(
                                ModelParsers.${modelParserName}(
                                    XmlExtensions.ReadDocument(stream).ElementOrThrow("${nameToMarshal}"))
                            );
                        }

                    case HttpStatusCode.ServiceUnavailable:
                        throw new Ds3MaxJobsException();

                    default:
                        return null; // we should never hit that
                }
            }
        }
    }
}