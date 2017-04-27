    /**
     * Set a MD5 checksum for the request.
     */
    public ${name} withChecksum(final ChecksumType checksum) {
        return withChecksum(checksum, ChecksumType.Type.MD5);
    }

    public ${name} withChecksum(final ChecksumType checksum, final ChecksumType.Type checksumType) {
        this.checksum = checksum;
        this.checksumType = checksumType;
        return this;
    }

    @Override
    ${javaHelper.createGetter("Checksum", "ChecksumType")}

    @Override
    ${javaHelper.createGetter("ChecksumType", "ChecksumType.Type")}
