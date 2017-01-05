        private IDictionary<string, string> _metadata = new Dictionary<string, string>();
        private static readonly TraceSwitch SdkNetworkSwitch = new TraceSwitch("sdkNetworkSwitch", "set in config file");

        public IDictionary<string, string> Metadata
        {
            get { return this._metadata; }
            set { this.WithMetadata(value); }
        }

        public ${name} WithMetadata(IDictionary<string, string> metadata)
        {
            foreach (var key in this.Headers.Keys().Where(key => key.StartsWith(HttpHeaders.AwsMetadataPrefix)).ToList())
            {
                this.Headers.Remove(key);
            }
            foreach (var keyValuePair in metadata)
            {
                if (string.IsNullOrEmpty(keyValuePair.Value))
                {
                    if (SdkNetworkSwitch.TraceWarning)
                    {
                        Trace.WriteLine("Key has not been added to metadata because value was null or empty: " + keyValuePair.Key);
                    }
                }
                else
                {
                    this.Headers.Add(HttpHeaders.AwsMetadataPrefix + keyValuePair.Key, keyValuePair.Value);
                }
            }
            this._metadata = metadata;
            return this;
        }