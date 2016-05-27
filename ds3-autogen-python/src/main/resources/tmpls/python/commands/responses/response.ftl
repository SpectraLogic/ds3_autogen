<#list responses as response>
class ${response.name}(AbstractResponse):
  def process_response(self, response):
    self.__check_status_codes__([${pythonHelper.toCommaSeparatedList(response.codes)}])
    self.result = ${response.parseResponseCode}

</#list>