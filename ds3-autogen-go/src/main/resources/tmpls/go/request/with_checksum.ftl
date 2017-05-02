func (${name?uncap_first} *${name}) WithChecksum(contentHash string, checksumType networking.ChecksumType) *${name} {
    ${name?uncap_first}.checksum.ContentHash = contentHash
    ${name?uncap_first}.checksum.Type = checksumType
    return ${name?uncap_first}
}

func (${name?uncap_first} *${name}) GetChecksum() networking.Checksum {
    return ${name?uncap_first}.checksum
}
