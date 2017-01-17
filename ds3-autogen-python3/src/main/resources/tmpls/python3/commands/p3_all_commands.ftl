<#include "../../python/common/copyright.ftl" />

import xml.etree.ElementTree as xmldom
import os

from abc import ABCMeta
import posixpath
from .ds3network import *

<#include "../../python/commands/create_client.ftl">

# Models

<#include "../../python/commands/static_classes.ftl" />

# Type Descriptors

<#include "../../python/commands/types/type_descriptor.ftl" />

<#include "../../python/commands/types/static_type_descriptors.ftl" />

<#include "../../python/commands/types/parser_function.ftl" />

# Request Handlers

class AbstractRequest(object, metaclass=ABCMeta):
  def __init__(self):
    self.path = '/'
    self.http_verb = HttpVerb.GET
    self.query_params = {}
    self.headers = {}
    self.body = None

<#include "../../python/commands/requests/request.ftl" />

# Response Handlers

class AbstractResponse(object, metaclass=ABCMeta):
  def __init__(self, response, request):
    self.request = request
    self.response = response
    self.result = None
    self.meta_data = None
    self.process_response(response)
    self.process_meta_data(response)

  def process_meta_data(self, response):
    headers = response.getheaders()
    if not headers:
      return
    meta_data = {}
    for header in headers:
      if header[0].startswith('x-amz-meta-'):
        values = header[1].split(',')
        meta_data[header[0][11:]] = values
    self.meta_data = meta_data

  def process_response(self, response):
    # this method must be implemented
    raise NotImplementedError("Request Implemented")

  def __check_status_codes__(self, expected_codes):
    if self.response.status not in expected_codes:
      err = "Return Code: Expected %s - Received %s" % (expected_codes, self.response.status)
      ds3_error = XmlSerializer().to_ds3error(self.response.read(), self.response.status, self.response.reason)
      raise RequestFailed(err, ds3_error)

  def parse_int_header(self, key, headers):
    if not headers:
      return None
    for header in headers:
      if header[0].lower() == key.lower():
        return int(header[1])
    return None

<#include "../../python/commands/responses/response.ftl" />

<#include "../../python/commands/client/client_class.ftl" />
