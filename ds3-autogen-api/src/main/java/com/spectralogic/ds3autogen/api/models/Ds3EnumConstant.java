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

package com.spectralogic.ds3autogen.api.models;

import com.google.common.collect.ImmutableList;

public class Ds3EnumConstant {

    private final String name;
    private final ImmutableList<Ds3Property> ds3Properties;

    public Ds3EnumConstant(
            final String name,
            final ImmutableList<Ds3Property> ds3Properties) {
        this.name = name;
        this.ds3Properties = ds3Properties;
    }

    public String getName() {
        return name;
    }

    public ImmutableList<Ds3Property> getDs3Properties() {
        return ds3Properties;
    }
}
