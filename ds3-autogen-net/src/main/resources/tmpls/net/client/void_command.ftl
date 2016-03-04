        <#list voidCommands as cmd>
        public ${cmd.getResponseType()} ${cmd.getCommandName()}(${cmd.getRequestName()} request)
        {
            using (var response = _netLayer.Invoke(request))
            {
                ResponseParseUtilities.HandleStatusCode(response, HttpStatusCode.${cmd.getHttpResponseCode()});
            }
        }
        </#list>