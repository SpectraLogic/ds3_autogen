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

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

public class Struct {
    private final String name;
    private final String nameToMarshall;
    private final ImmutableList<StructMember> members;
    private final boolean isTopLevel;
    private final boolean isArrayMember;
    private final boolean hasArrayMembers;
    private final boolean isEmbedded;

    public Struct(
            final String name,
            final ImmutableList<StructMember> members) {
        this.name = name;
        this.nameToMarshall = "Data";
        this.members = members;
        this.isTopLevel = false;
        this.isArrayMember = false;
        this.hasArrayMembers = false;
        this.isEmbedded = false;
    }

    public Struct(
            final String name,
            final String nameToMarshall,
            final ImmutableList<StructMember> members,
            final boolean isTopLevel,
            final boolean isArrayMember,
            final boolean hasArrayMembers,
            final boolean isEmbedded) {
        this.name = name;
        this.nameToMarshall = nameToMarshall;
        this.members = members;
        this.isTopLevel = isTopLevel;
        this.isArrayMember = isArrayMember;
        this.hasArrayMembers = hasArrayMembers;
        this.isEmbedded = isEmbedded;
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

    public boolean isArrayMember() {
        return isArrayMember;
    }

    public boolean hasArrayMembers() {
        return hasArrayMembers;
    }

    public boolean isEmbedded() {
        return isEmbedded;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Struct[" + getName() + "]" + (isTopLevel() ? " TopLevel" : "") + (isArrayMember() ? " ArrayMember" : "") + "\n");
        for (final StructMember structMember: getStructMembers()) {
            builder.append("  " + structMember.toString()
                    + (!isEmpty(structMember.getNameToMarshall()) ? " " + structMember.getNameToMarshall() :"")
                    + (!isEmpty(structMember.getEncapsulatingTag()) ? " " + structMember.getEncapsulatingTag() : "")
                    + (structMember.isAttribute() ? " ATTR" : "") + (structMember.hasWrapper() ? " WRAP" : "") + "\n");
        }
        return builder.toString();
    }
}
