<#include "../../common/copyright.ftl" />


using Ds3.Calls;
using Ds3.Runtime;
using System.Net;
using System.IO;

namespace Ds3.ResponseParsers
{
    internal class ${name} : IResponseParser<${requestName}, ${responseName}>
    {
        public ${responseName} Parse(${requestName} request, IWebResponse response)
        {
            using (response)
            {
                ResponseParseUtilities.HandleStatusCode(response, (HttpStatusCode)${responseCode});
                using (var stream = response.GetResponseStream())
                using (StreamReader sr = new StreamReader(stream))
                {
                    return new ${responseName}(sr.ReadToEnd());
                }
            }
        }
    }
}