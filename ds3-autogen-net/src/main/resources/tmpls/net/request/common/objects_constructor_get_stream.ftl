        <#list constructors as constructor>
        public ${name}(${netHelper.constructor(constructor.constructorArgs)}) {
            <#list constructor.constructorArgs as arg>
            this.${arg.getName()?cap_first} = ${netHelper.paramAssignmentRightValue(arg)};
            </#list>
            <#if constructor.operation??>
            this.QueryParams.Add("operation", "${constructor.operation.toString()?lower_case}");
            </#if>
            <#include "add_query_params.ftl" />

            if (!objects.ToList().TrueForAll(obj => obj.Size.HasValue))
            {
                throw new Ds3RequestException(Resources.ObjectsMissingSizeException);
            }
        }
        </#list>

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