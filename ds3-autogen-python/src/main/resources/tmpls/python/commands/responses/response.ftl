<#list responses as response>
class ${response.name}(AbstractResponse):
    ${response.initResponseCode}
    def process_response(self, response):
        self.__check_status_codes__([${pythonHelper.toCommaSeparatedList(response.codes)}])
        ${response.parseResponseCode}


</#list>