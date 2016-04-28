        public static ${parser.getName()} Parse${parser.getName()}(XElement element)
        {
            return new ${parser.getName()}
            {
                ${netHelper.commaSeparateStrings(parser.getParseElements(), 4)}
            };
        }

        public static ${parser.getName()} ParseNullable${parser.getName()}(XElement element)
        {
            return element == null || element.IsEmpty
                ? null
                : Parse${parser.getName()}(element);
        }
