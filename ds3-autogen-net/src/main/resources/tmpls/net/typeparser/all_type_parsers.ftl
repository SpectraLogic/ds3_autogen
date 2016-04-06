<#include "../common/copyright.ftl" />


namespace Ds3.ResponseParsers
{
    internal class ModelParsers
    {

<#list typeParsers as parser>
<#include "type_parser.ftl" />
</#list>

    }
}
