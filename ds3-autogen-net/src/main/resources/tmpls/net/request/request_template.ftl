<#include "../common/copyright.ftl" />

using Ds3.Models;
using System;
using System.Net;

namespace Ds3.Calls
{
    public class ${name} : Ds3Request
    {
        <#include "common/required_args.ftl" />

        <#include "common/optional_args.ftl" />

        <#include "common/constructor.ftl" />

        <#include "common/http_verb_and_path.ftl" />
    }
}