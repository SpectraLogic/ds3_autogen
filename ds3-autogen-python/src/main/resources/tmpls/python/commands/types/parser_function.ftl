def parseModel(root, model):
  # Primitive type
  if model is None:
    return root.text

  result = {}
  # Adds attributes to the result model
  for attr in model.attributes:
    temp = root.attrib.get(attr)
    if temp is not None:
      result[attr] = temp
    else:
      result[attr] = None

  # Adds child xmlNodes to the result model
  for elmt in model.elements:
    xmlElement = root.find(elmt)
    if xmlElement is not None:
      result[elmt] = parseModel(xmlElement, model.elements[elmt])
    else:
      result[elmt] = None

  # Adds lists of child xmlNodes to the result model
  for elmt in model.element_lists:
    xmlElements = None
    if elmt[1] is None:
      # No encapsulating node
      xmlElements = root.findall(elmt[0])
    else:
      # Get nodes from within encapsulating node
      encapsNode = root.find(elmt[1])
      if encapsNode is not None:
        xmlElements = encapsNode.findall(elmt[0])

    tempList = []
    for xmlElmt in xmlElements:
      tempList.append(parseModel(xmlElmt, elmt[2]))
    result[elmt[0] + 'List'] = tempList

  return result
