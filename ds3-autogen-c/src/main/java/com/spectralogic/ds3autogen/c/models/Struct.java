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
import com.spectralogic.ds3autogen.c.helpers.StructHelper;

public class Struct {
    private final String name;
    private final String nameToMarshall;
    private final ImmutableList<StructMember> members;
    private final boolean isTopLevel;
    private final StructHelper structHelper;

    public Struct(
            final String name,
            final String nameToMarshall,
            final ImmutableList<StructMember> members,
            final boolean isTopLevel) {
        this.name = name;
        this.nameToMarshall = nameToMarshall;
        this.members = members;
        this.isTopLevel = isTopLevel;
        this.structHelper = StructHelper.getInstance();
    }

    public String getName() {
        return name;
    }

    public String getNameToMarshall() {
        return nameToMarshall;
    }

    public ImmutableList<StructMember> getStructMembers() {
        return members;
    }

    public boolean isTopLevel() {
        return isTopLevel;
    }

    public StructHelper getStructHelper() {
        return structHelper;
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Struct[" + getName() + "]\n");
        for (final StructMember structMember: getStructMembers()) {
            builder.append("  " + structMember.toString() + "\n");
        }
        return builder.toString();
    }
}
