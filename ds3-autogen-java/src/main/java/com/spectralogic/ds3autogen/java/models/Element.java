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

public class Element {

    private final String name;
    private final String xmlTagName;
    private final boolean isAttribute;
    private final boolean hasWrapper;
    private final String type;
    private final String componentType;

    public Element(
            final String name,
            final String xmlTagName,
            final boolean isAttribute,
            final boolean hasWrapper,
            final String type,
            final String componentType) {
        this.name = name;
        this.xmlTagName = xmlTagName;
        this.isAttribute = isAttribute;
        this.hasWrapper = hasWrapper;
        this.type = type;
        this.componentType = componentType;
    }

    public Element(
            final String name,
            final String type,
            final String componentType) {
        this(name, null, false, false, type, componentType);
    }

    public String getName() {
        return name;
    }

    public String getInternalName() {
        if (name.equalsIgnoreCase("protected")) {
            return name + "Flag";
        }
        return name;
    }

    public String getType() {
        return type;
    }

    public String getComponentType() {
        return componentType;
    }

    public String getXmlTagName() {
        return xmlTagName;
    }

    public boolean isAttribute() {
        return isAttribute;
    }

    public boolean hasWrapper() {
        return hasWrapper;
    }
}
