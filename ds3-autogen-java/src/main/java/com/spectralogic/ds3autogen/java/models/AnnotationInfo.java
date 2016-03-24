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

import com.spectralogic.ds3autogen.utils.Helper;

/**
 * Used to store information for generating annotations within
 * the client
 */
public class AnnotationInfo {

    private final String responsePayloadModel;
    private final String action;
    private final String resource;

    public AnnotationInfo(
            final String responsePayloadModel,
            final String action,
            final String resource) {
        this.responsePayloadModel = responsePayloadModel;
        this.action = action;
        this.resource = resource;
    }

    public String getResponsePayloadModel() {
        return responsePayloadModel;
    }

    public String getAction() {
        return action;
    }

    public String getResource() {
        return resource;
    }
}
