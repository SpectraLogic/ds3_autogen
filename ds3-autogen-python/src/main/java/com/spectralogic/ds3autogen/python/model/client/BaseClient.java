/*
 * ******************************************************************************
 *   Copyright 2014-2017 Spectra Logic Corporation. All Rights Reserved.
 *   Licensed under the Apache License, Version 2.0 (the "License"). You may not use
 *   this file except in compliance with the License. A copy of the License is located at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   or in the "license" file accompanying this file.
 *   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 *   CONDITIONS OF ANY KIND, either express or implied. See the License for the
 *   specific language governing permissions and limitations under the License.
 * ****************************************************************************
 */

package com.spectralogic.ds3autogen.python.model.client;

public class BaseClient {

    private final String commandName;
    private final String requestType;
    private final String responseName;
    private final String documentation;
    private final String functionParams; /** comma separated list of function parameters */
    private final String responseParams; /** comma separated list of parameters passed to response */

    public BaseClient(
            final String commandName,
            final String requestType,
            final String responseName,
            final String documentation,
            final String functionParams,
            final String responseParams) {
        this.commandName = commandName;
        this.requestType = requestType;
        this.responseName = responseName;
        this.documentation = documentation;
        this.functionParams = functionParams;
        this.responseParams = responseParams;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getResponseName() {
        return responseName;
    }

    public String getDocumentation() {
        return documentation;
    }

    public String getFunctionParams() {
        return functionParams;
    }

    public String getResponseParams() {
        return responseParams;
    }
}
