class Client(object):
  def __init__(self, endpoint, credentials):
    self.net_client = NetworkClient(endpoint, credentials)

  def get_net_client(self):
    return self.net_client

<#list clientCommands as cmd>
  def ${cmd.commandName}(self, request):
    return ${cmd.responseName}(self.net_client.get_response(request), request)

</#list>
