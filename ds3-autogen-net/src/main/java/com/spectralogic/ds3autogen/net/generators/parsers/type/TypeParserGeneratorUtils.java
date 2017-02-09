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

package com.spectralogic.ds3autogen.net.generators.parsers.type;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element;
import com.spectralogic.ds3autogen.net.generators.parsers.element.NullableElement;

/**
 * Contains the interface for functions that are used to convert a Ds3Type
 * into the Type Parser model within Type Parser Generators
 */
public interface TypeParserGeneratorUtils {

    /**
     * Converts a list of Ds3Elements into their .net parsing code
     */
    ImmutableList<String> toParseElements(final ImmutableList<Ds3Element> ds3Elements, final boolean isObjectsType);

    /**
     * Converts a list of Ds3Elements into a list of Nullable Elements
     */
    ImmutableList<NullableElement> toNullableElementsList(final ImmutableList<Ds3Element> ds3Elements, final boolean isObjectsType);
}
