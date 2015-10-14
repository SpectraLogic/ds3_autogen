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

package com.spectralogic.d3autogen.models.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseType;

public class ResponseType {

    @JacksonXmlProperty(isAttribute = true, localName = "Type")
    private String type;

    @JacksonXmlProperty(isAttribute = true, localName = "ComponentType")
    private String componentType;

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(final String componentType) {
        this.componentType = componentType;
    }

    public Ds3ResponseType toDs3ResponseType() {
        final Ds3ResponseType ds3ResponseType = new Ds3ResponseType(type, componentType);
        return ds3ResponseType;
    }
}
