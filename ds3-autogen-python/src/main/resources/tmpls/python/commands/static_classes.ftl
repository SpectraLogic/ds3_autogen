class HeadRequestStatus(object):
  """Head bucket and head object return values"""
  EXISTS = 'EXISTS'  # 200
  NOTAUTHORIZED = 'NOTAUTHORIZED' # 403
  DOESNTEXIST = 'DOESNTEXIST' # 404
  UNKNOWN = 'UNKNOWN'

class FileObject(object):
  def __init__(self, name, size=None):
    self.name = name
    self.size = size

  def to_xml(self):
    xml_object = xmldom.Element('Object')
    xml_object.set('Name', posixpath.normpath(self.name))
    if self.size is not None:
      xml_object.set('Size', str(self.size))
    return xml_object

class FileObjectList(object):
  def __init__(self, object_list):
    for obj in object_list:
      if not isinstance(obj, FileObject):
        raise TypeError("FileObjectList should only contain type: FileObject")
    self.object_list = object_list

  def to_xml(self):
    xml_object_list = xmldom.Element('Objects')
    for obj in self.object_list:
      xml_object_list.append(obj.to_xml())
    return xml_object_list

class DeleteObject(object):
  def __init__(self, key):
    self.key = key

  def to_xml(self):
    xml_key = xmldom.Element('key')
    xml_key.text = self.key

    xml_object = xmldom.Element('Object')
    xml_object.append(xml_key)
    return xml_object

class DeleteObjectList(object):
  def __init__(self, object_list):
    for obj in object_list:
      if not isinstance(obj, DeleteObject):
        raise TypeError("DeleteObjectList should only contain type: DeleteObject")
    self.object_list = object_list

  def to_xml(self):
    xml_object_list = xmldom.Element('Delete')
    for obj in self.object_list:
      xml_object_list.append(obj.to_xml())
    return xml_object_list

class Part(object):
  def __init__(self, part_number, etag):
    self.part_number = str(part_number)
    self.etag = etag

  def to_xml(self):
    xml_part_number = xmldom.Element('PartNumber')
    xml_part_number.text = self.part_number

    xml_etag = xmldom.Element('ETag')
    xml_etag.text = self.etag

    xml_part = xmldom.Element('Part')
    xml_part.append(xml_part_number)
    xml_part.append(xml_etag)
    return xml_part

class PartList(object):
  def __init__(self, part_list):
    for part in part_list:
      if not isinstance(part, Part):
        raise TypeError("PartList should only contain type: Part")
    self.part_list = part_list

  def to_xml(self):
    xml_part_list = xmldom.Element('CompleteMultipartUpload')
    for part in self.part_list:
      xml_part_list.append(part.to_xml())
    return xml_part_list
