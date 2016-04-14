        internal static ${parser.getName()} Parse${parser.getName()}(XElement element)
        {
            return new ${parser.getName()}
            {
                //TODO
                <#list parser.getElements() as elmt>
                ${elmt.getNetType()} ${elmt.getName()} = Parse${elmt.getType()?cap_first}(element.Element("${elmt.getXmlTag()}")),
                </#list>
            };
        }

        public static ${parser.getName()} ParseNullable${parser.getName()}(XElement element)
        {
            return element == null || element.IsEmpty
                ? null
                : Parse${parser.getName()}(content);
        }
