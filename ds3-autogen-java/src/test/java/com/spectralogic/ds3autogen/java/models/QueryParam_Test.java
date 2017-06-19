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

package com.spectralogic.ds3autogen.java.models;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class QueryParam_Test {

    @Test
    public void toPutJavaCodeDifferentTypesTest() {
        final String expected = "this.updateQueryParam(\"name\", name);\n";

        final ImmutableList<QueryParam> qps = ImmutableList.of(
                new QueryParam("String", "Name"),
                new QueryParam("java.lang.String", "Name"),
                new QueryParam("int", "Name"),
                new QueryParam("java.lang.Integer", "Name"),
                new QueryParam("boolean", "Name"),
                new QueryParam("java.lang.UUID", "Name")
        );

        qps.forEach(qp -> assertThat(qp.toPutJavaCode(), is(expected)));
    }

    @Test
    public void toPutJavaCodeVoidTypeTest() {
        final String expected = "this.getQueryParams().put(\"name\", null);";

        final QueryParam qp = new QueryParam("void", "Name");

        assertThat(qp.toPutJavaCode(), is(expected));
    }
}
