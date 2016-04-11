        private Checksum _checksum = Checksum.None;
        private Checksum.ChecksumType _checksumType;

        internal override Checksum ChecksumValue
        {
            get { return this._checksum; }
        }

        internal override Checksum.ChecksumType ChecksumType
        {
            get { return this._checksumType; }
        }

        public Checksum Checksum
        {
            get { return this._checksum; }
            set { this.WithChecksum(value); }
        }

        public ${name} WithChecksum(Checksum checksum, Checksum.ChecksumType checksumType = Checksum.ChecksumType.Md5)
        {
            this._checksum = checksum;
            this._checksumType = checksumType;
            return this;
        }