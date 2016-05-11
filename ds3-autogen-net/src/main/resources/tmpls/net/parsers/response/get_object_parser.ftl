<#include "../../common/copyright.ftl" />


using System.Linq;
using System.Net;
using Ds3.Calls;
using Ds3.Lang;
using Ds3.Runtime;

namespace Ds3.ResponseParsers
{
    internal class ${name} : IResponseParser<${requestName}, ${responseName}>
    {
        private readonly int _copyBufferSize;

        public ${name}(int copyBufferSize)
        {
            _copyBufferSize = copyBufferSize;
        }

        public ${responseName} Parse(${requestName} request, IWebResponse response)
        {
            using (response)
            {
                ResponseParseUtilities.HandleStatusCode(
                    response,
                    request.GetByteRanges().Any()
                        ? HttpStatusCode.PartialContent
                        : HttpStatusCode.OK
                    );
                using (var stream = response.GetResponseStream())
                {
                    StreamsUtil.BufferedCopyTo(stream, request.DestinationStream, _copyBufferSize);
                }
                return new ${responseName}(ResponseParseUtilities.ExtractCustomMetadata(response.Headers));
            }
        }
    }
}