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

package com.spectralogic.ds3autogen.net.model.client;

/**
 * Defines a command within the .net client with the .net code
 * for the function body specified
 */
public class SpecializedCommand extends PayloadCommand {

    private final String functionBody;

    public SpecializedCommand(
            final String requestName,
            final String commandName,
            final String responseType,
            final String commandDocumentation,
            final String functionBody) {
        super(requestName, commandName, responseType, commandDocumentation);
        this.functionBody = functionBody;
    }

    public String getFunctionBody() {
        return functionBody;
    }
}
