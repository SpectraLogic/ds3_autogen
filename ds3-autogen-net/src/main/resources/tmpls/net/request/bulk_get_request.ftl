<#include "../common/copyright.ftl" />

using Ds3.Models;
using Ds3.Runtime;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Xml.Linq;

namespace Ds3.Calls
{
    public class ${name} : Ds3Request
    {
        <#include "common/required_args.ftl" />

        public JobChunkClientProcessingOrderGuarantee? ChunkClientProcessingOrderGuarantee { get; private set; }

        public ${name} WithChunkClientProcessingOrderGuarantee(JobChunkClientProcessingOrderGuarantee chunkClientProcessingOrderGuarantee)
        {
            this.ChunkClientProcessingOrderGuarantee = chunkClientProcessingOrderGuarantee;
            return this;
        }

        <#include "common/optional_args.ftl" />

        <#include "common/constructor.ftl" />

        public ${name}(string bucketName, List<Ds3Object> objects)
            : this(bucketName, objects.Select(o => o.Name), Enumerable.Empty<Ds3PartialObject>())
        {
        }

        <#include "common/http_verb_and_path.ftl" />

        internal override Stream GetContentStream()
        {
            var root = new XElement("Objects")
                .AddAllFluent(
                    from name in this.FullObjects
                    select new XElement("Object").SetAttributeValueFluent("Name", name)
                )
                .AddAllFluent(
                    from partial in this.PartialObjects
                    select new XElement("Object")
                        .SetAttributeValueFluent("Name", partial.Name)
                        .SetAttributeValueFluent("Offset", partial.Range.Start.ToString())
                        .SetAttributeValueFluent("Length", partial.Range.Length.ToString())
                );
            if (this.ChunkClientProcessingOrderGuarantee.HasValue)
            {
                root.SetAttributeValue(
                    "ChunkClientProcessingOrderGuarantee",
                    BuildChunkOrderingEnumString(this.ChunkClientProcessingOrderGuarantee.Value)
                );
            }
            return new XDocument().AddFluent(root).WriteToMemoryStream();
        }


        private static string BuildChunkOrderingEnumString(JobChunkClientProcessingOrderGuarantee chunkClientProcessingOrderGuarantee)
        {
            switch (chunkClientProcessingOrderGuarantee)
            {
                case JobChunkClientProcessingOrderGuarantee.NONE: return "NONE";
                case JobChunkClientProcessingOrderGuarantee.IN_ORDER: return "IN_ORDER";
                default: throw new NotSupportedException(Resources.InvalidEnumValueException);
            }
        }
    }
}