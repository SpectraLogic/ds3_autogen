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

package com.spectralogic.ds3autogen.c.converters;

import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.c.models.Element;

public class ElementConverter {
    private final Ds3Element ds3Element;

    private ElementConverter(final Ds3Element ds3Element) {
        this.ds3Element = ds3Element;
    }

    private Element convert() {
        return new Element(
                this.ds3Element.getName(),
                this.ds3Element.getType(),
                this.ds3Element.getComponentType(),
                this.ds3Element.getDs3Annotations());
    }

    public static Element toElement(final Ds3Element ds3Type) {
        final ElementConverter converter = new ElementConverter(ds3Type);
        return converter.convert();
    }
}
