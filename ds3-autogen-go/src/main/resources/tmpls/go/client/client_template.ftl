<#include "../common/copyright.ftl" />

package ds3

import "ds3/models"

<#list commandsNoRedirect as cmd>
func (client *Client) ${cmd.name}(request *models.${cmd.name}Request) (*models.${cmd.name}Response, error) {
    networkRetryDecorator := networking.NewNetworkRetryDecorator(&(client.netLayer), client.clientPolicy.maxRetries)

    // Invoke the HTTP request.
    response, requestErr := networkRetryDecorator.Invoke(request)
    if requestErr != nil {
        return nil, requestErr
    }

    // Create a response object based on the result.
    return models.New${cmd.name}Response(response)
}
</#list>

<#list commandsWithRedirect as cmd>
func (client *Client) ${cmd.name}(request *models.${cmd.name}Request) (*models.${cmd.name}Response, error) {
    networkRetryDecorator := networking.NewNetworkRetryDecorator(&(client.netLayer), client.clientPolicy.maxRetries)
    httpRedirectDecorator := networking.NewHttpTempRedirectDecorator(&networkRetryDecorator, client.clientPolicy.maxRedirect)

    // Invoke the HTTP request.
    response, requestErr := httpRedirectDecorator.Invoke(request)
    if requestErr != nil {
        return nil, requestErr
    }

    // Create a response object based on the result.
    return models.New${cmd.name}Response(response)
}
</#list>