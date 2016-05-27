<#include "../common/copyright.ftl" />

# Type Descriptors

<#include "types/type_descriptor.ftl" />

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
    self.process_response(response)

  def process_response(self, response):
    # this method must be implemented
    raise NotImplementedError("Request Implemented")

  def __check_status_codes__(self, expected_codes):
    if self.response.status not in expected_codes:
      err = "Return Code: Expected %s - Received %s" % (expected_codes, self.response.status)
      ds3error = XmlSerializer().to_ds3error(self.response.read())
      raise RequestFailed(err, ds3error)

<#include "responses/response.ftl" />
