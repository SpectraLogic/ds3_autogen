        private ChecksumType _checksum = ChecksumType.None;
        private ChecksumType.Type _ctype;

        internal override ChecksumType ChecksumValue
        {
            get { return this._checksum; }
        }

        internal override ChecksumType.Type CType
        {
            get { return this._ctype; }
        }

        public ChecksumType Checksum
        {
            get { return this._checksum; }
            set { this.WithChecksum(value); }
        }

        public ${name} WithChecksum(ChecksumType checksum, ChecksumType.Type ctype = ChecksumType.Type.MD5)
        {
            this._checksum = checksum;
            this._ctype = ctype;
            return this;
        }