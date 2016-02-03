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
import com.spectralogic.ds3autogen.api.models.Ds3Param;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.java.models.RequestConstructor;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.generators.requestmodels.CreateObjectRequestGenerator.createChannelConstructor;
import static com.spectralogic.ds3autogen.java.generators.requestmodels.CreateObjectRequestGenerator.createDeprecatedConstructor;
import static com.spectralogic.ds3autogen.java.generators.requestmodels.CreateObjectRequestGenerator.createInputStreamConstructor;
import static com.spectralogic.ds3autogen.java.test.helpers.RequestGeneratorTestHelper.createSimpleTestDs3Request;
import static com.spectralogic.ds3autogen.java.test.helpers.RequestGeneratorTestHelper.createTestDs3ParamList;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestCreateObject;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createDs3RequestTestData;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CreateObjectRequestGenerator_Test {

    private final static CreateObjectRequestGenerator generator = new CreateObjectRequestGenerator();

    @Test
    public void toConstructorArgumentsList_Test() {
        final Ds3Request request = createSimpleTestDs3Request();

        final ImmutableList<Arguments> result = generator.toConstructorArgumentsList(request);
        assertThat(result.size(), is(6));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(1).getName(), is("ObjectName"));
        assertThat(result.get(2).getName(), is("JobId"));
        assertThat(result.get(3).getName(), is("Priority"));
        assertThat(result.get(4).getName(), is("NotificationEndPoint"));
        assertThat(result.get(5).getName(), is("Size"));
        assertThat(result.get(5).getType(), is("long"));
    }

    @Test
    public void toQueryParamsList_Test() {
        final ImmutableList<Ds3Param> params = createTestDs3ParamList();
        final Ds3Request request = createDs3RequestTestData(true, params, null);

        final ImmutableList<Arguments> result = generator.toQueryParamsList(request);
        assertThat(result.size(), is(4));
        assertThat(result.get(0).getName(), is("IgnoreNamingConflicts"));
        assertThat(result.get(1).getName(), is("MaxUploadSize"));
        assertThat(result.get(2).getName(), is("Name"));
        assertThat(result.get(3).getName(), is("Priority"));
    }

    @Test
    public void toConstructorList_Test() {
        final Ds3Request request = getRequestCreateObject();

        final ImmutableList<RequestConstructor> result = generator.toConstructorList(request);
        assertThat(result.size(), is(3));

        //Deprecated Constructor
        final RequestConstructor constructor1 = result.get(0);
        assertThat(constructor1.isDeprecated(), is(true));
        assertThat(constructor1.getAdditionalLines().size(), is(1));
        assertThat(
                constructor1.getAdditionalLines().get(0),
                is("this.stream = new SeekableByteChannelInputStream(channel);"));

        final ImmutableList<Arguments> constructorParams1 = constructor1.getParameters();
        assertThat(constructorParams1.size(), is(4));
        assertThat(constructorParams1.get(0).getName(), is("BucketName"));
        assertThat(constructorParams1.get(1).getName(), is("ObjectName"));
        assertThat(constructorParams1.get(2).getName(), is("Size"));
        assertThat(constructorParams1.get(3).getName(), is("Channel"));

        final ImmutableList<Arguments> constructorAssignments1 = constructor1.getAssignments();
        assertThat(constructorAssignments1.size(), is(4));
        assertThat(constructorAssignments1.get(0).getName(), is("BucketName"));
        assertThat(constructorAssignments1.get(1).getName(), is("ObjectName"));
        assertThat(constructorAssignments1.get(2).getName(), is("Size"));
        assertThat(constructorAssignments1.get(3).getName(), is("Channel"));

        final ImmutableList<Arguments> queryParams1 = constructor1.getQueryParams();
        assertThat(queryParams1.size(), is(0));

        //Channel Constructor
        final RequestConstructor constructor2 = result.get(1);
        assertThat(constructor2.isDeprecated(), is(false));
        assertThat(constructor2.getAdditionalLines().size(), is(1));
        assertThat(
                constructor2.getAdditionalLines().get(0),
                is("this.stream = new SeekableByteChannelInputStream(channel);"));

        final ImmutableList<Arguments> constructorParams2 = constructor2.getParameters();
        assertThat(constructorParams2.size(), is(6));
        assertThat(constructorParams2.get(0).getName(), is("BucketName"));
        assertThat(constructorParams2.get(1).getName(), is("ObjectName"));
        assertThat(constructorParams2.get(2).getName(), is("Size"));
        assertThat(constructorParams2.get(3).getName(), is("Job"));
        assertThat(constructorParams2.get(4).getName(), is("Offset"));
        assertThat(constructorParams2.get(5).getName(), is("Channel"));

        final ImmutableList<Arguments> constructorAssignments2 = constructor2.getAssignments();
        assertThat(constructorAssignments2.size(), is(6));
        assertThat(constructorAssignments2.get(0).getName(), is("BucketName"));
        assertThat(constructorAssignments2.get(1).getName(), is("ObjectName"));
        assertThat(constructorAssignments2.get(2).getName(), is("Size"));
        assertThat(constructorAssignments2.get(3).getName(), is("Job"));
        assertThat(constructorAssignments2.get(4).getName(), is("Offset"));
        assertThat(constructorAssignments2.get(5).getName(), is("Channel"));

        final ImmutableList<Arguments> queryParams2 = constructor2.getQueryParams();
        assertThat(queryParams2.size(), is(2));
        assertThat(queryParams2.get(0).getName(), is("Job"));
        assertThat(queryParams2.get(1).getName(), is("Offset"));

        //Stream Constructor
        final RequestConstructor constructor3 = result.get(2);
        assertThat(constructor3.isDeprecated(), is(false));
        assertThat(constructor3.getAdditionalLines().size(), is(0));

        final ImmutableList<Arguments> constructorParams3 = constructor3.getParameters();
        assertThat(constructorParams3.size(), is(6));
        assertThat(constructorParams3.get(0).getName(), is("BucketName"));
        assertThat(constructorParams3.get(1).getName(), is("ObjectName"));
        assertThat(constructorParams3.get(2).getName(), is("Size"));
        assertThat(constructorParams3.get(3).getName(), is("Job"));
        assertThat(constructorParams3.get(4).getName(), is("Offset"));
        assertThat(constructorParams3.get(5).getName(), is("Stream"));

        final ImmutableList<Arguments> constructorAssignments3 = constructor3.getAssignments();
        assertThat(constructorAssignments3.size(), is(6));
        assertThat(constructorAssignments3.get(0).getName(), is("BucketName"));
        assertThat(constructorAssignments3.get(1).getName(), is("ObjectName"));
        assertThat(constructorAssignments3.get(2).getName(), is("Size"));
        assertThat(constructorAssignments3.get(3).getName(), is("Job"));
        assertThat(constructorAssignments3.get(4).getName(), is("Offset"));
        assertThat(constructorAssignments3.get(5).getName(), is("Stream"));

        final ImmutableList<Arguments> queryParams3 = constructor3.getQueryParams();
        assertThat(queryParams3.size(), is(2));
        assertThat(queryParams3.get(0).getName(), is("Job"));
        assertThat(queryParams3.get(1).getName(), is("Offset"));
    }

    @Test
    public void createInputStreamConstructor_Test() {
        final RequestConstructor result = createInputStreamConstructor(
                ImmutableList.of(new Arguments("Type", "Arg1")),
                ImmutableList.of(new Arguments("Type", "Arg2")),
                ImmutableList.of(new Arguments("Type", "Arg3")));

        assertThat(result.isDeprecated(), is(false));
        assertThat(result.getAdditionalLines().size(), is(0));

        final ImmutableList<Arguments> params = result.getParameters();
        assertThat(params.size(), is(3));
        assertThat(params.get(0).getName(), is("Arg1"));
        assertThat(params.get(1).getName(), is("Arg2"));
        assertThat(params.get(2).getName(), is("Stream"));

        final ImmutableList<Arguments> assignments = result.getAssignments();
        assertThat(assignments.size(), is(3));
        assertThat(assignments.get(0).getName(), is("Arg1"));
        assertThat(assignments.get(1).getName(), is("Arg2"));
        assertThat(assignments.get(2).getName(), is("Stream"));

        final ImmutableList<Arguments> queryParams = result.getQueryParams();
        assertThat(queryParams.size(), is(1));
        assertThat(queryParams.get(0).getName(), is("Arg3"));
    }

    @Test
    public void createChannelConstructor_Test() {
        final RequestConstructor result = createChannelConstructor(
                ImmutableList.of(new Arguments("Type", "Arg1")),
                ImmutableList.of(new Arguments("Type", "Arg2")),
                ImmutableList.of(new Arguments("Type", "Arg3")));

        assertThat(result.isDeprecated(), is(false));
        assertThat(result.getAdditionalLines().size(), is(1));
        assertThat(
                result.getAdditionalLines().get(0),
                is("this.stream = new SeekableByteChannelInputStream(channel);"));

        final ImmutableList<Arguments> params = result.getParameters();
        assertThat(params.size(), is(3));
        assertThat(params.get(0).getName(), is("Arg1"));
        assertThat(params.get(1).getName(), is("Arg2"));
        assertThat(params.get(2).getName(), is("Channel"));

        final ImmutableList<Arguments> assignments = result.getAssignments();
        assertThat(assignments.size(), is(3));
        assertThat(assignments.get(0).getName(), is("Arg1"));
        assertThat(assignments.get(1).getName(), is("Arg2"));
        assertThat(assignments.get(2).getName(), is("Channel"));

        final ImmutableList<Arguments> queryParams = result.getQueryParams();
        assertThat(queryParams.size(), is(1));
        assertThat(queryParams.get(0).getName(), is("Arg3"));
    }

    @Test
    public void createDeprecatedConstructor_Test() {
        final RequestConstructor result = createDeprecatedConstructor(
                ImmutableList.of(new Arguments("Type", "Arg1")));

        assertThat(result.isDeprecated(), is(true));
        assertThat(result.getAdditionalLines().size(), is(1));
        assertThat(
                result.getAdditionalLines().get(0),
                is("this.stream = new SeekableByteChannelInputStream(channel);"));

        final ImmutableList<Arguments> params = result.getParameters();
        assertThat(params.size(), is(2));
        assertThat(params.get(0).getName(), is("Arg1"));
        assertThat(params.get(1).getName(), is("Channel"));

        final ImmutableList<Arguments> assignments = result.getAssignments();
        assertThat(assignments.size(), is(2));
        assertThat(assignments.get(0).getName(), is("Arg1"));
        assertThat(assignments.get(1).getName(), is("Channel"));

        final ImmutableList<Arguments> queryParams = result.getQueryParams();
        assertThat(queryParams.size(), is(0));
    }
}
