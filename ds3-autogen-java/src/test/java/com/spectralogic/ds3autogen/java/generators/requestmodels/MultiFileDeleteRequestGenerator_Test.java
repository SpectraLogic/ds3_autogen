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

package com.spectralogic.ds3autogen.java.generators.requestmodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.java.models.QueryParam;
import com.spectralogic.ds3autogen.java.models.RequestConstructor;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.generators.requestmodels.MultiFileDeleteRequestGenerator.createIterableConstructor;
import static com.spectralogic.ds3autogen.java.generators.requestmodels.MultiFileDeleteRequestGenerator.createObjectsConstructor;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestMultiFileDelete;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MultiFileDeleteRequestGenerator_Test {

    private final static MultiFileDeleteRequestGenerator generator = new MultiFileDeleteRequestGenerator();

    @Test
    public void toConstructorList_Test() {
        final Ds3Request request = getRequestMultiFileDelete();

        final ImmutableList<RequestConstructor> result = generator.toConstructorList(request);
        assertThat(result.size(), is(2));

        //Create Objects
        final RequestConstructor constructor1 = result.get(0);
        assertThat(constructor1.isDeprecated(), is(false));
        assertThat(constructor1.getAdditionalLines().size(), is(0));

        final ImmutableList<Arguments> constructorParams1 = constructor1.getParameters();
        assertThat(constructorParams1.size(), is(2));
        assertThat(constructorParams1.get(0).getName(), is("BucketName"));
        assertThat(constructorParams1.get(1).getName(), is("Objects"));

        final ImmutableList<Arguments> constructorAssignments1 = constructor1.getAssignments();
        assertThat(constructorAssignments1.size(), is(2));
        assertThat(constructorAssignments1.get(0).getName(), is("BucketName"));
        assertThat(constructorAssignments1.get(1).getName(), is("Objects"));

        final ImmutableList<QueryParam> queryParams1 = constructor1.getQueryParams();
        assertThat(queryParams1.size(), is(1));
        assertThat(queryParams1.get(0).getName(), is("Delete"));

        //Create Iterable
        final RequestConstructor constructor2 = result.get(1);
        assertThat(constructor2.isDeprecated(), is(false));
        assertThat(constructor2.getAdditionalLines().size(), is(1));
        assertThat(
                constructor2.getAdditionalLines().get(0),
                is("this.objects = contentsToString(objs);"));

        final ImmutableList<Arguments> constructorParams2 = constructor2.getParameters();
        assertThat(constructorParams2.size(), is(2));
        assertThat(constructorParams2.get(0).getName(), is("BucketName"));
        assertThat(constructorParams2.get(1).getName(), is("Objs"));

        final ImmutableList<Arguments> constructorAssignments2 = constructor2.getAssignments();
        assertThat(constructorAssignments2.size(), is(1));
        assertThat(constructorAssignments2.get(0).getName(), is("BucketName"));

        final ImmutableList<QueryParam> queryParams2 = constructor2.getQueryParams();
        assertThat(queryParams2.size(), is(1));
        assertThat(queryParams2.get(0).getName(), is("Delete"));
    }

    @Test
    public void createObjectsConstructor_Test() {
        final RequestConstructor result = createObjectsConstructor(
                ImmutableList.of(new Arguments("Type", "Arg1")),
                ImmutableList.of(new QueryParam("Type", "Arg2")));

        assertThat(result.isDeprecated(), is(false));
        assertThat(result.getAdditionalLines().size(), is(0));

        final ImmutableList<Arguments> params = result.getParameters();
        assertThat(params.size(), is(2));
        assertThat(params.get(0).getName(), is("Arg1"));
        assertThat(params.get(1).getName(), is("Objects"));

        final ImmutableList<Arguments> assignments = result.getAssignments();
        assertThat(assignments.size(), is(2));
        assertThat(assignments.get(0).getName(), is("Arg1"));
        assertThat(assignments.get(1).getName(), is("Objects"));

        final ImmutableList<QueryParam> queryParams = result.getQueryParams();
        assertThat(queryParams.size(), is(1));
        assertThat(queryParams.get(0).getName(), is("Arg2"));
    }

    @Test
    public void createIterableConstructor_Test() {
        final RequestConstructor result = createIterableConstructor(
                ImmutableList.of(new Arguments("Type", "Arg1")),
                ImmutableList.of(new QueryParam("Type", "Arg2")));

        assertThat(result.isDeprecated(), is(false));
        assertThat(result.getAdditionalLines().size(), is(1));
        assertThat(
                result.getAdditionalLines().get(0),
                is("this.objects = contentsToString(objs);"));

        final ImmutableList<Arguments> params = result.getParameters();
        assertThat(params.size(), is(2));
        assertThat(params.get(0).getName(), is("Arg1"));
        assertThat(params.get(1).getName(), is("Objs"));

        final ImmutableList<Arguments> assignments = result.getAssignments();
        assertThat(assignments.size(), is(1));
        assertThat(assignments.get(0).getName(), is("Arg1"));

        final ImmutableList<QueryParam> queryParams = result.getQueryParams();
        assertThat(queryParams.size(), is(1));
        assertThat(queryParams.get(0).getName(), is("Arg2"));
    }
}
