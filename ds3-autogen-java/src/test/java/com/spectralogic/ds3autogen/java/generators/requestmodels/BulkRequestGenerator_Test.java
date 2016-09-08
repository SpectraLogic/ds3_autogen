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
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Param;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.java.models.QueryParam;
import com.spectralogic.ds3autogen.java.models.RequestConstructor;
import com.spectralogic.ds3autogen.java.models.Variable;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.generators.requestmodels.BulkRequestGenerator.*;
import static com.spectralogic.ds3autogen.java.test.helpers.RequestGeneratorTestHelper.createSimpleTestDs3Request;
import static com.spectralogic.ds3autogen.java.test.helpers.RequestGeneratorTestHelper.createTestDs3ParamList;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createDs3RequestTestData;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BulkRequestGenerator_Test {

    private final static BulkRequestGenerator generator = new BulkRequestGenerator();

    @Test
    public void isBulkRequestArg_Test() {
        assertTrue(isBulkRequestArg("Priority"));
        assertTrue(isBulkRequestArg("WriteOptimization"));
        assertTrue(isBulkRequestArg("BucketName"));
        assertFalse(isBulkRequestArg("ChunkClientProcessingOrderGuarantee"));
    }

    @Test
    public void toClassVariableArguments_Test() {
        final Ds3Request request = createSimpleTestDs3Request();

        final ImmutableList<Variable> result = generator.toClassVariableArguments(request);
        assertThat(result.size(), is(4));
        assertThat(result.get(0).getName(), is("ObjectName"));
        assertThat(result.get(1).getName(), is("JobId"));
        assertThat(result.get(2).getName(), is("NotificationEndPoint"));
        assertThat(result.get(3).getName(), is("RequestType"));
    }

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
        assertThat(result.get(5).getName(), is("Objects"));
        assertThat(result.get(5).getType(), is("List<Ds3Object>"));
    }

    @Test
    public void getParentImport_Test() {
        assertThat(generator.getParentImport(null), is("com.spectralogic.ds3client.commands.interfaces.BulkRequest"));
    }

    @Test
    public void toConstructorList_Test() {
        final ImmutableList<Ds3Param> params = createTestDs3ParamList();
        final Ds3Request request = createDs3RequestTestData(true, null, params);

        final ImmutableList<RequestConstructor> result = generator.toConstructorList(request);
        assertThat(result.size(), is(1));

        final RequestConstructor constructor = result.get(0);
        assertThat(constructor.getAdditionalLines().size(), is(0));
        assertThat(constructor.isDeprecated(), is(false));

        final ImmutableList<Arguments> constructorParams = constructor.getParameters();
        assertThat(constructorParams.size(), is(4));
        assertThat(constructorParams.get(0).getName(), is("MaxUploadSize"));
        assertThat(constructorParams.get(1).getName(), is("Name"));
        assertThat(constructorParams.get(2).getName(), is("Priority"));
        assertThat(constructorParams.get(3).getName(), is("Objects"));

        final ImmutableList<Arguments> constructorAssignments = constructor.getAssignments();
        assertThat(constructorAssignments.size(), is(2));
        assertThat(constructorAssignments.get(0).getName(), is("MaxUploadSize"));
        assertThat(constructorAssignments.get(1).getName(), is("Name"));

        final ImmutableList<QueryParam> queryParams = constructor.getQueryParams();
        assertThat(queryParams.size(), is(4));
        assertThat(queryParams.get(0).getName(), is("IgnoreNamingConflicts"));
        assertThat(queryParams.get(1).getName(), is("MaxUploadSize"));
        assertThat(queryParams.get(2).getName(), is("Name"));
        assertThat(queryParams.get(3).getName(), is("Priority"));
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
        final ImmutableList<Arguments> arguments = ImmutableList.of(
                new Arguments("type", "Priority"),
                new Arguments("type", "Name1"),
                new Arguments("type", "WriteOptimization"),
                new Arguments("type", "BucketName"),
                new Arguments("type", "Name2"),
                new Arguments("type", "Objects"));

        final ImmutableList<Arguments> result = toConstructorAssignmentList(arguments);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("Name1"));
        assertThat(result.get(1).getName(), is("Name2"));
    }

    @Test
    public void toWithConstructor_PriorityParam_Test() {
        final String expectedResult =
                "    @Override\n" +
                        "    public CreatePutJobRequestHandler withPriority(final Priority priority) {\n" +
                        "        super.withPriority(priority);\n" +
                        "        return this;\n" +
                        "    }\n";
        final Arguments arg = new Arguments("Priority", "Priority");
        final String result = generator.toWithConstructor(arg, "CreatePutJobRequestHandler");
        assertThat(result, is(expectedResult));
    }

    @Test
    public void toWithConstructor_MaxUploadSizeParam_Test() {
        final String expectedResult =
                "    public CreatePutJobRequestHandler withMaxUploadSize(final long maxUploadSize) {\n" +
                        "        if (maxUploadSize > MIN_UPLOAD_SIZE_IN_BYTES) {\n" +
                        "            this.getQueryParams().put(\"max_upload_size\", Long.toString(maxUploadSize));\n" +
                        "        } else {\n" +
                        "            this.getQueryParams().put(\"max_upload_size\", MAX_UPLOAD_SIZE_IN_BYTES);\n" +
                        "        }\n" +
                        "        return this;\n" +
                        "    }\n";
        final Arguments arg = new Arguments("long", "MaxUploadSize");
        final String result = generator.toWithConstructor(arg, "CreatePutJobRequestHandler");
        assertThat(result, is(expectedResult));
    }

    @Test
    public void toWithConstructor_VoidParam_Test() {
        final String expectedResult =
                "    public GetJobsRequestHandler withFullDetails(final boolean fullDetails) {\n" +
                        "        this.fullDetails = fullDetails;\n" +
                        "        if (this.fullDetails) {\n" +
                        "            this.getQueryParams().put(\"full_details\", null);\n" +
                        "        } else {\n" +
                        "            this.getQueryParams().remove(\"full_details\");\n" +
                        "        }\n" +
                        "        return this;\n" +
                        "    }\n";
        final Arguments arg = new Arguments("void", "FullDetails");
        final String result = generator.toWithConstructor(arg, "GetJobsRequestHandler");
        assertThat(result, is(expectedResult));
    }

    @Test
    public void toWithConstructor_Test() {
        final String expectedResult =
                "    public GetBucketRequestHandler withDelimiter(final String delimiter) {\n" +
                        "        this.delimiter = delimiter;\n" +
                        "        this.updateQueryParam(\"delimiter\", delimiter);\n" +
                        "        return this;\n" +
                        "    }\n";
        final Arguments arg = new Arguments("String", "Delimiter");
        final String result = generator.toWithConstructor(arg, "GetBucketRequestHandler");
        assertThat(result, is(expectedResult));
    }
}
