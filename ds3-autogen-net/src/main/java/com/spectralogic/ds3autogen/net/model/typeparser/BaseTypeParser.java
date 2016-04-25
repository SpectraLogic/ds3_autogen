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

package com.spectralogic.ds3autogen.net.model.typeparser;

import com.google.common.collect.ImmutableList;

public class BaseTypeParser {

    /** List of non-enum types (described by elements) */
    private final ImmutableList<TypeParser> typeParsers;

    /** List of enum type names */
    private final ImmutableList<String> enumParsers;

    public BaseTypeParser(
            final ImmutableList<TypeParser> typeParsers,
            final ImmutableList<String> enumParsers) {
        this.typeParsers = typeParsers;
        this.enumParsers = enumParsers;
    }

    public ImmutableList<TypeParser> getTypeParsers() {
        return typeParsers;
    }

    public ImmutableList<String> getEnumParsers() {
        return enumParsers;
    }
}
