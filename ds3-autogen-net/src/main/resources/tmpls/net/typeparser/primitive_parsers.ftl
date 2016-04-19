        //DateTime parsers

		private static DateTime? ParseNullableDateTime(XElement element)
        {
            return ParseNullableDateTime(element.Value);
        }

        private static DateTime? ParseNullableDateTime(string dateTimeStringOrNull)
        {
            return string.IsNullOrWhiteSpace(dateTimeStringOrNull)
                ? (DateTime?)null
                : ParseDateTime(dateTimeStringOrNull);
        }

        private static DateTime ParseDateTime(XElement element)
        {
            return ParseDateTime(element.Value);
        }

        private static DateTime ParseDateTime(string dateTimeString)
        {
            return DateTime.Parse(dateTimeString);
        }

		//Boolean parsers

		private static bool? ParseNullableBool(XElement element)
        {
            return ParseNullableBool(element.Value);
        }

        private static bool? ParseNullableBool(string boolStringOrNull)
        {
            return string.IsNullOrWhiteSpace(boolStringOrNull)
                ? (bool?)null
                : ParseBool(boolStringOrNull);
        }

        private static bool ParseBool(XElement element)
        {
            return ParseBool(element.Value);
        }

        private static bool ParseBool(string boolString)
        {
            return bool.Parse(boolString);
        }

		//String parsers

		private static string ParseNullableString(XElement element)
        {
            return ParseNullableString(element.Value);
        }

        private static string ParseNullableString(string stringOrNull)
        {
            return string.IsNullOrWhiteSpace(stringOrNull)
                ? (string)null
                : ParseString(stringOrNull);
        }

        private static string ParseString(XElement element)
        {
            return ParseString(element.Value);
        }

        private static string ParseString(string str)
        {
            return str;
        }

		//Integer parsers

		private static int? ParseNullableInt(XElement element)
        {
            return ParseNullableInt(element.Value);
        }

        private static int? ParseNullableInt(string intStringOrNull)
        {
            return string.IsNullOrWhiteSpace(intStringOrNull)
                ? (int?)null
                : ParseInt(intStringOrNull);
        }

        private static int ParseInt(XElement element)
        {
            return ParseInt(element.Value);
        }

        private static int ParseInt(string intString)
        {
            return int.Parse(intString);
        }

		//Long parsers

		private static long? ParseNullableLong(XElement element)
        {
            return ParseNullableLong(element.Value);
        }

        private static long? ParseNullableLong(string longStringOrNull)
        {
            return string.IsNullOrWhiteSpace(longStringOrNull)
                ? (long?)null
                : ParseLong(longStringOrNull);
        }

        private static long ParseLong(XElement element)
        {
            return ParseLong(element.Value);
        }

        private static long ParseLong(string longString)
        {
            return long.Parse(longString);
        }

		//Double parsers

		private static double? ParseNullableDouble(XElement element)
        {
            return ParseNullableDouble(element.Value);
        }

        private static double? ParseNullableDouble(string doubleStringOrNull)
        {
            return string.IsNullOrWhiteSpace(doubleStringOrNull)
                ? (double?)null
                : ParseDouble(doubleStringOrNull);
        }

        private static double ParseDouble(XElement element)
        {
            return ParseDouble(element.Value);
        }

        private static double ParseDouble(string doubleString)
        {
            return double.Parse(doubleString);
        }

		//Enum parser

		private static T ParseEnumType<T>(string enumString)
            where T : struct
        {
            T result;
            if (!Enum.TryParse(ConvertToPascalCase(enumString), out result))
            {
                throw new ArgumentException(string.Format(Resources.InvalidValueForTypeException, typeof(T).Name));
            }
            return result;
        }

		private static string ConvertToPascalCase(string uppercaseUnderscore)
        {
            var sb = new StringBuilder();
            foreach (var word in uppercaseUnderscore.Split('_'))
            {
                sb.Append(word[0]);
                sb.Append(word.Substring(1).ToLowerInvariant());
            }
            return sb.ToString();
        }