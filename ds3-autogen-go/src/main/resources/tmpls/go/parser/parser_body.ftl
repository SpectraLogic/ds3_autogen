func (${modelName?uncap_first} *${modelName?cap_first}) parse(xmlNode *XmlNode, aggErr *AggregateError) {
    <#if helper.hasContent(attributes) == true>
    // Parse Attributes
    for _, attr := range xmlNode.Attrs {
        switch attr.Name.Local {
        <#list attributes as attr>
        case "${attr.xmlTag}":
            ${attr.parsingCode}
        </#list>
        }
    }
    </#if>

    <#if helper.hasContent(childNodes) == true>
    // Parse Child Nodes
    for _, child := range xmlNode.Children {
        switch child.XMLName.Local {
        <#list childNodes as child>
        case "${child.xmlTag}":
            ${child.parsingCode}
        </#list>
        }
    }
    </#if>
}
