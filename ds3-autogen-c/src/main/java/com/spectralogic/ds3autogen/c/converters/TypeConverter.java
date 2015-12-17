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

import com.spectralogic.ds3autogen.api.models.*;
import com.spectralogic.ds3autogen.c.models.*;

public class TypeConverter {
    private final Ds3Type ds3Type;

    private TypeConverter(final Ds3Type ds3Type) {
        this.ds3Type = ds3Type;
    }

    private Type convert() {
        return new Type(
                this.ds3Type.getName(),
                this.ds3Type.getEnumConstants(),
                this.ds3Type.getElements());
    }

    public static Type toType(final Ds3Type ds3Type) {
        final TypeConverter converter = new TypeConverter(ds3Type);
        return converter.convert();
    }
}
