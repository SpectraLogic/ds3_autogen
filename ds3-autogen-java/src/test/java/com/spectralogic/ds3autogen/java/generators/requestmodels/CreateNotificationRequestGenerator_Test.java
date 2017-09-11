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
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Param;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecEmptyImpl;
import com.spectralogic.ds3autogen.java.models.ConstructorParam;
import com.spectralogic.ds3autogen.java.models.QueryParam;
import com.spectralogic.ds3autogen.java.models.RequestConstructor;
import com.spectralogic.ds3autogen.java.models.Variable;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.generators.requestmodels.CreateNotificationRequestGenerator.toConstructorAssignmentList;
import static com.spectralogic.ds3autogen.java.test.helpers.RequestGeneratorTestHelper.createSimpleTestDs3Request;
import static com.spectralogic.ds3autogen.java.test.helpers.RequestGeneratorTestHelper.createTestDs3ParamList;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createDs3RequestTestData;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CreateNotificationRequestGenerator_Test {

    private final static CreateNotificationRequestGenerator generator = new CreateNotificationRequestGenerator();

    @Test
    public void getParentImport_Test() {
        assertThat(generator.getParentImport(null), is("com.spectralogic.ds3client.commands.interfaces.AbstractCreateNotificationRequest"));
    }

    @Test
    public void toRequiredArgumentsList_Test() {
        final Ds3Request request = createSimpleTestDs3Request();

        final ImmutableList<Arguments> result = generator.toRequiredArgumentsList(request);
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("Priority"));
    }

    @Test
    public void toClassVariableArguments_Test() {
        final Ds3Request request = createSimpleTestDs3Request();

        final ImmutableList<Variable> result = generator.toClassVariableArguments(request);
        assertThat(result.size(), is(5));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(1).getName(), is("ObjectName"));
        assertThat(result.get(2).getName(), is("JobId"));
        assertThat(result.get(3).getName(), is("Priority"));
        assertThat(result.get(4).getName(), is("RequestType"));
    }

    @Test
    public void toQueryParamsList_Test() {
        final Ds3Request request = createSimpleTestDs3Request();

        final ImmutableList<QueryParam> result = generator.toQueryParamsList(request);
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("Priority"));
    }

    @Test
    public void toConstructorAssignmentList_NullList_Test() {
        final ImmutableList<Arguments> result = toConstructorAssignmentList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toConstructorAssignmentList_EmptyList_Test() {
        final ImmutableList<Arguments> result = toConstructorAssignmentList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toConstructorAssignmentList_FullList_Test() {
        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("Type", "Arg1"),
                new Arguments("Type", "NotificationEndPoint"),
                new Arguments("Type", "Arg2"));

        final ImmutableList<Arguments> result = toConstructorAssignmentList(constructorArgs);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("Arg1"));
        assertThat(result.get(1).getName(), is("Arg2"));
    }

    @Test
    public void toConstructorList_Test() {
        final ImmutableList.Builder<Ds3Param> builder = ImmutableList.builder();
        builder.addAll(createTestDs3ParamList());
        builder.add(new Ds3Param("NotificationEndPoint", "String", false));

        final Ds3Request request = createDs3RequestTestData(true, null, builder.build());

        final ImmutableList<RequestConstructor> result = generator.toConstructorList(request, "", new Ds3DocSpecEmptyImpl());
        assertThat(result.size(), is(1));

        final RequestConstructor constructor = result.get(0);
        assertThat(constructor.getAdditionalLines().size(), is(0));
        assertThat(constructor.isDeprecated(), is(false));

        final ImmutableList<ConstructorParam> constructorParams = constructor.getParameters();
        final ImmutableList<String> expectedParams = ImmutableList.of("MaxUploadSize", "Name", "Priority", "NotificationEndPoint");
        assertThat(constructorParams.size(), is(expectedParams.size()));
        constructorParams.forEach(param -> assertThat(expectedParams, hasItem(param.getName())));

        final ImmutableList<Arguments> constructorAssignments = constructor.getAssignments();
        final ImmutableList<String> expectedAssignments = ImmutableList.of("MaxUploadSize", "Name", "Priority");
        assertThat(constructorAssignments.size(), is(expectedAssignments.size()));
        constructorAssignments.forEach(assignment -> assertThat(expectedAssignments, hasItem(assignment.getName())));

        final ImmutableList<QueryParam> queryParams = constructor.getQueryParams();
        final ImmutableList<String> expectedQueryParams = ImmutableList.of("IgnoreNamingConflicts", "MaxUploadSize", "Name", "Priority");
        assertThat(queryParams.size(), is(expectedQueryParams.size()));
        queryParams.forEach(param -> assertThat(expectedQueryParams, hasItem(param.getName())));
    }
}
