<#list requests as request>
class ${request.name}(AbstractRequest):
  ${request.documentation}
  def __init__(${pythonHelper.toRequestInitList(request.constructorParams)}):
    super(${request.name}, self).__init__()
    <#list request.assignments as arg>
    self.${arg} = ${arg}
    </#list>
    <#list request.queryParams as arg>
    self.query_params['${arg.getName()}'] = ${arg.getAssignment()}
    </#list>

    <#if request.additionalContent??>
    ${request.additionalContent}
    </#if>

    <#list request.optionalArgs as arg>
    if ${helper.camelToUnderscore(arg.name)} is not None:
      self.query_params['${helper.camelToUnderscore(arg.name)}'] = ${helper.camelToUnderscore(arg.name)}
    </#list>

    self.path = ${request.path}
    self.http_verb = HttpVerb.${request.httpVerb}

</#list>