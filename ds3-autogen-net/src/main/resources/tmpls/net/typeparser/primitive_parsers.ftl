        //DateTime parsers

        public static DateTime? ParseNullableDateTime(XElement element)
        {
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
            if (!Enum.TryParse(ConvertToPascalCase(enumString), out result))
            {
                throw new ArgumentException(string.Format(Resources.InvalidValueForTypeException, typeof(T).Name));
            }
            return result;
        }

        public static string ConvertToPascalCase(string uppercaseUnderscore)
        {
            var sb = new StringBuilder();
            foreach (var word in uppercaseUnderscore.Split('_'))
            {
                sb.Append(word[0]);
                sb.Append(word.Substring(1).ToLowerInvariant());
            }
            return sb.ToString();
        }