<#include "../common/copyright.ftl" />

using Ds3.Models;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace Ds3.Calls
{
    public class ${name} : Ds3Request
    {
        <#include "common/required_args.ftl" />

        <#include "common/optional_args.ftl" />

        private IEnumerable<Range> _byteRanges = Enumerable.Empty<Range>();
        public IEnumerable<Range> ByteRanges
        {
            get { return _byteRanges; }
            set { WithByteRanges(value); }
        }

        public ${name} WithByteRanges(IEnumerable<Range> byteRanges)
        {
            this._byteRanges = byteRanges;
            return this;
        }

        internal override IEnumerable<Range> GetByteRanges()
        {
            return this._byteRanges;
        }

        internal Stream DestinationStream { get; private set; }

        public ${name}(${netHelper.constructor(constructorArgs)}) {
            <#list constructorArgs as arg>
            this.${arg.getName()?cap_first} = ${arg.getName()?uncap_first};
            </#list>
            <#if netHelper.containsArgument(constructorArgs, "Job")>
            if (job != Guid.Empty)
            {
                QueryParams.Add("job", job.ToString());
                QueryParams.Add("offset", offset.ToString());
            }
            </#if>
        }

        <#include "common/http_verb_and_path.ftl" />
    }
}