<#include "../common/copyright.ftl" />

using Ds3.Models;
using Ds3.Runtime;
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

        public BulkPutRequest WithMaxUploadSize(long maxUploadSize)
        {
            this.MaxUploadSize = maxUploadSize;
            this.QueryParams["max_upload_size"] = maxUploadSize.ToString("D");
            return this;
        }

        <#include "common/optional_args.ftl" />

        public ${name}(${netHelper.constructor(constructorArgs)}) {
            <#list constructorArgs as arg>
            this.${arg.getName()?cap_first} = ${arg.getName()?uncap_first};
            </#list>
            <#if operation??>
            this.QueryParams.Add("operation", "${operation.toString()?lower_case}");
            </#if>
            if (!objects.TrueForAll(obj => obj.Size.HasValue))
            {
                throw new Ds3RequestException(Resources.ObjectsMissingSizeException);
            }
        }


        <#include "common/http_verb_and_path.ftl" />

        internal override Stream GetContentStream()
        {
            return new XDocument()
                .AddFluent(
                    new XElement("Objects").AddAllFluent(
                        from obj in this.Objects
                        select new XElement("Object")
                            .SetAttributeValueFluent("Name", obj.Name)
                            .SetAttributeValueFluent("Size", obj.Size.Value.ToString("D"))
                    )
                )
                .WriteToMemoryStream();
        }
    }
}