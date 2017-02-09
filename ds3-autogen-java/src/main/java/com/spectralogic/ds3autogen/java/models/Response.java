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
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;

public class Response {
    private final String packageName;
    private final String name;
    private final String parentClass;

    /** comma separated list of constructor parameters */
    private final String constructorParams;

    private final ImmutableSet<String> imports;
    private final ImmutableList<Arguments> params;

    public Response(
            final String packageName,
            final String name,
            final String parentClass,
            final String constructorParams,
            final ImmutableSet<String> imports,
            final ImmutableList<Arguments> params) {
        this.packageName = packageName;
        this.name = name;
        this.constructorParams = constructorParams;
        this.parentClass = parentClass;
        this.imports = imports;
        this.params = params;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public ImmutableSet<String> getImports() {
        return imports;
    }

    public String getParentClass() {
        return parentClass;
    }

    public String getConstructorParams() {
        return constructorParams;
    }

    public ImmutableList<Arguments> getParams() {
        return params;
    }
}
