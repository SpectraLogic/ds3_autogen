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

import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.java.models.Element;

import static com.spectralogic.ds3autogen.utils.Ds3ElementUtil.getXmlTagName;

public class JobsApiBeanTypeGenerator extends BaseTypeGenerator {

    /**
     * Returns Jobs instead of an empty string as indicated within
     * the contract
     */
    @Override
    public String toNameToMarshal(final Ds3Type ds3Type) {
        return "Jobs";
    }

    /**
     * Converts a Ds3Element into an Element model. If the element is
     * the Jobs element, then its hasWrapper property is set to false.
     */
    @Override
    public Element toElement(final Ds3Element ds3Element) {
        final Boolean hasWrapper;
        if (ds3Element.getName().equals("Jobs")) {
            hasWrapper = false;
        } else {
            hasWrapper = hasWrapperAnnotations(ds3Element.getDs3Annotations());
        }
        return new Element(
                ds3Element.getName(),
                getXmlTagName(ds3Element),
                toElementAsAttribute(ds3Element.getDs3Annotations()),
                hasWrapper,
                ds3Element.getType(),
                ds3Element.getComponentType());
    }
}
