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

import com.google.common.collect.ImmutableList;

public class Model {

    private final String packageName;
    private final String name;
    private final String nameToMarshal;
    private final ImmutableList<Element> elements;
    private final ImmutableList<EnumConstant> enumConstants;
    private final ImmutableList<String> imports;

    public Model(final String packageName,
                 final String name,
                 final String nameToMarshal,
                 final ImmutableList<Element> elements,
                 final ImmutableList<EnumConstant> enumConstants,
                 final ImmutableList<String> imports) {
        this.packageName = packageName;
        this.name = name;
        this.nameToMarshal = nameToMarshal;
        this.elements = elements;
        this.enumConstants = enumConstants;
        this.imports = imports;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public ImmutableList<Element> getElements() {
        return elements;
    }

    public ImmutableList<String> getImports() {
        return imports;
    }

    public ImmutableList<EnumConstant> getEnumConstants() {
        return enumConstants;
    }

    public String getNameToMarshal() {
        return nameToMarshal;
    }
}
