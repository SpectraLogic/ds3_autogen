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

package com.spectralogic.ds3autogen.c;

public class RequestHelper_Test {
/*

<#---------------------------------------------- S3 REQUESTS ------------------------------------------->
<RequestHandler Name="com.spectralogic.s3.server.handler.reqhandler.amazons3.DeleteBucketRequestHandler">
    <Documentation>
        Delete a bucket. This is an AWS request (see http://docs.aws.amazon.com/AmazonS3/latest/API/RESTBucketDELETE.html for AWS documentation).
    </Documentation>
    <RequestRequirements>Cannot include an S3 object specification</RequestRequirements>
    <RequestRequirements>Must be HTTP request type DELETE</RequestRequirements>
    <RequestRequirements>Must be an AWS-style request</RequestRequirements>
    <RequestRequirements>Must include an S3 bucket specification</RequestRequirements>
    <RequestRequirements>Query Parameters Required: [], Optional: []</RequestRequirements>
    <SampleResponses>
    <HttpRequest>
        DELETE 'test_bucket_name' with query parameters {} and headers {Internal-Request=1}.
    </HttpRequest>
    <HttpResponse>
        with headers {x-amz-request-id=97, RequestHandler-Version=1.416E43D944A06C824AD8BF5005FA1DC3}.
    </HttpResponse>
    <HttpResponseCode>204</HttpResponseCode>
    <HttpResponseType>null</HttpResponseType>
    <Test>
        com.spectralogic.s3.server.handler.reqhandler.amazons3.DeleteBucketRequestHandler_Test.testDeleteBucketReturns204WhenBucketExistsAndIsEmpty
    </Test>
    </SampleResponses>
</RequestHandler>

<RequestHandler Name="com.spectralogic.s3.server.handler.reqhandler.amazons3.DeleteObjectRequestHandler">
    <Documentation>
        Delete an S3 object in a bucket. This is an AWS request (see http://docs.aws.amazon.com/AmazonS3/latest/API/RESTObjectDELETE.html for AWS documentation).
    </Documentation>
    <RequestRequirements>Must be HTTP request type DELETE</RequestRequirements>
    <RequestRequirements>Must be an AWS-style request</RequestRequirements>
    <RequestRequirements>Must include an S3 bucket specification</RequestRequirements>
    <RequestRequirements>Must include an S3 object specification</RequestRequirements>
    <RequestRequirements>
        Query Parameters Required: [], Optional: [roll_back]
    </RequestRequirements>
    <SampleResponses>
        <HttpRequest>
            DELETE 'test_bucket_name/test_object_name/' with query parameters {} and headers {Internal-Request=1}.
        </HttpRequest>
        <HttpResponse>
            with headers {x-amz-request-id=98, RequestHandler-Version=1.46B3A42F88F14FF8E433CEF3CED4C70E}.
        </HttpResponse>
        <HttpResponseCode>204</HttpResponseCode>
        <HttpResponseType>null</HttpResponseType>
        <Test>
            com.spectralogic.s3.server.handler.reqhandler.amazons3.DeleteObjectRequestHandler_Test.testDeleteObjectReturns204WhenFolderNotEmpty
        </Test>
    </SampleResponses>
</RequestHandler>

<RequestHandler Name="com.spectralogic.s3.server.handler.reqhandler.amazons3.DeleteObjectsRequestHandler">
    <Documentation>
        Delete multiple S3 objects in a bucket. This is an AWS request (see http://docs.aws.amazon.com/AmazonS3/latest/API/multiobjectdeleteapi.html for AWS documentation).
    </Documentation>
    <RequestRequirements>Cannot include an S3 object specification</RequestRequirements>
    <RequestRequirements>Must be HTTP request type POST</RequestRequirements>
    <RequestRequirements>Must be an AWS-style request</RequestRequirements>
    <RequestRequirements>Must include an S3 bucket specification</RequestRequirements>
    <RequestRequirements>
        Query Parameters Required: [delete], Optional: [roll_back]
    </RequestRequirements>
    <SampleResponses>
        <HttpRequest>
            POST 'test_bucket_name' with query parameters {delete=} and headers {Internal-Request=1} and request payload {
                <Delete>
                    <Object>
                        <Key>object/</Key>
                    </Object>
                    <Object>
                        <Key>object/1</Key>
                    </Object>
                    <Object>
                        <Key>object/2</Key>
                    </Object>
                </Delete>}.
        </HttpRequest>
        <HttpResponse>
            <DeleteResult>
                <Deleted>
                    <Key>object/</Key>
                </Deleted>
                <Deleted>
                    <Key>object/2</Key>
                </Deleted>
                <Error>
                    <Code>ObjectNotFound</Code>
                    <Key>object/1</Key>
                    <Message>Object not found</Message>
                </Error>
            </DeleteResult> with headers {x-amz-request-id=104, Content-Type=text/xml, RequestHandler-Version=1.773E50A74C3DC9EB87D6CD924CA1B34A}.
        </HttpResponse>
        <HttpResponseCode>200</HttpResponseCode>
        <HttpResponseType>
            com.spectralogic.s3.server.domain.DeleteResultApiBean
        </HttpResponseType>
    </SampleResponses>
</RequestHandler>

<#---------------------------------------------- SpectraS3 REQUESTS --------------------------------------------->
<RequestHandler Name="com.spectralogic.s3.server.handler.reqhandler.spectrads3.bucket.DeleteBucketRequestHandler">
    <Documentation>Delete a bucket.</Documentation>
    <RequestRequirements>Must be REST action DELETE</RequestRequirements>
    <RequestRequirements>Must be REST domain BUCKET</RequestRequirements>
    <RequestRequirements>Must be a DS3-style request</RequestRequirements>
    <RequestRequirements>Query Parameters Required: [], Optional: [force]</RequestRequirements>
    <SampleResponses>
        <HttpRequest>
            DELETE '_rest_/bucket/test_bucket_name' with query parameters {FORCE=0} and headers {Internal-Request=1}.
        </HttpRequest>
        <SampleResponses>
        <HttpRequest>
            DELETE '_rest_/bucket/test_bucket_name' with query parameters {} and headers {Internal-Request=1}.
        </HttpRequest>
        <HttpResponse>
            with headers {x-amz-request-id=211, RequestHandler-Version=1.7E0B95E1E222BB82689E2AA8CD3FE344}.
        </HttpResponse>
        <HttpResponseCode>204</HttpResponseCode>
        <HttpResponseType>null</HttpResponseType>
        <Test>
            com.spectralogic.s3.server.handler.reqhandler.spectrads3.bucket.DeleteBucketRequestHandler_Test.testDeleteBucketReturns204WhenBucketExistsAndIsEmpty
        </Test>
    </SampleResponses>
</RequestHandler>
*/
}
