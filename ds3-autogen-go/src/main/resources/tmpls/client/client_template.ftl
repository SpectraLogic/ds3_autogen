<#include "../common/copyright.ftl" />

package ds3

import "ds3/models"

<#list commands as cmd>
func (client *Client) ${cmd.name}(request *models.${cmd.name}Request) (*models.${cmd.name}Response, error) {
    // Invoke the HTTP request.
    response, requestErr := client.netLayer.Invoke(request)
    if requestErr != nil {
        return nil, requestErr
    }

    // Create a response object based on the result.
    return models.New${cmd.name}Response(response)
}
</#list>
