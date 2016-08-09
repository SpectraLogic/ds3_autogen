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

package com.spectralogic.ds3autogen.java.generators.typemodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3EnumConstant;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type;
import com.spectralogic.ds3autogen.java.models.Element;
import com.spectralogic.ds3autogen.java.models.EnumConstant;

public interface TypeGeneratorUtil {

    /**
     * Gets the NameToMarshal value that describes this Ds3Type. This refers to
     * the xml encapsulating tag for the payload described by this model
     */
    String toNameToMarshal(final Ds3Type ds3Type);

    /**
     * Converts a list of Ds3Elements into al ist of Element models
     */
    ImmutableList<Element> toElementList(final ImmutableList<Ds3Element> ds3Elements);

    /**
     * Converts a Ds3Element into an Element model
     */
    Element toElement(final Ds3Element ds3Element);

    /**
     * Converts a list of Ds3EnumConstants into a list of EnumConstant models
     */
    ImmutableList<EnumConstant> toEnumConstantList(final ImmutableList<Ds3EnumConstant> ds3EnumConstants);

    /**
     * Gets all the required imports that the Model will need in order to properly
     * generate the java model code
     */
    ImmutableList<String> getAllImports(final Ds3Type ds3Type);
}
