<#include "../common/copyright.ftl" />

using Ds3.Models;
using Ds3.Runtime;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Xml.Linq;

namespace Ds3.ResponseParsers
{
    public class ModelParsers
    {

<#list typeParsers as parser>
<#include "type_parser.ftl" />
</#list>

<#list enumParsers as enum>
<#include "enum_parser.ftl" />
</#list>

<#include "primitive_parsers.ftl" />

    }
}
