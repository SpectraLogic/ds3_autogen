        public ${name}(${netHelper.constructor(constructorArgs)}) {
            <#list constructorArgs as arg>
            this.${arg.getName()?cap_first} = ${arg.getName()?uncap_first};
            </#list>
            <#if operation??>
            this.QueryParams.Add("operation", "${operation.toString()?lower_case}");
            </#if>
            if (!objects.TrueForAll(obj => obj.Size.HasValue))
            {
                throw new Ds3RequestException(Resources.ObjectsMissingSizeException);
            }
        }

        internal override Stream GetContentStream()
        {
            return new XDocument()
                .AddFluent(
                    new XElement("Objects").AddAllFluent(
                        from obj in this.Objects
                        select new XElement("Object")
                            .SetAttributeValueFluent("Name", obj.Name)
                            .SetAttributeValueFluent("Size", obj.Size.Value.ToString("D"))
                    )
                )
                .WriteToMemoryStream();
        }