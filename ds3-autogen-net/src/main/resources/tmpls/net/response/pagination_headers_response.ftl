<#include "../common/copyright.ftl" />

using Ds3.Models;

namespace Ds3.Calls
{
    public class ${name}
    {
        public ${responseType} ResponsePayload { get; private set; }
        public int? PagingTruncated { get; private set; }
        public int? PagingTotalResultCount { get; private set; }

        public ${name}(${responseType} responsePayload, int? pagingTruncated, int? pagingTotalResultCount)
        {
            this.ResponsePayload = responsePayload;
            this.PagingTruncated = pagingTruncated;
            this.PagingTotalResultCount = pagingTotalResultCount;
        }
    }
}
