<#list requests as request>
class ${request.name}(AbstractRequest):
  def __init__(${pythonHelper.toRequestInitList(request.requiredArgs, request.optionalArgs, request.requestPayload)}):
    super(${request.name}, self).__init__()
    <#list request.requiredArgs as arg>
    self.${helper.camelToUnderscore(arg.name)} = ${helper.camelToUnderscore(arg.name)}
    </#list>
    <#list request.voidArgs as arg>
    self.query_params['${helper.camelToUnderscore(arg)}'] = ''
    </#list>
    <#if request.operation??>
    self.query_params['operation'] = '${request.operation}'
    </#if>

    <#if request.requestPayload??>
    ${request.requestPayload.assignmentCode}
    </#if>

    <#list request.optionalArgs as arg>
    if ${helper.camelToUnderscore(arg.name)} is not None:
      self.query_params['${helper.camelToUnderscore(arg.name)}'] = ${helper.camelToUnderscore(arg.name)}
    </#list>

    self.path = ${request.path}
    self.http_verb = HttpVerb.${request.httpVerb}

</#list>