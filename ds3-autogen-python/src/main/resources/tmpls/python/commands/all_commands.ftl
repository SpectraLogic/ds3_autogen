<#include "../common/copyright.ftl" />

import xml.etree.ElementTree as xmldom
import os

from abc import ABCMeta
import posixpath
from ds3network import *

def createClientFromEnv():
  """Build a Client from environment variables.

     Required: DS3_ACCESS_KEY, DS3_SECRET_KEY, DS3_ENDPOINT

     Optional: http_proxy
  """
  access_key = os.environ.get('DS3_ACCESS_KEY')
  secret_key = os.environ.get('DS3_SECRET_KEY')
  endpoint = os.environ.get('DS3_ENDPOINT')
  proxy = os.environ.get('http_proxy')

  if None in (access_key, secret_key, endpoint):
    raise Exception('Required environment variables are not set: DS3_ACCESS_KEY, DS3_SECRET_KEY, DS3_ENDPOINT')

  creds = Credentials(access_key, secret_key)
  client = Client(endpoint, creds, proxy)
  return client

# Models

<#include "static_classes.ftl" />

# Type Descriptors

<#include "types/type_descriptor.ftl" />

<#include "types/static_type_descriptors.ftl" />

<#include "types/parser_function.ftl" />

# Request Handlers

class AbstractRequest(object):
  __metaclass__ = ABCMeta
  def __init__(self):
    self.path = '/'
    self.http_verb = HttpVerb.GET
    self.query_params = {}
    self.headers = {}
    self.body = None

<#include "requests/request.ftl" />

# Response Handlers

class AbstractResponse(object):
  __metaclass__ = ABCMeta
  def __init__(self, response, request):
    self.request = request
    self.response = response
    self.object_data = None
    self.result = None
    self.process_response(response)

  def process_response(self, response):
    # this method must be implemented
    raise NotImplementedError("Request Implemented")

  def __check_status_codes__(self, expected_codes):
    if self.response.status not in expected_codes:
      err = "Return Code: Expected %s - Received %s" % (expected_codes, self.response.status)
      ds3_error = XmlSerializer().to_ds3error(self.response.read())
      raise RequestFailed(err, ds3_error)

<#include "responses/response.ftl" />

<#include "client/client_class.ftl" />
