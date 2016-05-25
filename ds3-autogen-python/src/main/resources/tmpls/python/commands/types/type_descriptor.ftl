<#list typeDescriptors as type>
class ${type.name}(object):
  def __init__(self):
    self.attributes = [${pythonHelper.toTypeContentLines(type.attributes, 3)}]
    self.elements = {${pythonHelper.toTypeContentLines(type.elements, 3)}}
    self.element_lists = {${pythonHelper.toTypeContentLines(type.elementLists, 3)}}

</#list>