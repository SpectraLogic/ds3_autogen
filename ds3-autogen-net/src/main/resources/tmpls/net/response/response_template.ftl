<#include "../common/copyright.ftl" />

using Ds3.Models;

namespace Ds3.Calls
{
    public class ${name}
    {
        public ${responseType} ResponsePayload { get; private set; }

        public ${name}(${responseType} responsePayload)
        {
            this.ResponsePayload = responsePayload;
        }
    }
}
