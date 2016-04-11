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

package com.spectralogic.ds3autogen.c.models;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.c.helpers.StructHelper;

public class Source {
    final ImmutableList<Enum> enums;
    final ImmutableList<Struct> arrayStructs;
    final ImmutableSet<C_Type> arrayTypes;
    final ImmutableList<Struct> structs;
    final ImmutableList<Request> requests;

    public Source(
            final ImmutableList<Enum> enums,
            final ImmutableList<Struct> structs,
            final ImmutableList<Request> requests) {
        this.enums = enums;
        this.arrayTypes = StructHelper.getArrayStructMemberTypes(structs);
        this.arrayStructs = StructHelper.getArrayStructs(structs, arrayTypes);
        this.structs = StructHelper.filterArrayStructs(structs, arrayTypes);
        this.requests = requests;
    }

    public ImmutableList<Enum> getEnums() {
        return enums;
    }

    public ImmutableList<Struct> getArrayStructs() {
        return arrayStructs;
    }

    public ImmutableSet<C_Type> getArrayTypes() {
        return arrayTypes;
    }

    public ImmutableList<Struct> getStructs() {
        return structs;
    }

    public ImmutableList<Request> getRequests() {
        return requests;
    }
}
