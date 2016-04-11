        private IDictionary<string, string> _metadata = new Dictionary<string, string>();

        public IDictionary<string, string> Metadata
        {
            get { return this._metadata; }
            set { this.WithMetadata(value); }
        }

        public ${name} WithMetadata(IDictionary<string, string> metadata)
        {
            foreach (var key in this.Headers.Keys.Where(key => key.StartsWith(HttpHeaders.AwsMetadataPrefix)).ToList())
            {
                this.Headers.Remove(key);
            }
            foreach (var keyValuePair in metadata)
            {
                this.Headers.Add(HttpHeaders.AwsMetadataPrefix + keyValuePair.Key, keyValuePair.Value);
            }
            this._metadata = metadata;
            return this;
        }