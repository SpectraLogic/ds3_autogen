<#include "../../common/copyright.ftl" />


using Ds3.Calls;
using Ds3.Runtime;
using System.Linq;

namespace Ds3.ResponseParsers
{
    internal class ${name} : IResponseParser<${requestName}, ${responseName}>
    {
        public ${responseName} Parse(${requestName} request, IWebResponse response)
        {
            using (response)
            {
                return new ${responseName}(
                    ResponseParseUtilities.ParseBlobChecksumHeaders(response.Headers),
                    ResponseParseUtilities.ParseBlobChecksumTypeHeader(response.Headers),
                    long.Parse(response.Headers.Single(kvp => kvp.Key.ToLowerInvariant() == "content-length").Value),
                    response.Headers.Single(kvp => kvp.Key.ToLowerInvariant() == "etag").Value,
                    ResponseParseUtilities.ExtractCustomMetadata(response.Headers)
                );
            }
        }
    }
}