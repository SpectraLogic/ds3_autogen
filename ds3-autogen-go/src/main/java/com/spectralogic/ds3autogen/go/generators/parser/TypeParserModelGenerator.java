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

package com.spectralogic.ds3autogen.go.generators.parser;

import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type;
import com.spectralogic.ds3autogen.go.models.parser.TypeParser;

/**
 * Functional interface defining the generation of TypeParser models that are used to generate the
 * Go model parser files. This is used in {@link com.spectralogic.ds3autogen.go.GoCodeGenerator#generateTypeParser(Ds3Type, ImmutableMap)}
 */
@FunctionalInterface
public interface TypeParserModelGenerator<T extends TypeParser> {

    T generate(final Ds3Type ds3Type, ImmutableMap<String, Ds3Type> typeMap);
}
