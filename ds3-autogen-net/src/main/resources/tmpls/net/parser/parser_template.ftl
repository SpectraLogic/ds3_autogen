<#include "../common/copyright.ftl" />


using Ds3.Calls;
using Ds3.Models;
using Ds3.Runtime;
using System.Linq;
using System.Net;
using System.Xml.Linq;

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
                {
                    return new ${responseName}(
                        XmlExtensions
                            .ReadDocument(stream)
                            <#if netHelper.hasNameToMarshal(nameToMarshal)>.ElementOrThrow("${nameToMarshal}")</#if>
                            .Select(${modelParserName})
                            .ToList()
                    );
                }
            }
        }
    }
}