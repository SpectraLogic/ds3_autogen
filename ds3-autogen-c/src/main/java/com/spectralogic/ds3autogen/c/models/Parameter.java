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

package com.spectralogic.ds3autogen.c.models;

import com.google.common.base.Objects;

public class Parameter {
    private final String parameterType;
    private final ParameterModifier typeModifier; // const, static etc
    private final String name;
    private final ParameterPointerType parameterPointerType; // *, **, &
    private final boolean isRequired;

    public Parameter(final ParameterModifier typeModifier,
                     final String parameterType,
                     final String name,
                     final ParameterPointerType parameterPointerType,
                     final boolean isRequired) {
        this.parameterType = parameterType;
        this.typeModifier = typeModifier;
        this.name = name;
        this.parameterPointerType = parameterPointerType;
        this.isRequired = isRequired;
    }

    public String getParameterType() {
        return parameterType;
    }

    public ParameterModifier getTypeModifier() {
        return typeModifier;
    }

    public String getName() {
        return name;
    }

    public ParameterPointerType getParameterPointerType() {
        return parameterPointerType;
    }

    public boolean isRequired() {
        return isRequired;
    }

    @Override
    public String toString() {
        if (typeModifier != ParameterModifier.NONE) {
            return typeModifier + parameterType + parameterPointerType + " " + name;
        }
        return parameterType + parameterPointerType + " " + name;
    }

    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Parameter)) {
            return false;
        }
        final Parameter parameter = (Parameter) obj;
        return getName().equals(parameter.getName())
            && getParameterType().equals(parameter.getParameterType())
            && getParameterPointerType().equals(parameter.getParameterPointerType())
            && getTypeModifier().equals(parameter.getTypeModifier());
    }

    public int hashCode() {
        return Objects.hashCode(getName(), getParameterType(), getTypeModifier(), getParameterPointerType(), isRequired());
    }

}
