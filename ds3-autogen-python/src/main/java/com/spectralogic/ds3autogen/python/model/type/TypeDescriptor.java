/*
 * ******************************************************************************
 *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.python.model.type;

import com.google.common.collect.ImmutableList;

public class TypeDescriptor {

    private final String name;
    private final ImmutableList<TypeAttribute> attributes;
    private final ImmutableList<TypeElement> elements;
    private final ImmutableList<TypeElementList> elementLists;

    public TypeDescriptor(
            final String name,
            final ImmutableList<TypeAttribute> attributes,
            final ImmutableList<TypeElement> elements,
            final ImmutableList<TypeElementList> elementLists) {
        this.name = name;
        this.attributes = attributes;
        this.elements = elements;
        this.elementLists = elementLists;
    }

    public String getName() {
        return name;
    }

    public ImmutableList<TypeAttribute> getAttributes() {
        return attributes;
    }

    public ImmutableList<TypeElement> getElements() {
        return elements;
    }

    public ImmutableList<TypeElementList> getElementLists() {
        return elementLists;
    }
}
