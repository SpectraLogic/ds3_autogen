<#include "../common/copyright.ftl" />

using System.Net;

using Ds3.Runtime;
using Ds3.Models;

namespace Ds3.Calls
{
    public class ${name}
    {
        public StatusType Status { get; private set; }

        public ${name}(StatusType status)
        {
            this.Status = status;
        }

        public enum StatusType
        {
            Exists,
            NotAuthorized,
            DoesntExist
        }
    }
}
