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

package com.spectralogic.ds3autogen.api.models;

import com.google.common.collect.ImmutableList;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Ds3Type {

    private final String name;
    private final ImmutableList<Ds3Element> elements;
    private final ImmutableList<Ds3EnumConstant> enumConstants;

    public Ds3Type(
            final String name,
            final ImmutableList<Ds3Element> elements,
            final ImmutableList<Ds3EnumConstant> enumConstants) {
        this.name = name;
        this.elements = elements;
        this.enumConstants = enumConstants;
    }

    public Ds3Type(
            final String name,
            final ImmutableList<Ds3Element> elements) {
        this(name, elements, null);
    }

    public String getName() {
        return name;
    }

    public ImmutableList<Ds3Element> getElements() {
        return elements;
    }

    public ImmutableList<Ds3EnumConstant> getEnumConstants() {
        return enumConstants;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getElements(), getEnumConstants());
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Ds3Type)) {
            return false;
        }
        final Ds3Type type = (Ds3Type) obj;
        if (!this.getName().equals(type.getName())) {
            return false;
        }

        return listEquals(this.getElements(), type.getElements())
                && listEquals(this.getEnumConstants(), type.getEnumConstants());
    }

    protected static <E> boolean listEquals(
            final Collection<E> leftCollection,
            final Collection<E> rightCollection) {
        if (isEmpty(leftCollection) && isEmpty(rightCollection)) {
            return true;
        }
        if (isEmpty(leftCollection)
                || isEmpty(rightCollection)
                || leftCollection.size() != rightCollection.size()) {
            return false;
        }
        Set<E> set = new HashSet<E>(leftCollection);
        for (final E element : rightCollection) {
            if (set.contains(element)) {
                set.remove(element);
            }
        }
        return set.size() == 0;
    }

    //TODO remove (temporary until dependencies decided)
    public static boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}
