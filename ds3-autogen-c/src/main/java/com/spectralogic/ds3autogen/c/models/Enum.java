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

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.c.helpers.EnumHelper;

public class Enum {
    private final String name;
    private final ImmutableList<String> values;
    private final EnumHelper enumHelper;

    public Enum(
            final String name,
            final ImmutableList<String> values) {
        this.name = name;
        this.values = values;
        this.enumHelper = EnumHelper.getInstance();
    }

    public String getName() {
        return this.name;
    }

    public ImmutableList<String> getValues() {
        return values;
    }

    public EnumHelper getEnumHelper() {
        return enumHelper;
    }
}
