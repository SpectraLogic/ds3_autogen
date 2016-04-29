/*
 * ******************************************************************************
 *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.net.generators.typemodels;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

/**
 * This generator is used to change the element named Objects into ObjectsList.
 * This is used to generate the Objects class to ensure that a parameter does
 * not conflict with the class name.
 */
public class ObjectsGenerator extends BaseTypeGenerator {

    /**
     * Converts a Ds3Element name into the sdk's name for that element.
     * Element 'Objects' is renamed to 'ObjectsList' to prevent conflict
     * with class name
     */
    @Override
    public String toElementName(final String elementName) {
        if (isEmpty(elementName)) {
            throw new IllegalArgumentException("Element name is empty");
        }
        if (elementName.equals("Objects")) {
            return elementName + "List";
        }
        return elementName;
    }
}
