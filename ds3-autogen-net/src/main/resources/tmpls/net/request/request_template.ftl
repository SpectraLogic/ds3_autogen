<#include "../common/copyright.ftl" />

using System.Net;

namespace Ds3.Calls
{
    public class ${name} : Ds3Request
    {
        <#include "common/required_args.ftl" />

        <#include "common/optional_args.ftl" />

        <#include "common/constructor.ftl" />

        internal override HttpVerb Verb
        {
            get
            {
                return HttpVerb.${verb.toString()}
            }
        }

        internal override string Path
        {
            get
            {
                return "${path}";
            }
        }
    }
}