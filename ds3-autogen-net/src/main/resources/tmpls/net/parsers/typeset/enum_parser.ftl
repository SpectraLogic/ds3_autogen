        public static ${enum}? ParseNullable${enum}(string ${enum?uncap_first}OrNull)
        {
            return string.IsNullOrWhiteSpace(${enum?uncap_first}OrNull)
                ? (${enum}?) null
                : Parse${enum}(${enum?uncap_first}OrNull);
        }

        public static ${enum} Parse${enum}(string ${enum?uncap_first})
        {
            return ParseEnumType<${enum}>(${enum?uncap_first});
        }

        public static ${enum}? ParseNullable${enum}(XElement element)
        {
            if (null == element)
            {
                return null;
            }
            return ParseNullable${enum}(element.Value);
        }

        public static ${enum} Parse${enum}(XElement element)
        {
            return Parse${enum}(element.Value);
        }
