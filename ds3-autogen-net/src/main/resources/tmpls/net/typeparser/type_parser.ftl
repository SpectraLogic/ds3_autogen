        internal static ${parser.getName()} Parse${parser.getName()}(XElement element)
        {
            return new ${parser.getName()}
            {
                //TODO
                <#list parser.getVariables() as var>
                ${var.getNullableType()} ${var.getName()}
                </#list>
            };
        }

        public static ${parser.getName()} ParseNullable${parser.getName()}(XElement element)
        {
            return element == null || element.IsEmpty
                ? null
                : Parse${parser.getName()}(content);
        }
