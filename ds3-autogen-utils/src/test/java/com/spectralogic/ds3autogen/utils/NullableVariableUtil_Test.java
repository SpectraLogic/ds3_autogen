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

package com.spectralogic.ds3autogen.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Annotation;
import org.junit.Test;

import static com.spectralogic.ds3autogen.utils.NullableVariableUtil.isNullableElement;
import static com.spectralogic.ds3autogen.utils.NullableVariableUtil.isNullableType;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NullableVariableUtil_Test {

    @Test
    public void isNullableType_NullString_Test() {
        assertFalse(isNullableType(null));
    }

    @Test
    public void isNullableType_EmptyString_Test() {
        assertFalse(isNullableType(""));
    }

    @Test
    public void isNullableType_Test() {
        assertTrue(isNullableType("java.lang.Integer"));
        assertTrue(isNullableType("java.lang.Long"));
        assertTrue(isNullableType("java.lang.String"));
        assertTrue(isNullableType("java.lang.Double"));
        assertTrue(isNullableType("java.lang.Boolean"));

        assertFalse(isNullableType("int"));
        assertFalse(isNullableType("long"));
        assertFalse(isNullableType("String"));
        assertFalse(isNullableType("double"));
        assertFalse(isNullableType("boolean"));

    }

    @Test
    public void isNullableElement_NoAnnotations_Test() {
        assertTrue(isNullableElement("java.lang.Integer", null));

        assertFalse(isNullableElement(null, null));
        assertFalse(isNullableElement("", null));
        assertFalse(isNullableElement("int", null));
    }

    @Test
    public void isNullableElement_OptionalAnnotation_Test() {
        final ImmutableList<Ds3Annotation> annotations = ImmutableList.of(
                new Ds3Annotation("com.spectralogic.util.db.lang.References", null),
                new Ds3Annotation("com.spectralogic.util.bean.lang.Optional", null));

        assertFalse(isNullableElement(null, ImmutableList.of()));
        assertFalse(isNullableElement("", ImmutableList.of()));

        assertTrue(isNullableElement("MyType", annotations));
        assertTrue(isNullableElement("java.lang.Integer", annotations));
    }

    @Test
    public void isNullableElement_NoOptionalAnnotation_Test() {
        final ImmutableList<Ds3Annotation> annotations = ImmutableList.of(
                new Ds3Annotation("com.spectralogic.util.db.lang.References", null),
                new Ds3Annotation("com.spectralogic.util.db.lang.CascadeDelete", null));

        assertFalse(isNullableElement("MyType", annotations));
        assertTrue(isNullableElement("java.lang.Integer", annotations));
    }
}
