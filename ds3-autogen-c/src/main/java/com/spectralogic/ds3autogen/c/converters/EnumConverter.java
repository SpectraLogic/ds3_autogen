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

package com.spectralogic.ds3autogen.c.converters;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type;
import com.spectralogic.ds3autogen.c.helpers.EnumHelper;
import com.spectralogic.ds3autogen.c.models.Enum;
import com.spectralogic.ds3autogen.utils.Helper;

public final class EnumConverter {
    private EnumConverter() {}

    public static Enum toEnum(final Ds3Type ds3Type) {
        final ImmutableList<String> valuesList = EnumHelper.convertDs3EnumConstants(ds3Type);
        return new Enum(
                EnumHelper.getDs3Type(ds3Type.getName()),
                valuesList,
                requiresMatcher(ds3Type));
    }

    public static boolean requiresMatcher(final Ds3Type ds3Type) {
        switch(Helper.unqualifiedName(ds3Type.getName())) {
            case "Severity":
            case "Application":
            case "RestActionType":
            case "RestDomainType":
            case "RestOperationType":
            case "RestResourceType":
            case "SqlOperation":
                return false;
        }
        return true;
    }
}
