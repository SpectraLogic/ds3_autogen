<#include "../common/copyright.ftl" />


using System.Collections.Generic;
using Ds3.Models;

using Ds3.Runtime;

namespace Ds3.Calls
{
    public class ${name}
    {
        public IDictionary<long, string> BlobChecksums { get; private set; }
        public ChecksumType.Type BlobChecksumType { get; private set; }
        public IDictionary<string, string> Metadata { get; private set; }
        public string ETag { get; private set; }
        public long Length { get; private set; }

        public ${name}(IDictionary<long, string> blobChecksums, ChecksumType.Type blobChecksumType, long length, string eTag, IDictionary<string, string> metadata)
        {
            this.BlobChecksums = blobChecksums;
            this.BlobChecksumType = blobChecksumType;
            this.Length = length;
            this.ETag = eTag;
            this.Metadata = metadata;
        }
    }
}