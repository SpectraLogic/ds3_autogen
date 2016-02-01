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
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.java.models.RequestConstructor;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.generators.requestmodels.GetObjectRequestGenerator.createDeprecatedConstructor;
import static com.spectralogic.ds3autogen.java.generators.requestmodels.GetObjectRequestGenerator.createRegularConstructor;
import static com.spectralogic.ds3autogen.java.test.helpers.RequestGeneratorTestHelper.createSimpleTestDs3Request;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestAmazonS3GetObject;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GetObjectRequestGenerator_Test {

    private final static GetObjectRequestGenerator generator = new GetObjectRequestGenerator();

    @Test
    public void toConstructorArgumentsList_Test() {
        final Ds3Request request = createSimpleTestDs3Request();

        final ImmutableList<Arguments> result = generator.toConstructorArgumentsList(request);
        assertThat(result.size(), is(6));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(1).getName(), is("ObjectName"));
        assertThat(result.get(2).getName(), is("JobId"));
        assertThat(result.get(3).getName(), is("Channel"));
        assertThat(result.get(4).getName(), is("Priority"));
        assertThat(result.get(5).getName(), is("NotificationEndPoint"));
    }

    @Test
    public void toConstructorList_Test() {
        final Ds3Request request = getRequestAmazonS3GetObject();

        final ImmutableList<RequestConstructor> result = generator.toConstructorList(request);
        assertThat(result.size(), is(2));

        //Deprecated Constructor
        final RequestConstructor constructor1 = result.get(0);
        assertThat(constructor1.isDeprecated(), is(true));
        assertThat(constructor1.getAdditionalLines().size(), is(0));

        final ImmutableList<Arguments> constructorParams1 = constructor1.getParameters();
        assertThat(constructorParams1.size(), is(3));
        assertThat(constructorParams1.get(0).getName(), is("BucketName"));
        assertThat(constructorParams1.get(1).getName(), is("ObjectName"));
        assertThat(constructorParams1.get(2).getName(), is("Channel"));

        final ImmutableList<Arguments> constructorAssignments1 = constructor1.getAssignments();
        assertThat(constructorAssignments1.size(), is(3));
        assertThat(constructorAssignments1.get(0).getName(), is("BucketName"));
        assertThat(constructorAssignments1.get(1).getName(), is("ObjectName"));
        assertThat(constructorAssignments1.get(2).getName(), is("Channel"));

        final ImmutableList<Arguments> queryParams1 = constructor1.getQueryParams();
        assertThat(queryParams1.size(), is(0));

        //Regular Constructor
        final RequestConstructor constructor2 = result.get(1);
        assertThat(constructor2.isDeprecated(), is(false));
        assertThat(constructor2.getAdditionalLines().size(), is(0));

        final ImmutableList<Arguments> constructorParams2 = constructor2.getParameters();
        assertThat(constructorParams2.size(), is(5));
        assertThat(constructorParams2.get(0).getName(), is("BucketName"));
        assertThat(constructorParams2.get(1).getName(), is("ObjectName"));
        assertThat(constructorParams2.get(2).getName(), is("Channel"));
        assertThat(constructorParams2.get(3).getName(), is("Job"));
        assertThat(constructorParams2.get(4).getName(), is("Offset"));

        final ImmutableList<Arguments> constructorAssignments2 = constructor2.getAssignments();
        assertThat(constructorAssignments2.size(), is(5));
        assertThat(constructorAssignments2.get(0).getName(), is("BucketName"));
        assertThat(constructorAssignments2.get(1).getName(), is("ObjectName"));
        assertThat(constructorAssignments2.get(2).getName(), is("Channel"));
        assertThat(constructorAssignments2.get(3).getName(), is("Job"));
        assertThat(constructorAssignments2.get(4).getName(), is("Offset"));

        final ImmutableList<Arguments> queryParams2 = constructor2.getQueryParams();
        assertThat(queryParams2.size(), is(2));
        assertThat(queryParams2.get(0).getName(), is("Job"));
        assertThat(queryParams2.get(1).getName(), is("Offset"));
    }

    @Test
    public void createDeprecatedConstructor_Test() {
        final RequestConstructor result = createDeprecatedConstructor(
                ImmutableList.of(new Arguments("Type", "Arg1")),
                ImmutableList.of(new Arguments("Type", "Arg2")));

        assertThat(result.isDeprecated(), is(true));
        assertThat(result.getAdditionalLines().size(), is(0));

        final ImmutableList<Arguments> params = result.getParameters();
        assertThat(params.size(), is(1));
        assertThat(params.get(0).getName(), is("Arg1"));

        final ImmutableList<Arguments> assignments = result.getAssignments();
        assertThat(assignments.size(), is(1));
        assertThat(assignments.get(0).getName(), is("Arg1"));

        final ImmutableList<Arguments> queryParams = result.getQueryParams();
        assertThat(queryParams.size(), is(1));
        assertThat(queryParams.get(0).getName(), is("Arg2"));
    }

    @Test
    public void createRegularConstructor_Test() {
        final RequestConstructor result = createRegularConstructor(
                ImmutableList.of(new Arguments("Type", "Arg1")),
                ImmutableList.of(new Arguments("Type", "Arg2")),
                ImmutableList.of(new Arguments("Type", "Arg3")));

        assertThat(result.isDeprecated(), is(false));
        assertThat(result.getAdditionalLines().size(), is(0));

        final ImmutableList<Arguments> params = result.getParameters();
        assertThat(params.size(), is(2));
        assertThat(params.get(0).getName(), is("Arg1"));
        assertThat(params.get(1).getName(), is("Arg2"));

        final ImmutableList<Arguments> assignments = result.getAssignments();
        assertThat(assignments.size(), is(2));
        assertThat(assignments.get(0).getName(), is("Arg1"));
        assertThat(assignments.get(1).getName(), is("Arg2"));

        final ImmutableList<Arguments> queryParams = result.getQueryParams();
        assertThat(queryParams.size(), is(2));
        assertThat(queryParams.get(0).getName(), is("Arg3"));
        assertThat(queryParams.get(1).getName(), is("Arg2"));
    }
}
