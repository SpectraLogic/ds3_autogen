        //ChecksumType parsers

        public static ChecksumType.Type? ParseNullableChecksumType(string checksumTypeOrNull)
        {
            return string.IsNullOrWhiteSpace(checksumTypeOrNull)
                ? (ChecksumType.Type?) null
                : ParseChecksumType(checksumTypeOrNull);
        }

        public static ChecksumType.Type ParseChecksumType(string checksumType)
        {
            return
                ParseEnumType<ChecksumType.Type>(checksumType);
        }

        public static ChecksumType.Type? ParseNullableChecksumType(XElement element)
        {
            if (null == element)
            {
                return null;
            }
            return ParseNullableChecksumType(element.Value);
        }

        public static ChecksumType.Type ParseChecksumType(XElement element)
        {
            return ParseChecksumType(element.Value);
        }

        //Guid parsers

        public static Guid? ParseNullableGuid(XElement element)
        {
            if (null == element)
            {
                return null;
            }
            return ParseNullableGuid(element.Value);
        }

        public static Guid? ParseNullableGuid(string guidStringOrNull)
        {
            return string.IsNullOrWhiteSpace(guidStringOrNull)
                ? (Guid?)null
                : ParseGuid(guidStringOrNull);
        }

        public static Guid ParseGuid(XElement element)
        {
            return ParseGuid(element.Value);
        }

        public static Guid ParseGuid(string guidString)
        {
            return Guid.Parse(guidString);
        }

        //DateTime parsers

        public static DateTime? ParseNullableDateTime(XElement element)
        {
            if (null == element)
            {
                return null;
            }
            return ParseNullableDateTime(element.Value);
        }

        public static DateTime? ParseNullableDateTime(string dateTimeStringOrNull)
        {
            return string.IsNullOrWhiteSpace(dateTimeStringOrNull)
                ? (DateTime?)null
                : ParseDateTime(dateTimeStringOrNull);
        }

        public static DateTime ParseDateTime(XElement element)
        {
            return ParseDateTime(element.Value);
        }

        public static DateTime ParseDateTime(string dateTimeString)
        {
            return DateTime.Parse(dateTimeString);
        }

        //Boolean parsers

        public static bool? ParseNullableBool(XElement element)
        {
            if (null == element)
            {
                return null;
            }
            return ParseNullableBool(element.Value);
        }

        public static bool? ParseNullableBool(string boolStringOrNull)
        {
            return string.IsNullOrWhiteSpace(boolStringOrNull)
                ? (bool?)null
                : ParseBool(boolStringOrNull);
        }

        public static bool ParseBool(XElement element)
        {
            return ParseBool(element.Value);
        }

        public static bool ParseBool(string boolString)
        {
            return bool.Parse(boolString);
        }

        //String parsers

        public static string ParseNullableString(XElement element)
        {
            if (null == element)
            {
                return null;
            }
            return ParseNullableString(element.Value);
        }

        public static string ParseNullableString(string stringOrNull)
        {
            return string.IsNullOrWhiteSpace(stringOrNull)
                ? (string)null
                : ParseString(stringOrNull);
        }

        public static string ParseString(XElement element)
        {
            return ParseString(element.Value);
        }

        public static string ParseString(string str)
        {
            return str;
        }

        //Integer parsers

        public static int? ParseNullableInt(XElement element)
        {
            if (null == element)
            {
                return null;
            }
            return ParseNullableInt(element.Value);
        }

        public static int? ParseNullableInt(string intStringOrNull)
        {
            return string.IsNullOrWhiteSpace(intStringOrNull)
                ? (int?)null
                : ParseInt(intStringOrNull);
        }

        public static int ParseInt(XElement element)
        {
            return ParseInt(element.Value);
        }

        public static int ParseInt(string intString)
        {
            return int.Parse(intString);
        }

        //Long parsers

        public static long? ParseNullableLong(XElement element)
        {
            if (null == element)
            {
                return null;
            }
            return ParseNullableLong(element.Value);
        }

        public static long? ParseNullableLong(string longStringOrNull)
        {
            return string.IsNullOrWhiteSpace(longStringOrNull)
                ? (long?)null
                : ParseLong(longStringOrNull);
        }

        public static long ParseLong(XElement element)
        {
            return ParseLong(element.Value);
        }

        public static long ParseLong(string longString)
        {
            return long.Parse(longString);
        }

        //Double parsers

        public static double? ParseNullableDouble(XElement element)
        {
            if (null == element)
            {
                return null;
            }
            return ParseNullableDouble(element.Value);
        }

        public static double? ParseNullableDouble(string doubleStringOrNull)
        {
            return string.IsNullOrWhiteSpace(doubleStringOrNull)
                ? (double?)null
                : ParseDouble(doubleStringOrNull);
        }

        public static double ParseDouble(XElement element)
        {
            return ParseDouble(element.Value);
        }

        public static double ParseDouble(string doubleString)
        {
            return double.Parse(doubleString);
        }

        //Enum parser

        public static T ParseEnumType<T>(string enumString)
            where T : struct
        {
            T result;
            if (!Enum.TryParse(enumString, out result))
            {
                throw new ArgumentException(string.Format(Resources.InvalidValueForTypeException, typeof(T).Name));
            }
            return result;
        }

        //List Parser

        public static IEnumerable<TResult> ParseEncapsulatedList<TResult>(
            XElement element,
            string xmlTag,
            string encapsulatingXmlTag,
            Func<XElement, TResult> parser)
        {
            var encapsulatingElement = element.Element(encapsulatingXmlTag);
            if (null == encapsulatingElement || encapsulatingElement.IsEmpty)
            {
                return null;
            }
            var elements = encapsulatingElement.Elements(xmlTag);
            if (null == elements)
            {
                return null;
            }
            return elements.Select(parser);
        }