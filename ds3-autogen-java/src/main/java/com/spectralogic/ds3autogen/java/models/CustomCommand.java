/*
 * ******************************************************************************
 *   Copyright 2014-2015 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.java.models;

public class CustomCommand extends Command {

    private final String customBody;

    public CustomCommand(
            final String name,
            final String requestName,
            final String responseName,
            final AnnotationInfo annotationInfo,
            final String customBody) {
        super(name, requestName, responseName, annotationInfo);
        this.customBody = customBody;
    }

    public String getCustomBody() {
        return customBody;
    }
}
