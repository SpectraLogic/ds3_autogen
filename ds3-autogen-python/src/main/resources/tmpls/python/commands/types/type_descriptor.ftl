<#list typeDescriptors as type>
class ${type.name}(object):
    def __init__(self):
        self.attributes = [${pythonHelper.toCommaSeparatedLines(type.attributes, 3)}]
        self.elements = {${pythonHelper.toCommaSeparatedLines(type.elements, 3)}}
        self.element_lists = {${pythonHelper.toCommaSeparatedLines(type.elementLists, 3)}}


</#list>