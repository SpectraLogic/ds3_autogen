        <#list payloadCommands as cmd>
        public ${cmd.getResponseType()} ${cmd.getCommandName()}(${cmd.getRequestName()} request)
        {
            return new ${cmd.getResponseType()}Parser().Parse(request, _netLayer.Invoke(request));
        }
        </#list>