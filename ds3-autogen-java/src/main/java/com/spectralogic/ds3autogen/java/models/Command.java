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

package com.spectralogic.ds3autogen.java.models;

public class Command {
    private final String name;
    private final String requestName;
    private final String responseName;
    private final String documentation;
    private final AnnotationInfo annotationInfo;

    public Command(
            final String name,
            final String requestName,
            final String responseName,
            final String documentation,
            final AnnotationInfo annotationInfo) {
        this.name = name;
        this.requestName = requestName;
        this.responseName = responseName;
        this.documentation = documentation;
        this.annotationInfo = annotationInfo;
    }

    public String getName() {
        return name;
    }

    public String getRequestName() {
        return requestName;
    }

    public String getResponseName() {
        return responseName;
    }

    public AnnotationInfo getAnnotationInfo() {
        return annotationInfo;
    }

    public String getDocumentation() {
        return documentation;
    }
}
