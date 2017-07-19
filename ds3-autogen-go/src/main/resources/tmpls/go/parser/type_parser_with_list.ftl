<#include "base_type_parser.ftl" />

func parse${modelName}Slice(tagName string, xmlNodes []XmlNode, aggErr *AggregateError) []${modelName} {
    var result []${modelName}
    for _, curXmlNode := range xmlNodes {
        if curXmlNode.XMLName.Local == tagName {
            var curResult ${modelName}
            curResult.parse(&curXmlNode, aggErr)
            result = append(result, curResult)
        } else {
            log.Printf("WARNING: Discovered unexpected xml tag '%s' when expected tag '%s' when parsing ${modelName} struct.\n", curXmlNode.XMLName.Local, tagName)
        }
    }
    return result
}
