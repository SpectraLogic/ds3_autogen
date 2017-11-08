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

package com.spectralogic.ds3autogen.go.generators.request;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import org.junit.Test;

import static com.spectralogic.ds3autogen.go.generators.request.RequestGeneratorUtilKt.toFunctionInput;
import static com.spectralogic.ds3autogen.go.generators.request.RequestGeneratorUtilKt.usesStrconv;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class RequestGeneratorUtil_Test {

    @Test
    public void toFunctionInput_EmptyList_Test() {
        final String result = toFunctionInput(ImmutableList.of());
        assertThat(result, is(""));
    }

    @Test
    public void toFunctionInput_FullList_Test() {
        final String expected = "bucketName string, objectName string, a int, b float64";

        final ImmutableList<Arguments> args = ImmutableList.of(
                new Arguments("float64", "b"),
                new Arguments("int", "a"),
                new Arguments("string", "ObjectName"),
                new Arguments("string", "BucketName")
        );

        final String result = toFunctionInput(args);
        assertThat(result, is(expected));
    }

    @Test
    public void usesStrconv_Test() {
        assertTrue(usesStrconv("Boolean"));
        assertTrue(usesStrconv("java.lang.Boolean"));
        assertTrue(usesStrconv("java.lang.Integer"));
        assertTrue(usesStrconv("int"));
        assertTrue(usesStrconv("java.lang.Double"));
        assertTrue(usesStrconv("double"));
        assertTrue(usesStrconv("long"));
        assertTrue(usesStrconv("java.lang.Double"));

        assertFalse(usesStrconv("java.util.UUID"));
        assertFalse(usesStrconv("String"));
        assertFalse(usesStrconv("java.lang.String"));
        assertFalse(usesStrconv("com.test.TestType"));
        assertFalse(usesStrconv("void"));
    }
}
