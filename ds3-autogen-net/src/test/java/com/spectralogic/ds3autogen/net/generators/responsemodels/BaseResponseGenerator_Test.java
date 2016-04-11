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

package com.spectralogic.ds3autogen.net.generators.responsemodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import org.junit.Test;

import static com.spectralogic.ds3autogen.net.generators.responsemodels.BaseResponseGenerator.toArgType;
import static com.spectralogic.ds3autogen.net.generators.responsemodels.BaseResponseGenerator.toArgument;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BaseResponseGenerator_Test {

    private static final BaseResponseGenerator generator = new BaseResponseGenerator();

    @Test
    public void toArgType_Test() {
        assertThat(toArgType("int", null), is("int"));
        assertThat(toArgType("long", ""), is("long"));
        assertThat(toArgType("com.spectralogic.s3.common.dao.domain.ds3.CompletedJob", ""), is("CompletedJob"));
        assertThat(toArgType("array", "MyType"), is("IEnumerable<MyType>"));
        assertThat(toArgType("array", "java.lang.String"), is("IEnumerable<string>"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void toArgType_Error_Test() {
        toArgType("int", "long");
    }

    @Test
    public void toArgument_Test() {
        final Arguments simpleResult = toArgument(new Ds3Element("com.test.Name", "com.test.SimpleType", null, false));
        assertThat(simpleResult.getName(), is("Name"));
        assertThat(simpleResult.getType(), is("SimpleType"));

        final Arguments componentResult = toArgument(new Ds3Element("com.test.Name", "array", "com.test.ComponentType", false));
        assertThat(componentResult.getName(), is("Name"));
        assertThat(componentResult.getType(), is("IEnumerable<ComponentType>"));
    }

    @Test
    public void toArgumentsList_NullList_Test() {
        final ImmutableList<Arguments> result = generator.toArgumentsList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toArgumentsList_EmptyList_Test() {
        final ImmutableList<Arguments> result = generator.toArgumentsList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toArgumentsList_FullList_Test() {
        final ImmutableList<Ds3Element> elements = ImmutableList.of(
                new Ds3Element("com.test.SimpleArg", "com.test.SimpleType", null, false),
                new Ds3Element("com.test.ComponentArg", "array", "com.test.ComponentType", false),
                new Ds3Element("com.test.IntArg", "Integer", null, false));

        final ImmutableList<Arguments> result = generator.toArgumentsList(elements);
        assertThat(result.size(), is(3));
        assertThat(result.get(0).getName(), is("SimpleArg"));
        assertThat(result.get(0).getType(), is("SimpleType"));
        assertThat(result.get(1).getName(), is("ComponentArg"));
        assertThat(result.get(1).getType(), is("IEnumerable<ComponentType>"));
        assertThat(result.get(2).getName(), is("IntArg"));
        assertThat(result.get(2).getType(), is("int"));
    }

    @Test
    public void typeToArgumentsList_NullType_Test() {
        final ImmutableList<Arguments> result = generator.typeToArgumentsList(null);
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("ResponsePayload"));
        assertThat(result.get(0).getType(), is("String"));
    }

    @Test
    public void typeToArgumentsList_Test() {
        final ImmutableList<Ds3Element> elements = ImmutableList.of(
                new Ds3Element("com.test.SimpleArg", "com.test.SimpleType", null, false));

        final Ds3Type type = new Ds3Type("TestType", elements);
        final ImmutableList<Arguments> result = generator.typeToArgumentsList(type);
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("SimpleArg"));
    }
}
