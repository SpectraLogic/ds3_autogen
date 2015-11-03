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

package com.spectralogic.ds3autogen.net.helpers;

import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3TypeMapper;
import com.spectralogic.ds3autogen.net.model.Request;

public class RequestConverter {
    public static Request toRequest(final Ds3Request ds3Request, final Ds3TypeMapper ds3TypeMapper, final String commandsNamespace) {
        return new Request(getName(ds3Request));
    }

    private static String getName(final Ds3Request ds3Request) {
        final String name = ds3Request.getName();
        final int lastIndex = name.lastIndexOf(".");

        return name.substring(lastIndex + 1);
    }
}
