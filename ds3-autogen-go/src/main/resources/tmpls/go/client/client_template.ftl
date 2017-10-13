<#include "../common/copyright.ftl" />

package ds3

import (
    "ds3/models"
    "ds3/networking"
)

<#list commandsNoRedirect as cmd>
func (client *Client) ${cmd.name}(request *models.${cmd.name}Request) (*models.${cmd.name}Response, error) {
    // Build the http request
    httpRequest, err := networking.NewHttpRequestBuilder().
        <#list cmd.requestBuildLine as line>
        ${line.line}
        </#list>
        Build(client.connectionInfo)

    if err != nil {
        return nil, err
    }

    networkRetryDecorator := networking.NewNetworkRetryDecorator(&(client.sendNetwork), client.clientPolicy.maxRetries)

    // Invoke the HTTP request.
    response, requestErr := networkRetryDecorator.Invoke(httpRequest)
    if requestErr != nil {
        return nil, requestErr
    }

    // Create a response object based on the result.
    return models.New${cmd.name}Response(response)
}

</#list>

<#list commandsWithRedirect as cmd>
func (client *Client) ${cmd.name}(request *models.${cmd.name}Request) (*models.${cmd.name}Response, error) {
    // Build the http request
    httpRequest, err := networking.NewHttpRequestBuilder().
        <#list cmd.requestBuildLine as line>
        ${line.line}
        </#list>
        Build(client.connectionInfo)

    if err != nil {
        return nil, err
    }

    networkRetryDecorator := networking.NewNetworkRetryDecorator(&(client.sendNetwork), client.clientPolicy.maxRetries)
    httpRedirectDecorator := networking.NewHttpTempRedirectDecorator(&networkRetryDecorator, client.clientPolicy.maxRedirect)

    // Invoke the HTTP request.
    response, requestErr := httpRedirectDecorator.Invoke(httpRequest)
    if requestErr != nil {
        return nil, requestErr
    }

    // Create a response object based on the result.
    return models.New${cmd.name}Response(response)
}

</#list>