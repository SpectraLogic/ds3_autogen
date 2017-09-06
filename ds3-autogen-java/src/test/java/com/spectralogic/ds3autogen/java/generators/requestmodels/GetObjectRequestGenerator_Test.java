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

package com.spectralogic.ds3autogen.java.generators.requestmodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecEmptyImpl;
import com.spectralogic.ds3autogen.java.models.ConstructorParam;
import com.spectralogic.ds3autogen.java.models.QueryParam;
import com.spectralogic.ds3autogen.java.models.RequestConstructor;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.generators.requestmodels.GetObjectRequestGenerator.createDeprecatedConstructor;
import static com.spectralogic.ds3autogen.java.generators.requestmodels.GetObjectRequestGenerator.createChannelConstructor;
import static com.spectralogic.ds3autogen.java.generators.requestmodels.GetObjectRequestGenerator.createOutputStreamConstructor;
import static com.spectralogic.ds3autogen.java.test.helpers.RequestGeneratorTestHelper.createSimpleTestDs3Request;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestAmazonS3GetObject;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GetObjectRequestGenerator_Test {

    private final static GetObjectRequestGenerator generator = new GetObjectRequestGenerator();

    @Test
    public void toConstructorArgumentsList_Test() {
        final Ds3Request request = createSimpleTestDs3Request();

        final ImmutableList<Arguments> result = generator.toConstructorArgumentsList(request);
        assertThat(result.size(), is(5));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(1).getName(), is("ObjectName"));
        assertThat(result.get(2).getName(), is("JobId"));
        assertThat(result.get(3).getName(), is("Priority"));
        assertThat(result.get(4).getName(), is("NotificationEndPoint"));
    }

    @Test
    public void toConstructorList_Test() {
        final Ds3Request request = getRequestAmazonS3GetObject();

        final ImmutableList<RequestConstructor> result = generator.toConstructorList(request, "", new Ds3DocSpecEmptyImpl());
        assertThat(result.size(), is(3));

        //Deprecated Constructor
        final RequestConstructor constructor1 = result.get(0);
        assertThat(constructor1.isDeprecated(), is(true));
        assertThat(constructor1.getAdditionalLines().size(), is(0));

        final ImmutableList<ConstructorParam> constructorParams1 = constructor1.getParameters();
        final ImmutableList<String> expectedNames1 = ImmutableList.of("BucketName", "ObjectName", "Channel");

        assertThat(constructorParams1.size(), is(expectedNames1.size()));
        constructorParams1.forEach(param -> assertThat(expectedNames1, hasItem(param.getName())));

        final ImmutableList<Arguments> constructorAssignments1 = constructor1.getAssignments();
        assertThat(constructorAssignments1.size(), is(expectedNames1.size()));
        constructorAssignments1.forEach(assignment -> assertThat(expectedNames1, hasItem(assignment.getName())));

        final ImmutableList<QueryParam> queryParams1 = constructor1.getQueryParams();
        assertThat(queryParams1.size(), is(0));

        //Channel Constructor
        final RequestConstructor constructor2 = result.get(1);
        assertThat(constructor2.isDeprecated(), is(false));
        assertThat(constructor2.getAdditionalLines().size(), is(0));

        final ImmutableList<ConstructorParam> constructorParams2 = constructor2.getParameters();
        final ImmutableList<String> expectedNames2 = ImmutableList.of("BucketName", "ObjectName", "Job", "Offset", "Channel");
        assertThat(constructorParams2.size(), is(expectedNames2.size()));
        constructorParams2.forEach(param -> assertThat(expectedNames2, hasItem(param.getName())));

        final ImmutableList<Arguments> constructorAssignments2 = constructor2.getAssignments();
        assertThat(constructorAssignments2.size(), is(expectedNames2.size()));
        constructorAssignments2.forEach(assignment -> assertThat(expectedNames2, hasItem(assignment.getName())));

        final ImmutableList<QueryParam> queryParams2 = constructor2.getQueryParams();
        assertThat(queryParams2.size(), is(2));
        assertThat(queryParams2.get(0).getName(), is("Job"));
        assertThat(queryParams2.get(1).getName(), is("Offset"));

        //Channel Constructor
        final RequestConstructor constructor3 = result.get(2);
        assertThat(constructor3.isDeprecated(), is(false));
        assertThat(constructor3.getAdditionalLines().size(), is(1));
        assertThat(constructor3.getAdditionalLines().get(0), is("this.channel = Channels.newChannel(stream);"));

        final ImmutableList<ConstructorParam> constructorParams3 = constructor3.getParameters();
        final ImmutableList<String> expectedNames3 = ImmutableList.of("BucketName", "ObjectName", "Job", "Offset", "Stream");
        assertThat(constructorParams3.size(), is(expectedNames3.size()));
        constructorParams3.forEach(param -> assertThat(expectedNames3, hasItem(param.getName())));

        final ImmutableList<Arguments> constructorAssignments3 = constructor3.getAssignments();
        assertThat(constructorAssignments3.size(), is(4));
        assertThat(constructorAssignments3.get(0).getName(), is("BucketName"));
        assertThat(constructorAssignments3.get(1).getName(), is("ObjectName"));
        assertThat(constructorAssignments3.get(2).getName(), is("Job"));
        assertThat(constructorAssignments3.get(3).getName(), is("Offset"));

        final ImmutableList<QueryParam> queryParams3 = constructor3.getQueryParams();
        assertThat(queryParams3.size(), is(2));
        assertThat(queryParams3.get(0).getName(), is("Job"));
        assertThat(queryParams3.get(1).getName(), is("Offset"));
    }

    @Test
    public void createDeprecatedConstructor_Test() {
        final RequestConstructor result = createDeprecatedConstructor(
                ImmutableList.of(new Arguments("Type", "Arg1")),
                ImmutableList.of(new QueryParam("Type", "Arg2")),
                "",
                new Ds3DocSpecEmptyImpl());

        assertThat(result.isDeprecated(), is(true));
        assertThat(result.getAdditionalLines().size(), is(0));

        final ImmutableList<ConstructorParam> params = result.getParameters();
        assertThat(params.size(), is(2));
        assertThat(params.get(0).getName(), is("Arg1"));
        assertThat(params.get(1).getName(), is("Channel"));

        final ImmutableList<Arguments> assignments = result.getAssignments();
        assertThat(assignments.size(), is(2));
        assertThat(assignments.get(0).getName(), is("Arg1"));
        assertThat(assignments.get(1).getName(), is("Channel"));

        final ImmutableList<QueryParam> queryParams = result.getQueryParams();
        assertThat(queryParams.size(), is(1));
        assertThat(queryParams.get(0).getName(), is("Arg2"));
    }

    @Test
    public void createRegularConstructor_Test() {
        final RequestConstructor result = createChannelConstructor(
                ImmutableList.of(new Arguments("Type", "Arg1")),
                ImmutableList.of(new Arguments("Type", "Arg2")),
                ImmutableList.of(new QueryParam("Type", "Arg3")),
                "",
                new Ds3DocSpecEmptyImpl());

        assertThat(result.isDeprecated(), is(false));
        assertThat(result.getAdditionalLines().size(), is(0));

        final ImmutableList<ConstructorParam> params = result.getParameters();
        assertThat(params.size(), is(3));
        assertThat(params.get(0).getName(), is("Arg1"));
        assertThat(params.get(1).getName(), is("Arg2"));
        assertThat(params.get(2).getName(), is("Channel"));

        final ImmutableList<Arguments> assignments = result.getAssignments();
        assertThat(assignments.size(), is(3));
        assertThat(assignments.get(0).getName(), is("Arg1"));
        assertThat(assignments.get(1).getName(), is("Arg2"));
        assertThat(assignments.get(2).getName(), is("Channel"));

        final ImmutableList<QueryParam> queryParams = result.getQueryParams();
        assertThat(queryParams.size(), is(2));
        assertThat(queryParams.get(0).getName(), is("Arg3"));
        assertThat(queryParams.get(1).getName(), is("Arg2"));
    }

    @Test
    public void createOutputStreamConstructor_Test() {
        final RequestConstructor result = createOutputStreamConstructor(
                ImmutableList.of(new Arguments("Type", "Arg1")),
                ImmutableList.of(new Arguments("Type", "Arg2")),
                ImmutableList.of(new QueryParam("Type", "Arg3")),
                "",
                new Ds3DocSpecEmptyImpl());

        assertThat(result.isDeprecated(), is(false));
        assertThat(result.getAdditionalLines().size(), is(1));
        assertThat(result.getAdditionalLines().get(0), is("this.channel = Channels.newChannel(stream);"));

        final ImmutableList<ConstructorParam> constructorParams = result.getParameters();
        assertThat(constructorParams.size(), is(3));
        assertThat(constructorParams.get(0).getName(), is("Arg1"));
        assertThat(constructorParams.get(1).getName(), is("Arg2"));
        assertThat(constructorParams.get(2).getName(), is("Stream"));

        final ImmutableList<Arguments> constructorAssignments = result.getAssignments();
        assertThat(constructorAssignments.size(), is(2));
        assertThat(constructorAssignments.get(0).getName(), is("Arg1"));
        assertThat(constructorAssignments.get(1).getName(), is("Arg2"));

        final ImmutableList<QueryParam> queryParams = result.getQueryParams();
        assertThat(queryParams.size(), is(2));
        assertThat(queryParams.get(0).getName(), is("Arg3"));
        assertThat(queryParams.get(1).getName(), is("Arg2"));
    }
}
