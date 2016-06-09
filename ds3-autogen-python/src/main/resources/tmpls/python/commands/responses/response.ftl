<#list responses as response>
class ${response.name}(AbstractResponse):
  def __init__(self, response, request):
    AbstractResponse.__init__(self, response, request)

  def process_response(self, response):
    self.__check_status_codes__([${pythonHelper.toCommaSeparatedList(response.codes)}])
    ${response.parseResponseCode}

</#list>