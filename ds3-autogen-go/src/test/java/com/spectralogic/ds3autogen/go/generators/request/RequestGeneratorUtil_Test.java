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
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Param;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.enums.*;
import com.spectralogic.ds3autogen.go.models.request.SimpleVariable;
import com.spectralogic.ds3autogen.go.models.request.Variable;
import com.spectralogic.ds3autogen.go.models.request.WithConstructor;
import org.junit.Test;

import static com.spectralogic.ds3autogen.go.generators.request.RequestGeneratorUtilKt.*;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.*;
import static com.spectralogic.ds3autogen.utils.Helper.uncapFirst;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class RequestGeneratorUtil_Test {

    @Test
    public void removeVoidParams_NullList_Test() {
        final ImmutableList<Ds3Param> result = removeVoidDs3Params(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeVoidParams_EmptyList_Test() {
        final ImmutableList<Ds3Param> result = removeVoidDs3Params(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeVoidParams_FullList_Test() {
        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("Param1", "Type1", false),
                new Ds3Param("Param2", "void", false),
                new Ds3Param("Param3", "int", false),
                new Ds3Param("Param4", "VOID", false)
        );
        final ImmutableList<Ds3Param> result = removeVoidDs3Params(params);
        assertThat(result.size(), is(2));

        result.forEach(param -> assertThat(param.getType(), not("void")));
    }

    @Test
    public void removeVoidAndOperationDs3Params_NullList_Test() {
        final ImmutableList<Ds3Param> result = removeVoidAndOperationDs3Params(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeVoidAndOperationDs3Params_EmptyList_Test() {
        final ImmutableList<Ds3Param> result = removeVoidAndOperationDs3Params(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeVoidAndOperationDs3Params_FullList_Test() {
        final ImmutableList<Ds3Param> expected = ImmutableList.of(
                new Ds3Param("Param1", "Type1", false),
                new Ds3Param("Param3", "int", false)
        );

        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("Param1", "Type1", false),
                new Ds3Param("Param2", "void", false),
                new Ds3Param("Param3", "int", false),
                new Ds3Param("Param4", "VOID", false),
                new Ds3Param("Operation", "RestOperationType", false)
        );

        final ImmutableList<Ds3Param> result = removeVoidAndOperationDs3Params(params);
        assertThat(result.size(), is(expected.size()));
        expected.forEach(param -> assertThat(result, hasItem(param)));
    }

    @Test
    public void toGoArgumentsList_NullList_Test() {
        final ImmutableList<Arguments> result = toGoArgumentsList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toGoArgumentsList_EmptyList_Test() {
        final ImmutableList<Arguments> result = toGoArgumentsList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toGoArgumentsList_FullList_Test() {
        final ImmutableList<String> expectedTypes = ImmutableList.of("", "*int", "float64", "string");

        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("Param1", "void", false),
                new Ds3Param("Param2", "java.lang.Integer", true),
                new Ds3Param("Param3", "double", false),
                new Ds3Param("Param4", "java.util.UUID", false)
        );

        final ImmutableList<Arguments> result = toGoArgumentsList(params);
        assertThat(result.size(), is(4));

        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i).getType(), is(expectedTypes.get(i)));
        }
    }

    @Test
    public void toGoArgument_Test() {
        final ImmutableList<String> expectedTypes = ImmutableList.of("", "*int", "float64", "string");

        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("Param1", "void", false),
                new Ds3Param("Param2", "java.lang.Integer", true),
                new Ds3Param("Param3", "double", false),
                new Ds3Param("Param4", "java.util.UUID", false)
        );

        for (int i = 0; i < params.size(); i++) {
            final Arguments result = toGoArgument(params.get(i));
            assertThat(result.getName(), is(uncapFirst(params.get(i).getName())));
            assertThat(result.getType(), is(expectedTypes.get(i)));
        }
    }

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

    @Test (expected = IllegalArgumentException.class)
    public void getHttpVerb_Exception_Test() {
        getHttpVerb(null);
    }

    @Test
    public void getHttpVerb_Test() {
        assertThat(getHttpVerb(HttpVerb.DELETE), is(HttpVerb.DELETE));
        assertThat(getHttpVerb(HttpVerb.GET), is(HttpVerb.GET));
        assertThat(getHttpVerb(HttpVerb.HEAD), is(HttpVerb.HEAD));
        assertThat(getHttpVerb(HttpVerb.POST), is(HttpVerb.POST));
        assertThat(getHttpVerb(HttpVerb.PUT), is(HttpVerb.PUT));
    }

    @Test
    public void toQueryParamVarList_NullList_Test() {
        final ImmutableList<Variable> result = toQueryParamVarList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toQueryParamVarList_EmptyList_Test() {
        final ImmutableList<Variable> result = toQueryParamVarList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toQueryParamVarList_FullList_Test() {
        final ImmutableList<Variable> expected = ImmutableList.of(
                new Variable("param_one", "strconv.Itoa(paramOne)"),
                new Variable("param_two", "strconv.FormatFloat(paramTwo, 'f', -1, 64)"),
                new Variable("param_three", "paramThree"),
                new Variable("param_four", "\"\"")
        );

        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("ParamOne", "int", false),
                new Ds3Param("ParamTwo", "float64", false),
                new Ds3Param("ParamThree", "string", false),
                new Ds3Param("ParamFour", "void", false),
                new Ds3Param("Operation", "RestOperationType", false)
        );

        final ImmutableList<Variable> result = toQueryParamVarList(params);
        assertThat(result.size(), is(expected.size()));

        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i), is(expected.get(i)));
        }
    }

    @Test
    public void toQueryParamVar_Test() {
        final ImmutableList<Variable> expected = ImmutableList.of(
                new Variable("param_one", "strconv.Itoa(paramOne)"),
                new Variable("param_two", "strconv.FormatFloat(paramTwo, 'f', -1, 64)"),
                new Variable("param_three", "paramThree"),
                new Variable("param_four", "\"\"")
        );

        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("ParamOne", "int", false),
                new Ds3Param("ParamTwo", "float64", false),
                new Ds3Param("ParamThree", "string", false),
                new Ds3Param("ParamFour", "void", false)
        );

        assertThat(expected.size(), is(params.size()));
        for (int i = 0; i < params.size(); i++) {
            final Variable result = toQueryParamVar(params.get(i));
            assertThat(result, is(expected.get(i)));
        }
    }

    @Test
    public void goVarToString_Test() {
        assertThat(goVarToString("Arg", ""), is("\"\""));
        assertThat(goVarToString("Arg", "int"), is("strconv.Itoa(Arg)"));
        assertThat(goVarToString("Arg", "*int"), is("strconv.Itoa(*Arg)"));
        assertThat(goVarToString("Arg", "bool"), is("strconv.FormatBool(Arg)"));
        assertThat(goVarToString("Arg", "*bool"), is("strconv.FormatBool(*Arg)"));
        assertThat(goVarToString("Arg", "int64"), is("strconv.FormatInt(Arg, 10)"));
        assertThat(goVarToString("Arg", "*int64"), is("strconv.FormatInt(*Arg, 10)"));
        assertThat(goVarToString("Arg", "float64"), is("strconv.FormatFloat(Arg, 'f', -1, 64)"));
        assertThat(goVarToString("Arg", "*float64"), is("strconv.FormatFloat(*Arg, 'f', -1, 64)"));
        assertThat(goVarToString("Arg", "string"), is("Arg"));
        assertThat(goVarToString("Arg", "CustomType"), is("Arg.String()"));
    }

    @Test
    public void toSimpleVariables_NullList_Test() {
        final ImmutableList<SimpleVariable> result = toSimpleVariables(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toSimpleVariables_EmptyList_Test() {
        final ImmutableList<SimpleVariable> result = toSimpleVariables(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toSimpleVariables_FullList_Test() {
        final ImmutableList<SimpleVariable> expected = ImmutableList.of(
                new SimpleVariable("paramOne"),
                new SimpleVariable("paramTwo"),
                new SimpleVariable("paramThree")
        );

        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("ParamOne", "string", false),
                new Ds3Param("ParamTwo", "string", true),
                new Ds3Param("ParamThree", "string", false)
        );

        final ImmutableList<SimpleVariable> result = toSimpleVariables(params);
        assertThat(result.size(), is(3));

        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i), is(expected.get(i)));
        }
    }

    @Test
    public void toRequestPath_Test() {
        //Spectra requests
        assertThat(toRequestPath(getRequestDeleteNotification()),
                is("\"/_rest_/job_created_notification_registration/\" + deleteJobCreatedNotificationRegistrationRequestHandler.notificationId"));
        assertThat(toRequestPath(getRequestCreateNotification()),
                is("\"/_rest_/job_created_notification_registration\""));
        assertThat(toRequestPath(getRequestGetNotification()),
                is("\"/_rest_/job_completed_notification_registration/\" + getJobCompletedNotificationRegistrationRequestHandler.notificationId"));
        assertThat(toRequestPath(getRequestVerifyPhysicalPlacement()),
                is("\"/_rest_/bucket/\" + verifyPhysicalPlacementForObjectsRequestHandler.bucketName"));
        assertThat(toRequestPath(getRequestBulkGet()),
                is("\"/_rest_/bucket/\" + createGetJobRequestHandler.bucketName"));
        assertThat(toRequestPath(getRequestBulkPut()),
                is("\"/_rest_/bucket/\" + createPutJobRequestHandler.bucketName"));
        assertThat(toRequestPath(getRequestSpectraS3GetObject()),
                is("\"/_rest_/object/\" + getObjectRequestHandler.objectName"));
        assertThat(toRequestPath(getRequestGetJob()),
                is("\"/_rest_/job/\" + getJobRequestHandler.jobId"));

        //Amazon requests
        assertThat(toRequestPath(getRequestMultiFileDelete()),
                is("\"/\" + deleteObjectsRequestHandler.bucketName"));
        assertThat(toRequestPath(getRequestCreateObject()),
                is("\"/\" + createObjectRequestHandler.bucketName + \"/\" + createObjectRequestHandler.objectName"));
        assertThat(toRequestPath(getRequestAmazonS3GetObject()),
                is("\"/\" + getObjectRequestHandler.bucketName + \"/\" + getObjectRequestHandler.objectName"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void toRequestPath_Exception_Test() {
        final Ds3Request request = new Ds3Request(
                "Test",
                HttpVerb.GET,
                Classification.spectrainternal,
                Requirement.NOT_ALLOWED,
                Requirement.NOT_ALLOWED,
                Action.BULK_DELETE,
                Resource.ACTIVE_JOB,
                ResourceType.NON_SINGLETON,
                Operation.ALLOCATE,
                false,
                ImmutableList.of(),
                ImmutableList.of(),
                ImmutableList.of());

        toRequestPath(request);
    }

    @Test
    public void getAmazonS3RequestPath_Test() {
        //Amazon requests
        assertThat(getAmazonS3RequestPath(getRequestMultiFileDelete()),
                is("\"/\" + deleteObjectsRequestHandler.bucketName"));
        assertThat(getAmazonS3RequestPath(getRequestCreateObject()),
                is("\"/\" + createObjectRequestHandler.bucketName + \"/\" + createObjectRequestHandler.objectName"));
        assertThat(getAmazonS3RequestPath(getRequestAmazonS3GetObject()),
                is("\"/\" + getObjectRequestHandler.bucketName + \"/\" + getObjectRequestHandler.objectName"));

        //Spectra requests
        assertThat(getAmazonS3RequestPath(getRequestDeleteNotification()), is(""));
        assertThat(getAmazonS3RequestPath(getRequestCreateNotification()), is(""));
        assertThat(getAmazonS3RequestPath(getRequestGetNotification()), is(""));
        assertThat(getAmazonS3RequestPath(getRequestVerifyPhysicalPlacement()), is(""));
        assertThat(getAmazonS3RequestPath(getRequestBulkGet()), is(""));
        assertThat(getAmazonS3RequestPath(getRequestBulkPut()), is(""));
        assertThat(getAmazonS3RequestPath(getRequestSpectraS3GetObject()), is(""));
        assertThat(getAmazonS3RequestPath(getRequestGetJob()), is(""));
    }

    @Test
    public void getSpectraDs3RequestPath_Test() {
        //Spectra requests
        assertThat(getSpectraDs3RequestPath(getRequestDeleteNotification()),
                is("\"/_rest_/job_created_notification_registration/\" + deleteJobCreatedNotificationRegistrationRequestHandler.notificationId"));
        assertThat(getSpectraDs3RequestPath(getRequestCreateNotification()),
                is("\"/_rest_/job_created_notification_registration\""));
        assertThat(getSpectraDs3RequestPath(getRequestGetNotification()),
                is("\"/_rest_/job_completed_notification_registration/\" + getJobCompletedNotificationRegistrationRequestHandler.notificationId"));
        assertThat(getSpectraDs3RequestPath(getRequestVerifyPhysicalPlacement()),
                is("\"/_rest_/bucket/\" + verifyPhysicalPlacementForObjectsRequestHandler.bucketName"));
        assertThat(getSpectraDs3RequestPath(getRequestBulkGet()),
                is("\"/_rest_/bucket/\" + createGetJobRequestHandler.bucketName"));
        assertThat(getSpectraDs3RequestPath(getRequestBulkPut()),
                is("\"/_rest_/bucket/\" + createPutJobRequestHandler.bucketName"));
        assertThat(getSpectraDs3RequestPath(getRequestSpectraS3GetObject()),
                is("\"/_rest_/object/\" + getObjectRequestHandler.objectName"));
        assertThat(getSpectraDs3RequestPath(getRequestGetJob()),
                is("\"/_rest_/job/\" + getJobRequestHandler.jobId"));

        //Amazon requests
        assertThat(getSpectraDs3RequestPath(getRequestMultiFileDelete()), is(""));
        assertThat(getSpectraDs3RequestPath(getRequestCreateObject()), is(""));
        assertThat(getSpectraDs3RequestPath(getRequestAmazonS3GetObject()), is(""));
    }

    @Test
    public void toWithConstructors_NullList_Test() {
        final ImmutableList<WithConstructor> result = toWithConstructors(null, false);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toWithConstructors_EmptyList_Test() {
        final ImmutableList<WithConstructor> result = toWithConstructors(ImmutableList.of(), false);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toWithConstructors_NonNullableParams_Test() {
        final ImmutableList<WithConstructor> expected = ImmutableList.of(
                new WithConstructor("ParamOne", "Type1", "param_one", "paramOne.String()"),
                new WithConstructor("ParamThree", "int", "param_three", "strconv.Itoa(paramThree)")
        );

        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("ParamOne", "Type1", false),
                new Ds3Param("ParamTwo", "void", false),
                new Ds3Param("ParamThree", "int", false),
                new Ds3Param("ParamFour", "VOID", false)
        );

        final ImmutableList<WithConstructor> result = toWithConstructors(params, false);
        assertThat(result.size(), is(expected.size()));

        expected.forEach(expectedConst -> assertThat(result, hasItem(expectedConst)));
    }

    @Test
    public void toWithConstructors_NullableParams_Test() {
        final ImmutableList<WithConstructor> expected = ImmutableList.of(
                new WithConstructor("ParamOne", "*Type1", "param_one", "paramOne.String()"),
                new WithConstructor("ParamThree", "*int", "param_three", "strconv.Itoa(*paramThree)")
        );

        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("ParamOne", "Type1", true),
                new Ds3Param("ParamTwo", "void", true),
                new Ds3Param("ParamThree", "int", true),
                new Ds3Param("ParamFour", "VOID", true)
        );

        final ImmutableList<WithConstructor> result = toWithConstructors(params, true);
        assertThat(result.size(), is(expected.size()));

        expected.forEach(expectedConst -> assertThat(result, hasItem(expectedConst)));
    }
}
