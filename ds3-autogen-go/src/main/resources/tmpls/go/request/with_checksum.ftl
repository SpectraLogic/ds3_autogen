func (${name?uncap_first} *${name}) WithChecksum(contentHash string, checksumType networking.ChecksumType) *${name} {
    ${name?uncap_first}.Checksum.ContentHash = contentHash
    ${name?uncap_first}.Checksum.Type = checksumType
    return ${name?uncap_first}
}
