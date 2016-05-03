        <#list specializedCommands as cmd>
        public ${cmd.getResponseType()} ${cmd.getCommandName()}(${cmd.getRequestName()} request)
        {
            ${cmd.getFunctionBody()}
        }
        </#list>