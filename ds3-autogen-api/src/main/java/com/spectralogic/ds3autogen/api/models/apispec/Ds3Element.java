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

package com.spectralogic.ds3autogen.api.models.apispec;

import com.google.common.collect.ImmutableList;

import java.util.Objects;

public class Ds3Element {

    private final String name;
    private final String type;
    private final String componentType;
    private final ImmutableList<Ds3Annotation> ds3Annotations;
    private final boolean nullable;

    public Ds3Element(
            final String name,
            final String type,
            final String componentType,
            final ImmutableList<Ds3Annotation> ds3Annotations,
            final boolean nullable) {
        this.name = name;
        this.type = type;
        this.componentType = componentType;
        this.ds3Annotations = ds3Annotations;
        this.nullable = nullable;
    }

    public Ds3Element(
            final String name,
            final String type,
            final String componentType,
            final boolean nullable) {
        this(name, type, componentType, null, nullable);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public ImmutableList<Ds3Annotation> getDs3Annotations() {
        return ds3Annotations;
    }

    public String getComponentType() {
        return componentType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType(), getComponentType());
    }

    /**
     * Compares the name, type, and component type. Does not compare the Ds3Annotation list
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Ds3Element)) {
            return false;
        }
        final Ds3Element element = (Ds3Element) obj;
        return Objects.equals(this.getName(), element.getName())
                && Objects.equals(this.getType(), element.getType())
                && Objects.equals(this.getComponentType(), element.getComponentType());
    }

    public boolean isNullable() {
        return nullable;
    }
}
