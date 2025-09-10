class Client(object):
    def __init__(self, endpoint, credentials, proxy=None):
        self.net_client = NetworkClient(endpoint, credentials)
        if proxy is not None:
            self.net_client = self.net_client.with_proxy(proxy)

    def get_net_client(self):
        return self.net_client

<#list clientCommands as cmd>
    ${cmd.documentation}
    def ${cmd.commandName}(${cmd.functionParams}):
        if not isinstance(request, ${cmd.requestType}):
            raise TypeError('request for ${cmd.commandName} should be of type ${cmd.requestType} but was ' + request.__class__.__name__)
        with self.net_client.open_response(request) as resp:
            return ${cmd.responseName}(resp, request)
</#list>
