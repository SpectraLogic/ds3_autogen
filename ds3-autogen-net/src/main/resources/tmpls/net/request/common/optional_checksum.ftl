        private ChecksumType _checksum = ChecksumType.None;
        private ChecksumType.Type _type;

        internal override ChecksumType ChecksumValue
        {
            get { return this._checksum; }
        }

        internal override ChecksumType.Type Type
        {
            get { return this._type; }
        }

        public ChecksumType Checksum
        {
            get { return this._checksum; }
            set { this.WithChecksum(value); }
        }

        public ${name} WithChecksum(ChecksumType checksum, ChecksumType.Type type = ChecksumType.Type.MD5)
        {
            this._checksum = checksum;
            this._type = type;
            return this;
        }