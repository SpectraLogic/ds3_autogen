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

package com.spectralogic.ds3autogen.c.models;

import com.google.common.collect.ImmutableList;

public class Enum {
    private final String name;
    private final ImmutableList<String> values;
    private final boolean requiresMatcher;

    public Enum(
            final String name,
            final ImmutableList<String> values) {
        this.name = name;
        this.values = values;
        this.requiresMatcher = true;
    }

    public Enum(
            final String name,
            final ImmutableList<String> values,
            final boolean requiresMatcher) {
        this.name = name;
        this.values = values;
        this.requiresMatcher = requiresMatcher;
    }
    public String getName() {
        return this.name;
    }

    public ImmutableList<String> getValues() {
        return values;
    }

    public boolean requiresMatcher() {
        return requiresMatcher;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Enum[" + getName() + "]\n");
        for (final String value : getValues()) {
            builder.append("  " + value + "\n");
        }
        return builder.toString();
    }
}
