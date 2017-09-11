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
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Param;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.enums.*;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecEmptyImpl;
import com.spectralogic.ds3autogen.java.models.*;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.generators.requestmodels.BaseRequestGenerator.*;
import static com.spectralogic.ds3autogen.java.test.helpers.RequestGeneratorTestHelper.createSimpleTestDs3Request;
import static com.spectralogic.ds3autogen.java.test.helpers.RequestGeneratorTestHelper.createTestDs3ParamList;
import static com.spectralogic.ds3autogen.java.utils.CommonRequestGeneratorUtils.argsToQueryParams;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.*;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createDs3RequestTestData;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createEmptyDs3Request;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BaseRequestGenerator_Test {

    private final static BaseRequestGenerator generator = new BaseRequestGenerator();

    private static final ImmutableList<Arguments> SIMPLE_ARGS = ImmutableList.of(
            new Arguments("String", "StringArg"),
            new Arguments("UUID", "UuidArg"),
            new Arguments("int", "IntArg"));

    private static final ImmutableList<ConstructorParam> SIMPLE_PARAMS = ImmutableList.of(
            new SimpleConstructorParam("StringArg", "String"),
            new SimpleConstructorParam("UuidArg", "UUID"),
            new SimpleConstructorParam("IntArg", "int"));

    @Test
    public void isSpectraDs3_Test() {
        assertTrue(isSpectraDs3("com.spectralogic.ds3client.commands.spectrads3"));
        assertFalse(isSpectraDs3("com.spectralogic.ds3client.commands"));

        assertTrue(isSpectraDs3("com.spectralogic.ds3client.commands.spectrads3.notifications"));
        assertFalse(isSpectraDs3("com.spectralogic.ds3client.commands.notifications"));
    }

    @Test
    public void getSpectraDs3RequestPath_Test() {
        final Ds3Request deleteNotification = getRequestDeleteNotification();
        assertThat(getSpectraDs3RequestPath(deleteNotification), is("\"/_rest_/job_created_notification_registration/\" + this.getNotificationId().toString()"));

        final Ds3Request createNotification  = getRequestCreateNotification();
        assertThat(getSpectraDs3RequestPath(createNotification), is("\"/_rest_/job_created_notification_registration\""));

        final Ds3Request getNotification  = getRequestGetNotification();
        assertThat(getSpectraDs3RequestPath(getNotification), is("\"/_rest_/job_completed_notification_registration/\" + this.getNotificationId().toString()"));

        final Ds3Request verifyPhysicalPlacement = getRequestVerifyPhysicalPlacement();
        assertThat(getSpectraDs3RequestPath(verifyPhysicalPlacement), is("\"/_rest_/bucket/\" + this.bucketName"));

        final Ds3Request bulkRequest = getRequestBulkGet();
        assertThat(getSpectraDs3RequestPath(bulkRequest), is("\"/_rest_/bucket/\" + this.bucketName"));

        final Ds3Request spectraS3GetObject = getRequestSpectraS3GetObject();
        assertThat(getSpectraDs3RequestPath(spectraS3GetObject), is("\"/_rest_/object/\" + objectName"));

        //Non-Spectra Request
        final Ds3Request multiFileDelete  = getRequestMultiFileDelete();
        assertThat(getSpectraDs3RequestPath(multiFileDelete), is(""));
    }

    @Test
    public void getAmazonS3RequestPath_Test() {
        final Ds3Request multiFileDelete  = getRequestMultiFileDelete();
        assertThat(getAmazonS3RequestPath(multiFileDelete), is("\"/\" + this.bucketName"));

        final Ds3Request createObject = getRequestCreateObject();
        assertThat(getAmazonS3RequestPath(createObject), is("\"/\" + this.bucketName + \"/\" + this.objectName"));

        final Ds3Request amazonS3GetObject = getRequestAmazonS3GetObject();
        assertThat(getAmazonS3RequestPath(amazonS3GetObject), is("\"/\" + this.bucketName + \"/\" + this.objectName"));

        //Non-Amazon Request
        final Ds3Request deleteNotification = getRequestDeleteNotification();
        assertThat(getAmazonS3RequestPath(deleteNotification), is(""));
    }

    @Test
    public void getRequestPath_Empty_Test() {
        final String expectedPath = "\"/\"";
        final Ds3Request request = createDs3RequestTestData(
                "RequestName",
                Classification.amazons3);

        final String requestPath = getRequestPath(request);
        assertThat(requestPath, is(expectedPath));
    }

    @Test
    public void getRequestPath_Bucket_Test() {
        final String expectedPath = "\"/\" + this.bucketName";
        final Ds3Request request = createDs3RequestTestData(
                "RequestName",
                Classification.amazons3,
                Requirement.REQUIRED,
                null);

        final String requestPath = getRequestPath(request);
        assertThat(requestPath, is(expectedPath));
    }

    @Test
    public void getRequestPath_BucketAndObject_Test() {
        final String expectedPath = "\"/\" + this.bucketName + \"/\" + this.objectName";
        final Ds3Request request = createDs3RequestTestData(
                "RequestName",
                Classification.amazons3,
                Requirement.REQUIRED,
                Requirement.REQUIRED);

        final String requestPath = getRequestPath(request);
        assertThat(requestPath, is(expectedPath));
    }

    @Test
    public void getRequestPath_SpectraS3_Test() {
        final String expectedPath = "\"/_rest_/\"";
        final Ds3Request request = createDs3RequestTestData(
                "RequestName",
                Classification.spectrads3);

        final String requestPath = getRequestPath(request);
        assertThat(requestPath, is(expectedPath));
    }

    @Test
    public void getRequestPath_SpectraS3Resource_Test() {
        final String expectedPath = "\"/_rest_/active_job\"";
        final Ds3Request request = createDs3RequestTestData(
                "RequestName",
                Classification.spectrads3,
                Resource.ACTIVE_JOB,
                null,
                false);

        final String requestPath = getRequestPath(request);
        assertThat(requestPath, is(expectedPath));
    }

    @Test
    public void getRequestPath_SpectraS3ResourceWithBucket_Test() {
        final String expectedPath = "\"/_rest_/bucket/\" + this.bucketName";
        final Ds3Request request = createDs3RequestTestData(
                "RequestName",
                Classification.spectrads3,
                Resource.BUCKET,
                null,
                true);

        final String requestPath = getRequestPath(request);
        assertThat(requestPath, is(expectedPath));
    }

    @Test
    public void getRequestPath_SpectraS3ResourceWithJobChunkId_Test() {
        final String expectedPath = "\"/_rest_/job_chunk/\" + jobChunkId";
        final Ds3Request request = createDs3RequestTestData(
                "RequestName",
                Classification.spectrads3,
                Resource.JOB_CHUNK,
                ResourceType.NON_SINGLETON,
                true);

        final String requestPath = getRequestPath(request);
        assertThat(requestPath, is(expectedPath));
    }

    @Test
    public void getRequestPath_SpectraS3ResourceWithJobChunkId_NotInclude_Test() {
        final String expectedPath = "\"/_rest_/job_chunk\"";
        final Ds3Request request = createDs3RequestTestData(
                "RequestName",
                Classification.spectrads3,
                Resource.JOB_CHUNK,
                ResourceType.NON_SINGLETON,
                false);

        final String requestPath = getRequestPath(request);
        assertThat(requestPath, is(expectedPath));
    }

    @Test
    public void toArgumentsList_NullList_Test() {
        final ImmutableList<Arguments> result = toArgumentsList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toArgumentsList_EmptyList_Test() {
        final ImmutableList<Arguments> result = toArgumentsList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toArgumentsList_FullList_Test() {
        final ImmutableList<Ds3Param> params = createTestDs3ParamList();

        final ImmutableList<Arguments> result = toArgumentsList(params);
        assertThat(result.size(), is(4));
        assertThat(result.get(0).getName(), is("IgnoreNamingConflicts"));
        assertThat(result.get(1).getName(), is("MaxUploadSize"));
        assertThat(result.get(2).getName(), is("Name"));
        assertThat(result.get(3).getName(), is("Priority"));
    }

    @Test
    public void toOptionalArgumentsList_NullList_Test() {
        final ImmutableList<Arguments> result = toOptionalArgumentsList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toOptionalArgumentsList_EmptyList_Test() {
        final ImmutableList<Arguments> result = toOptionalArgumentsList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toOptionalArgumentsList_FullList_Test() {
        final ImmutableList<Ds3Param> params = createTestDs3ParamList();

        final ImmutableList<Arguments> result = toOptionalArgumentsList(params);
        assertThat(result.size(), is(4));
        assertThat(result.get(0).getName(), is("IgnoreNamingConflicts"));
        assertThat(result.get(1).getName(), is("MaxUploadSize"));
        assertThat(result.get(2).getName(), is("Name"));
        assertThat(result.get(3).getName(), is("Priority"));
    }

    @Test
    public void toRequiredArgumentsList_Test() {
        final ImmutableList<Ds3Param> params = createTestDs3ParamList();

        final Ds3Request request = createDs3RequestTestData(true, null, params);

        final ImmutableList<Arguments> result = generator.toRequiredArgumentsList(request);
        assertThat(result.size(), is(4));
        assertThat(result.get(0).getName(), is("IgnoreNamingConflicts"));
        assertThat(result.get(1).getName(), is("MaxUploadSize"));
        assertThat(result.get(2).getName(), is("Name"));
        assertThat(result.get(3).getName(), is("Priority"));
    }

    @Test
    public void toConstructorArgumentsList_Test() {
        final ImmutableList<Ds3Param> params = createTestDs3ParamList();

        final Ds3Request request = new Ds3Request(
                "RequestName",
                HttpVerb.DELETE,
                Classification.spectrads3,
                Requirement.REQUIRED,
                Requirement.REQUIRED,
                Action.BULK_DELETE,
                Resource.JOB,
                ResourceType.NON_SINGLETON,
                Operation.START_BULK_GET,
                true,
                null,
                null,
                params);

        final ImmutableList<Arguments> result = generator.toConstructorArgumentsList(request);
        assertThat(result.size(), is(6));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(1).getName(), is("ObjectName"));
        assertThat(result.get(2).getName(), is("JobId"));
        assertThat(result.get(3).getName(), is("MaxUploadSize"));
        assertThat(result.get(4).getName(), is("Name"));
        assertThat(result.get(5).getName(), is("Priority"));
    }

    @Test
    public void getImportsFromParamList_NullList_Test() {
        final ImmutableSet<String> result = getImportsFromParamList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getImportsFromParamList_EmptyList_Test() {
        final ImmutableSet<String> result = getImportsFromParamList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void getImportsFromParamList_FullList_Test() {
        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("BucketId", "java.util.UUID", false),
                new Ds3Param("MaxUploadSize", "long", false),
                new Ds3Param("Operation", "com.spectralogic.s3.server.request.rest.RestOperationType", false),
                new Ds3Param("Priority", "com.spectralogic.s3.common.dao.domain.ds3.BlobStoreTaskPriority", false),
                new Ds3Param("Name", "java.lang.String", true));

        final ImmutableSet<String> result = getImportsFromParamList(params);
        assertThat(result.size(), is(3));
        assertTrue(result.contains("java.util.UUID"));
        assertTrue(result.contains("com.spectralogic.ds3client.models.BlobStoreTaskPriority"));
        assertTrue(result.contains("com.google.common.net.UrlEscapers"));
    }

    @Test
    public void getParentImport_Test() {
        assertThat(generator.getParentImport(createEmptyDs3Request()), is("com.spectralogic.ds3client.commands.interfaces.AbstractRequest"));
        assertThat(generator.getParentImport(getObjectsDetailsRequest()), is("com.spectralogic.ds3client.commands.interfaces.AbstractPaginationRequest"));
    }

    @Test
    public void getParentClass_Test() {
        assertThat(generator.getParentClass(createEmptyDs3Request()), is("AbstractRequest"));
        assertThat(generator.getParentClass(getObjectsDetailsRequest()), is("AbstractPaginationRequest"));
    }

    @Test
    public void getAllImports_Test() {
        final Ds3Request request = createSimpleTestDs3Request();

        final ImmutableList<String> result = generator.getAllImports(
                request,
                "com.spectralogic.ds3client.commands.spectrads3",
                generator.toConstructorList(request, "", new Ds3DocSpecEmptyImpl()));

        assertThat(result)
                .hasSize(5)
                .contains(
                        "com.spectralogic.ds3client.commands.interfaces.AbstractRequest",
                        "com.spectralogic.ds3client.models.JobRequestType",
                        "com.spectralogic.ds3client.models.BlobStoreTaskPriority",
                        "java.util.UUID",
                        "com.google.common.net.UrlEscapers");
    }

    @Test
    public void toClassVariableArguments_Test() {
        final Ds3Request request = createSimpleTestDs3Request();

        final ImmutableList<Variable> result = generator.toClassVariableArguments(request);
        assertThat(result.size(), is(6));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(1).getName(), is("ObjectName"));
        assertThat(result.get(2).getName(), is("JobId"));
        assertThat(result.get(3).getName(), is("Priority"));
        assertThat(result.get(4).getName(), is("NotificationEndPoint"));
        assertThat(result.get(5).getName(), is("RequestType"));
    }

    @Test
    public void toQueryParamsList_Test() {
        final ImmutableList<Ds3Param> params = createTestDs3ParamList();
        final Ds3Request request = createDs3RequestTestData(true, null, params);

        final ImmutableList<QueryParam> result = generator.toQueryParamsList(request);
        assertThat(result.size(), is(4));
        assertThat(result.get(0).getName(), is("IgnoreNamingConflicts"));
        assertThat(result.get(1).getName(), is("MaxUploadSize"));
        assertThat(result.get(2).getName(), is("Name"));
        assertThat(result.get(3).getName(), is("Priority"));
    }

    @Test
    public void toConstructorList_Test() {
        final ImmutableList<Ds3Param> params = createTestDs3ParamList();
        final Ds3Request request = createDs3RequestTestData(true, null, params);

        final ImmutableList<RequestConstructor> result = generator.toConstructorList(request, "", new Ds3DocSpecEmptyImpl());
        assertThat(result.size(), is(1));

        final RequestConstructor constructor = result.get(0);
        assertThat(constructor.getAdditionalLines().size(), is(0));
        assertThat(constructor.isDeprecated(), is(false));

        final ImmutableList<ConstructorParam> constructorParams = constructor.getParameters();
        assertThat(constructorParams.size(), is(3));
        assertThat(constructorParams.get(0).getName(), is("MaxUploadSize"));
        assertThat(constructorParams.get(1).getName(), is("Name"));
        assertThat(constructorParams.get(2).getName(), is("Priority"));

        final ImmutableList<Arguments> constructorAssignments = constructor.getAssignments();
        assertThat(constructorAssignments.size(), is(3));
        assertThat(constructorAssignments.get(0).getName(), is("MaxUploadSize"));
        assertThat(constructorAssignments.get(1).getName(), is("Name"));
        assertThat(constructorAssignments.get(2).getName(), is("Priority"));

        final ImmutableList<QueryParam> queryParams = constructor.getQueryParams();
        assertThat(queryParams.size(), is(4));
        assertThat(queryParams.get(0).getName(), is("IgnoreNamingConflicts"));
        assertThat(queryParams.get(1).getName(), is("MaxUploadSize"));
        assertThat(queryParams.get(2).getName(), is("Name"));
        assertThat(queryParams.get(3).getName(), is("Priority"));
    }

    @Test
    public void updateDs3RequestParamTypes_Test() {
        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("Arg1", "String", false),
                new Ds3Param("Arg2", "java.util.UUID", false),
                new Ds3Param("BucketId", "java.util.UUID", false));
        final Ds3Request request = createDs3RequestTestData(false, params, params);

        final Ds3Request result = updateDs3RequestParamTypes(request);

        final ImmutableList<Ds3Param> requiredParams = result.getRequiredQueryParams();
        assertThat(requiredParams.size(), is(3));
        assertThat(requiredParams.get(0).getName(), is("Arg1"));
        assertThat(requiredParams.get(0).getType(), is("String"));
        assertThat(requiredParams.get(1).getName(), is("Arg2"));
        assertThat(requiredParams.get(1).getType(), is("java.util.UUID"));
        assertThat(requiredParams.get(2).getName(), is("BucketId"));
        assertThat(requiredParams.get(2).getType(), is("String"));

        final ImmutableList<Ds3Param> optionalParams = result.getOptionalQueryParams();
        assertThat(optionalParams.size(), is(3));
        assertThat(optionalParams.get(0).getName(), is("Arg1"));
        assertThat(optionalParams.get(0).getType(), is("String"));
        assertThat(optionalParams.get(1).getName(), is("Arg2"));
        assertThat(optionalParams.get(1).getType(), is("java.util.UUID"));
        assertThat(optionalParams.get(2).getName(), is("BucketId"));
        assertThat(optionalParams.get(2).getType(), is("String"));
    }

    @Test
    public void updateDs3ParamListTypes_NullList_Test() {
        final ImmutableList<Ds3Param> result = updateDs3ParamListTypes(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void updateDs3ParamListTypes_EmptyList_Test() {
        final ImmutableList<Ds3Param> result = updateDs3ParamListTypes(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void updateDs3ParamListTypes_FullList_Test() {
        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("Arg1", "String", false),
                new Ds3Param("Arg2", "java.util.UUID", false),
                new Ds3Param("BucketId", "java.util.UUID", false));

        final ImmutableList<Ds3Param> result = updateDs3ParamListTypes(params);
        assertThat(result.size(), is(3));
        assertThat(result.get(0).getName(), is("Arg1"));
        assertThat(result.get(0).getType(), is("String"));
        assertThat(result.get(1).getName(), is("Arg2"));
        assertThat(result.get(1).getType(), is("java.util.UUID"));
        assertThat(result.get(2).getName(), is("BucketId"));
        assertThat(result.get(2).getType(), is("String"));
    }

    @Test
    public void convertUuidConstructorToStringConstructor_Test() {
        final RequestConstructor constructor = new RequestConstructor(SIMPLE_PARAMS, SIMPLE_ARGS, argsToQueryParams(SIMPLE_ARGS), ImmutableList.of(), "");

        final RequestConstructor result = convertUuidConstructorToStringConstructor(constructor);

        assertThat(result.getParameters()).extracting(ConstructorParam::getType).doesNotContain("UUID");
        assertThat(result.getQueryParams()).extracting(QueryParam::getType).doesNotContain("UUID");
        assertThat(result.getAssignments()).extracting(Arguments::getType).doesNotContain("UUID");
    }

    @Test
    public void splitUuidConstructor_NoUuid_Test() {
        final ImmutableList<Arguments> args = ImmutableList.of(
                new Arguments("String", "StringArg"),
                new Arguments("int", "IntArg"));

        final ImmutableList<ConstructorParam> params = ImmutableList.of(
                new SimpleConstructorParam("StringArg", "String"),
                new SimpleConstructorParam("IntArg", "int"));

        final RequestConstructor constructor = new RequestConstructor(params, args, argsToQueryParams(args), ImmutableList.of(), "");

        final ImmutableList<RequestConstructor> result = splitUuidConstructor(constructor);
        assertThat(result).hasSize(1);
    }



    @Test
    public void splitUuidConstructor_Test() {

        final RequestConstructor constructor = new RequestConstructor(SIMPLE_PARAMS, SIMPLE_ARGS, argsToQueryParams(SIMPLE_ARGS), ImmutableList.of(), "");

        final ImmutableList<RequestConstructor> result = splitUuidConstructor(constructor);
        assertThat(result).hasSize(2);

        assertThat(result.get(0).getParameters()).extracting(ConstructorParam::getType).contains("UUID");
        assertThat(result.get(0).getQueryParams()).extracting(QueryParam::getType).contains("UUID");
        assertThat(result.get(0).getAssignments()).extracting(Arguments::getType).contains("UUID");

        assertThat(result.get(1).getParameters()).extracting(ConstructorParam::getType).doesNotContain("UUID");
        assertThat(result.get(1).getQueryParams()).extracting(QueryParam::getType).doesNotContain("UUID");
        assertThat(result.get(1).getAssignments()).extracting(Arguments::getType).doesNotContain("UUID");
    }

    @Test
    public void splitAllUuidConstructors_Test() {
        final RequestConstructor constructor = new RequestConstructor(SIMPLE_PARAMS, SIMPLE_ARGS, argsToQueryParams(SIMPLE_ARGS), ImmutableList.of(), "");

        final ImmutableList<RequestConstructor> result = splitAllUuidConstructors(ImmutableList.of(constructor));
        assertThat(result).hasSize(2);

        assertThat(result.get(0).getParameters()).extracting(ConstructorParam::getType).contains("UUID");
        assertThat(result.get(0).getQueryParams()).extracting(QueryParam::getType).contains("UUID");
        assertThat(result.get(0).getAssignments()).extracting(Arguments::getType).contains("UUID");

        assertThat(result.get(1).getParameters()).extracting(ConstructorParam::getType).doesNotContain("UUID");
        assertThat(result.get(1).getQueryParams()).extracting(QueryParam::getType).doesNotContain("UUID");
        assertThat(result.get(1).getAssignments()).extracting(Arguments::getType).doesNotContain("UUID");
    }

    @Test
    public void convertUuidClassVariable_Test() {
        final Variable uuidVar = convertUuidClassVariable(new Variable("UuidVar", "UUID", true));
        assertThat(uuidVar.getType()).isEqualTo("String");

        final Variable intVar = convertUuidClassVariable(new Variable("IntVar", "int", true));
        assertThat(intVar.getType()).isEqualTo("int");
    }

    @Test
    public void convertAllUuidClassVariables_NullList_Test() {
        final ImmutableList<Variable> result = convertAllUuidClassVariables(null);
        assertThat(result).hasSize(0);
    }

    @Test
    public void convertAllUuidClassVariables_EmptyList_Test() {
        final ImmutableList<Variable> result = convertAllUuidClassVariables(ImmutableList.of());
        assertThat(result).hasSize(0);
    }

    @Test
    public void convertAllUuidClassVariables_FullList_Test() {
        final ImmutableList<Variable> variables = ImmutableList.of(
                new Variable("Var", "String", true),
                new Variable("Var", "UUID", true),
                new Variable("Var", "UUID", false),
                new Variable("Var", "Integer", true));

        final ImmutableList<Variable> result = convertAllUuidClassVariables(variables);
        assertThat(result).hasSize(4)
                .extracting(Variable::getType).doesNotContain("UUID");
    }

    @Test
    public void splitUuidOptionalArgument_UUID_Test() {
        final ImmutableList<Arguments> result = splitUuidOptionalArgument(new Arguments("UUID", "Arg"));
        assertThat(result).hasSize(2)
                .extracting(Arguments::getType).containsExactly("UUID", "String");
    }

    @Test
    public void splitUuidOptionalArgument_Test() {
        final ImmutableList<Arguments> result = splitUuidOptionalArgument(new Arguments("Integer", "Arg"));
        assertThat(result).hasSize(1)
                .extracting(Arguments::getType).containsExactly("Integer");
    }

    @Test
    public void splitAllUuidOptionalArguments_NullList_Test() {
        final ImmutableList<Arguments> result = splitAllUuidOptionalArguments(null);
        assertThat(result).hasSize(0);
    }

    @Test
    public void splitAllUuidOptionalArguments_EmptyList_Test() {
        final ImmutableList<Arguments> result = splitAllUuidOptionalArguments(ImmutableList.of());
        assertThat(result).hasSize(0);
    }

    @Test
    public void splitAllUuidOptionalArguments_FullList_Test() {
        final ImmutableList<Arguments> variables = ImmutableList.of(
                new Arguments("UUID", "Var1"),
                new Arguments("Integer", "Var2"));

        final ImmutableList<Arguments> result = splitAllUuidOptionalArguments(variables);
        assertThat(result).hasSize(3);
        assertThat(result).extracting(Arguments::getName).containsExactly("Var1", "Var1", "Var2");
        assertThat(result).extracting(Arguments::getType).containsExactly("UUID", "String", "Integer");
    }

    @Test
    public void resourceArgToString_Test() {
        assertThat(resourceArgToString(new Arguments("UUID", "Arg"))).isEqualTo("arg");
        assertThat(resourceArgToString(new Arguments("String", "Arg"))).isEqualTo("arg");
        assertThat(resourceArgToString(new Arguments("Integer", "Arg"))).isEqualTo("String.valueOf(arg)");
    }

    @Test
    public void toWithConstructor_UUID_Test() {
        final String expected =
                "    public MyRequest withMyId(final UUID myId) {\n" +
                        "        this.myId = myId.toString();\n" +
                        "        this.updateQueryParam(\"my_id\", myId);\n" +
                        "        return this;\n" +
                        "    }\n";

        final Arguments idArg = new Arguments("UUID", "MyId");
        final String result = generator.toWithConstructor(idArg, "MyRequest", new Ds3DocSpecEmptyImpl());
        assertThat(result, is(expected));
    }

    @Test
    public void toWithConstructor_Boolean_Test() {
        final String expectedResultBoolean =
                "    public RequestName withArgName(final boolean argName) {\n" +
                        "        this.argName = argName;\n" +
                        "        this.updateQueryParam(\"arg_name\", argName);\n" +
                        "        return this;\n" +
                        "    }\n";
        final Arguments booleanArgument = new Arguments("boolean", "ArgName");
        final String booleanResult = generator.toWithConstructor(booleanArgument, "RequestName", new Ds3DocSpecEmptyImpl());
        assertThat(booleanResult, is(expectedResultBoolean));
    }

    @Test
    public void toWithConstructor_Test() {
        final String expectedResult =
                "    public RequestName withArgName(final ArgType argName) {\n" +
                        "        this.argName = argName;\n" +
                        "        this.updateQueryParam(\"arg_name\", argName);\n" +
                        "        return this;\n" +
                        "    }\n";
        final Arguments argument = new Arguments("ArgType", "ArgName");
        final String result = generator.toWithConstructor(argument, "RequestName", new Ds3DocSpecEmptyImpl());
        assertThat(result, is(expectedResult));
    }

    @Test
    public void modifyQueryParamType_NullList_Test() {
        final ImmutableList<QueryParam> result = modifyQueryParamType(null, "CurType", "NewType");
        assertThat(result.size(), is(0));
    }

    @Test
    public void modifyQueryParamType_EmptyList_Test() {
        final ImmutableList<QueryParam> result = modifyQueryParamType(ImmutableList.of(), "CurType", "NewType");
        assertThat(result.size(), is(0));
    }

    @Test
    public void modifyQueryParamType_FullList_Test() {
        final ImmutableList<QueryParam> params = ImmutableList.of(new QueryParam("CurType", "Name"));

        final ImmutableList<QueryParam> result = modifyQueryParamType(params, "CurType", "NewType");
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("Name"));
        assertThat(result.get(0).getType(), is("NewType"));
    }

    @Test
    public void toWithConstructorList_Test() {
        final String expected =
                "    public TestRequest withArgOne(final TypeOne argOne) {\n" +
                "        this.argOne = argOne;\n" +
                "        this.updateQueryParam(\"arg_one\", argOne);\n" +
                "        return this;\n" +
                "    }\n";

        final String voidExpected =
                "    public TestRequest withVoidArg(final boolean voidArg) {\n" +
                "        this.voidArg = voidArg;\n" +
                "        if (this.voidArg) {\n" +
                "            this.getQueryParams().put(\"void_arg\", null);\n" +
                "        } else {\n" +
                "            this.getQueryParams().remove(\"void_arg\");\n" +
                "        }\n" +
                "        return this;\n" +
                "    }\n";

        final ImmutableList<Arguments> optionalParams = ImmutableList.of(
                new Arguments("TypeOne", "ArgOne"),
                new Arguments("void", "VoidArg"));

        final ImmutableList<String> result = generator.toWithConstructorList(optionalParams, "TestRequest", new Ds3DocSpecEmptyImpl());
        assertThat(result.size(), is(2));
        assertThat(result.get(0), is(expected));
        assertThat(result.get(1), is(voidExpected));
    }

    private static final RequestConstructor EMPTY_REQUEST_CONSTRUCTOR = new RequestConstructor(
            ImmutableList.of(), ImmutableList.of(), ImmutableList.of(), ImmutableList.of(), "");

    private static final RequestConstructor REQUEST_CONSTRUCTOR_WITH_STREAM = new RequestConstructor(
            ImmutableList.of(new NonnullConstructorParam("Stream", "InputStream")),
            ImmutableList.of(),
            ImmutableList.of(),
            ImmutableList.of(new NotNullPrecondition("Stream")),
            "");

    @Test
    public void preconditionImportsNotIncludedTest() {
        final ImmutableList<String> result = preconditionImports(ImmutableList.of(EMPTY_REQUEST_CONSTRUCTOR));
        assertThat(result).hasSize(0);
    }

    @Test
    public void preconditionImportsIncludedTest() {
        final ImmutableList<RequestConstructor> constructors = ImmutableList.of(
                EMPTY_REQUEST_CONSTRUCTOR,
                REQUEST_CONSTRUCTOR_WITH_STREAM);

        final ImmutableList<String> result = preconditionImports(constructors);
        assertThat(result).hasSize(1)
                .contains("com.google.common.base.Preconditions");
    }

    @Test
    public void annotationImportsNotIncludedTest() {
        final ImmutableList<String> result = annotationImports(ImmutableList.of(EMPTY_REQUEST_CONSTRUCTOR));
        assertThat(result).hasSize(0);
    }

    @Test
    public void annotationImportsIncludedTest() {
        final ImmutableList<RequestConstructor> constructors = ImmutableList.of(
                EMPTY_REQUEST_CONSTRUCTOR,
                REQUEST_CONSTRUCTOR_WITH_STREAM);

        final ImmutableList<String> result = annotationImports(constructors);
        assertThat(result).hasSize(1).contains("javax.annotation.Nonnull");
    }

    @Test
    public void commonImportsTest() {
        final Ds3Request request = getRequestCreateObject();

        final ImmutableList<String> expected = ImmutableList.of(
                "com.spectralogic.ds3client.commands.interfaces.AbstractRequest",
                "java.util.UUID",
                "com.google.common.net.UrlEscapers",
                "com.google.common.base.Preconditions",
                "javax.annotation.Nonnull");

        final ImmutableList<String> result = generator.commonImports(request, ImmutableList.of(REQUEST_CONSTRUCTOR_WITH_STREAM));
        assertThat(result).hasSize(expected.size())
                .containsAll(expected);
    }

    @Test
    public void modifyConstructorParamTypesTest() {
        final ImmutableList <ConstructorParam> expected = ImmutableList.of(
                new SimpleConstructorParam("SimpleParam", "String"),
                new SimpleConstructorParam("SimpleUuidParam", "String"),
                new NonnullConstructorParam("NonnullParam", "int"),
                new NonnullConstructorParam("NonnullUuidParam", "String")
        );

        final ImmutableList <ConstructorParam> constructorParams = ImmutableList.of(
                new SimpleConstructorParam("SimpleParam", "String"),
                new SimpleConstructorParam("SimpleUuidParam", "UUID"),
                new NonnullConstructorParam("NonnullParam", "int"),
                new NonnullConstructorParam("NonnullUuidParam", "UUID")
        );

        final ImmutableList<ConstructorParam> result = modifyConstructorParamTypes(constructorParams, "UUID", "String");

        assertThat(result).hasSize(expected.size());

        for (int i = 0; i < expected.size(); i++) {
            assertThat(result.get(i).getName()).isEqualTo(expected.get(i).getName());
            assertThat(result.get(i).getType()).isEqualTo(expected.get(i).getType());
            assertThat(result.get(i).getAnnotation()).isEqualTo(expected.get(i).getAnnotation());
            assertThat(result.get(i).toJavaCode()).isEqualTo(expected.get(i).toJavaCode());
        }
    }
}
