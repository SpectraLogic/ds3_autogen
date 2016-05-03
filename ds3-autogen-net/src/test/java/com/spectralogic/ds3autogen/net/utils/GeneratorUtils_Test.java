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

package com.spectralogic.ds3autogen.net.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.Ds3Param;
import org.junit.Test;

import static com.spectralogic.ds3autogen.net.utils.GeneratorUtils.*;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GeneratorUtils_Test {

    @Test
    public void toArgument_Test() {
        final Ds3Param simpleParam = new Ds3Param("ArgName", "SimpleType", false);
        final Arguments simpleArg = toArgument(simpleParam);
        assertThat(simpleArg.getName(), is("ArgName"));
        assertThat(simpleArg.getType(), is("SimpleType"));

        final Ds3Param pathParam = new Ds3Param("ArgName", "com.spectralogic.test.TypeWithPath", false);
        final Arguments pathArg = toArgument(pathParam);
        assertThat(pathArg.getName(), is("ArgName"));
        assertThat(pathArg.getType(), is("TypeWithPath"));
    }

    @Test
    public void getArgsFromParamList_NullList_Test() {
        final ImmutableList<Arguments> result = getArgsFromParamList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getArgsFromParamList_EmptyList_Test() {
        final ImmutableList<Arguments> result = getArgsFromParamList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void getArgsFromParamList_FullList_Test() {
        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("SimpleArg", "SimpleType", false),
                new Ds3Param("ArgWithPath", "com.test.TypeWithPath", false),
                new Ds3Param("Operation", "com.spectralogic.s3.server.request.rest.RestOperationType", false));

        final ImmutableList<Arguments> result = getArgsFromParamList(params);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("SimpleArg"));
        assertThat(result.get(0).getType(), is("SimpleType"));
        assertThat(result.get(1).getName(), is("ArgWithPath"));
        assertThat(result.get(1).getType(), is("TypeWithPath"));
    }

    @Test
    public void getRequiredArgs_Test() {
        final ImmutableList<Arguments> result = getRequiredArgs(getRequestMultiFileDelete());
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(1).getName(), is("Delete"));
    }

    @Test
    public void getSpectraDs3RequestPath_Test() {
        //Spectra requests
        assertThat(getSpectraDs3RequestPath(getRequestDeleteNotification()), is("\"/_rest_/job_created_notification_registration/\" + NotificationId.ToString()"));
        assertThat(getSpectraDs3RequestPath(getRequestCreateNotification()), is("\"/_rest_/job_created_notification_registration/\""));
        assertThat(getSpectraDs3RequestPath(getRequestGetNotification()), is("\"/_rest_/job_completed_notification_registration/\" + NotificationId.ToString()"));
        assertThat(getSpectraDs3RequestPath(getRequestVerifyPhysicalPlacement()), is("\"/_rest_/bucket/\" + BucketName"));
        assertThat(getSpectraDs3RequestPath(getRequestBulkGet()), is("\"/_rest_/bucket/\" + BucketName"));
        assertThat(getSpectraDs3RequestPath(getRequestBulkPut()), is("\"/_rest_/bucket/\" + BucketName"));
        assertThat(getSpectraDs3RequestPath(getRequestSpectraS3GetObject()), is("\"/_rest_/object/\" + ObjectName"));
        assertThat(getSpectraDs3RequestPath(getRequestGetJob()), is("\"/_rest_/job/\" + JobId.ToString()"));

        //Amazon requests
        assertThat(getSpectraDs3RequestPath(getRequestMultiFileDelete()), is(""));
        assertThat(getSpectraDs3RequestPath(getRequestCreateObject()), is(""));
        assertThat(getSpectraDs3RequestPath(getRequestAmazonS3GetObject()), is(""));
    }

    @Test
    public void getAmazonS3RequestPath_Test() {
        //Amazon requests
        assertThat(getAmazonS3RequestPath(getRequestMultiFileDelete()), is("\"/\" + BucketName"));
        assertThat(getAmazonS3RequestPath(getRequestCreateObject()), is("\"/\" + BucketName + \"/\" + ObjectName"));
        assertThat(getAmazonS3RequestPath(getRequestAmazonS3GetObject()), is("\"/\" + BucketName + \"/\" + ObjectName"));

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
    public void toRequestPath_Test() {
        //Spectra requests
        assertThat(toRequestPath(getRequestDeleteNotification()), is("\"/_rest_/job_created_notification_registration/\" + NotificationId.ToString()"));
        assertThat(toRequestPath(getRequestCreateNotification()), is("\"/_rest_/job_created_notification_registration/\""));
        assertThat(toRequestPath(getRequestGetNotification()), is("\"/_rest_/job_completed_notification_registration/\" + NotificationId.ToString()"));
        assertThat(toRequestPath(getRequestVerifyPhysicalPlacement()), is("\"/_rest_/bucket/\" + BucketName"));
        assertThat(toRequestPath(getRequestBulkGet()), is("\"/_rest_/bucket/\" + BucketName"));
        assertThat(toRequestPath(getRequestBulkPut()), is("\"/_rest_/bucket/\" + BucketName"));
        assertThat(toRequestPath(getRequestSpectraS3GetObject()), is("\"/_rest_/object/\" + ObjectName"));
        assertThat(toRequestPath(getRequestGetJob()), is("\"/_rest_/job/\" + JobId.ToString()"));

        //Amazon requests
        assertThat(toRequestPath(getRequestMultiFileDelete()), is("\"/\" + BucketName"));
        assertThat(toRequestPath(getRequestCreateObject()), is("\"/\" + BucketName + \"/\" + ObjectName"));
        assertThat(toRequestPath(getRequestAmazonS3GetObject()), is("\"/\" + BucketName + \"/\" + ObjectName"));
    }

    @Test
    public void toModelParserName_Test() {
        assertThat(toModelParserName(null), is(""));
        assertThat(toModelParserName(""), is(""));
        assertThat(toModelParserName("TestModel"), is("ParseTestModel"));
        assertThat(toModelParserName("com.test.TestModel"), is("ParseTestModel"));
    }

    @Test
    public void getNetType_WithComponentType_Test() {
        assertThat(getNetType("array", "MyType"), is("IEnumerable<MyType>"));
        assertThat(getNetType("array", "Void"), is("IEnumerable<bool>"));
        assertThat(getNetType("array", "Boolean"), is("IEnumerable<bool>"));
        assertThat(getNetType("array", "Integer"), is("IEnumerable<int>"));
        assertThat(getNetType("array", "String"), is("IEnumerable<string>"));
        assertThat(getNetType("array", "UUID"), is("IEnumerable<Guid>"));
        assertThat(getNetType("array", "ChecksumType"), is("IEnumerable<ChecksumType.Type>"));
    }

    @Test
    public void getNetType_Test() {
        assertThat(getNetType("MyType", null), is("MyType"));
        assertThat(getNetType("MyType", ""), is("MyType"));

        assertThat(getNetType("Void", null), is("bool"));
        assertThat(getNetType("Boolean", null), is("bool"));
        assertThat(getNetType("Integer", null), is("int"));
        assertThat(getNetType("String", null), is("string"));
        assertThat(getNetType("UUID", null), is("Guid"));
        assertThat(getNetType("ChecksumType", null), is("ChecksumType.Type"));
    }

    @Test
    public void hasResponseHandlerAndParser_Test() {
        assertTrue(hasResponseHandlerAndParser(getRequestAmazonS3GetObject()));
        assertFalse(hasResponseHandlerAndParser(getRequestSpectraS3GetObject()));
    }
}
