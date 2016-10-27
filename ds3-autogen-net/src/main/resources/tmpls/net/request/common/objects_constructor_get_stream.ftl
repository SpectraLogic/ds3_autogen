        <#list constructors as constructor>
        public ${name}(${netHelper.constructor(constructor.constructorArgs)}) {
            <#list constructor.constructorArgs as arg>
            this.${arg.getName()?cap_first} = ${netHelper.paramAssignmentRightValue(arg)};
            </#list>
            <#if constructor.operation??>
            this.QueryParams.Add("operation", "${constructor.operation.toString()?lower_case}");
            </#if>
            <#include "add_query_params.ftl" />

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
                            .SetAttributeValueFluent("Size", ToDs3ObjectSize(obj))
                    )
                )
                .WriteToMemoryStream();
        }

        internal string ToDs3ObjectSize(Ds3Object ds3Object)
        {
            if (ds3Object.Size == null)
            {
                return null;
            }
            return ds3Object.Size.Value.ToString("D");
        }

        internal override long GetContentLength()
        {
            return GetContentStream().Length;
        }
