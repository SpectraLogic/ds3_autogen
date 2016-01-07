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

package com.spectralogic.ds3autogen.converters;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.*;
import org.junit.Test;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.ResponseTypeConverter.*;
import static com.spectralogic.ds3autogen.utils.test.utils.ResponseTypeConverterHelper.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class ResponseTypeConverter_Test {

    @Test
    public void getResponseTypesToUpdateFromResponseTypes_NullList_Test() {
        final ImmutableSet<String> result = getResponseTypesToUpdateFromResponseTypes(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getResponseTypesToUpdateFromResponseTypes_EmptyList_Test() {
        final ImmutableSet<String> result = getResponseTypesToUpdateFromResponseTypes(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void getResponseTypesToUpdateFromResponseTypes_FullList_Test() {
        final ImmutableList<Ds3ResponseType> responseTypes = createPopulatedDs3ResponseTypeList("");

        final ImmutableSet<String> result = getResponseTypesToUpdateFromResponseTypes(responseTypes);

        assertThat(result.size(), is(2));
        assertTrue(result.contains("SimpleComponentType"));
        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl"));
    }

    @Test
    public void getResponseComponentTypesFromResponseCodes_NullList_Test() {
        final ImmutableSet<String> result = getResponseComponentTypesFromResponseCodes(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getResponseComponentTypesFromResponseCodes_EmptyList_Test() {
        final ImmutableSet<String> result = getResponseComponentTypesFromResponseCodes(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void getResponseComponentTypesFromResponseCodes_FullList_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = createPopulatedDs3ResponseCodeList("_v1", "_v2");
        final ImmutableSet<String> result = getResponseComponentTypesFromResponseCodes(responseCodes);

        assertThat(result.size(), is(4));

        assertTrue(result.contains("SimpleComponentType_v1"));
        assertTrue(result.contains("SimpleComponentType_v2"));
        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl_v1"));
        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl_v2"));
    }

    @Test
    public void getResponseComponentTypesFromRequests_NullList_Test() {
        final ImmutableSet<String> result = getResponseComponentTypesFromRequests(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getResponseComponentTypesFromRequests_EmptyList_Test() {
        final ImmutableSet<String> result = getResponseComponentTypesFromRequests(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void getResponseComponentTypesFromRequests_FullList_Test() {
        final ImmutableList<Ds3Request> requests = createPopulatedDs3RequestList("_v1", "_v2", "_v3", "_v4");
        final ImmutableSet<String> result = getResponseComponentTypesFromRequests(requests);

        assertThat(result.size(), is(8));

        assertTrue(result.contains("SimpleComponentType_v1"));
        assertTrue(result.contains("SimpleComponentType_v2"));
        assertTrue(result.contains("SimpleComponentType_v3"));
        assertTrue(result.contains("SimpleComponentType_v4"));
        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl_v1"));
        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl_v2"));
        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl_v3"));
        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl_v4"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void toDs3Type_NullString_Test() {
        toDs3Type(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void toDs3Type_EmptyString_Test() {
        toDs3Type("");
    }

    @Test
    public void toDs3Type_FullString_Test() {
        final Ds3Type ds3Type = toDs3Type("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl");

        assertThat(ds3Type.getName(), is("com.spectralogic.s3.common.dao.domain.ds3.BucketAclList"));
        assertTrue(isEmpty(ds3Type.getEnumConstants()));
        assertThat(ds3Type.getElements().size(), is(1));

        final Ds3Element element = ds3Type.getElements().get(0);
        assertThat(element.getName(), is("BucketAcl"));
        assertThat(element.getType(), is("array"));
        assertThat(element.getComponentType(), is("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl"));
        assertTrue(isEmpty(element.getDs3Annotations()));
    }

    @Test
    public void createEncapsulatingModelResponseTypes_NullRequests_Test() {
        final ImmutableMap<String, Ds3Type> result = createEncapsulatingModelResponseTypes(
                null,
                createPopulatedDs3TypeMap());
        assertThat(result.size(), is(2));
    }

    @Test
    public void createEncapsulatingModelResponseTypes_EmptyRequests_Test() {
        final ImmutableMap<String, Ds3Type> result = createEncapsulatingModelResponseTypes(
                ImmutableList.of(),
                createPopulatedDs3TypeMap());
        assertThat(result.size(), is(2));
    }

    @Test
    public void createEncapsulatingModelResponseTypes_FullRequests_Test() {
        final ImmutableMap<String, Ds3Type> result = createEncapsulatingModelResponseTypes(
                createPopulatedDs3RequestList("", "_v1_", "_v2_", "_v3_"),
                createPopulatedDs3TypeMap());

        assertThat(result.size(), is(10));
        assertTrue(result.containsKey("com.spectralogic.s3.common.dao.domain.tape.TapePartition"));
        assertTrue(result.containsKey("com.spectralogic.s3.common.dao.domain.tape.TapeDriveType"));

        assertTrue(result.containsKey("SimpleComponentTypeList"));
        assertTrue(result.containsKey("SimpleComponentType_v1_List"));
        assertTrue(result.containsKey("SimpleComponentType_v2_List"));
        assertTrue(result.containsKey("SimpleComponentType_v3_List"));
        assertTrue(result.containsKey("com.spectralogic.s3.common.dao.domain.ds3.BucketAclList"));
        assertTrue(result.containsKey("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl_v1_List"));
        assertTrue(result.containsKey("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl_v2_List"));
        assertTrue(result.containsKey("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl_v3_List"));
    }

    @Test
    public void toUpdatedDs3ResponseType_NullComponentType_Test() {
        final Ds3ResponseType responseType = new Ds3ResponseType(
                "com.spectralogic.s3.common.dao.domain.tape.TapePartition", null);
        final Ds3ResponseType result = toUpdatedDs3ResponseType(responseType);
        assertThat(result, is(responseType));
    }

    @Test
    public void toUpdatedDs3ResponseType_EmptyComponentType_Test() {
        final Ds3ResponseType responseType = new Ds3ResponseType(
                "com.spectralogic.s3.common.dao.domain.tape.TapePartition", "");
        final Ds3ResponseType result = toUpdatedDs3ResponseType(responseType);
        assertThat(result, is(responseType));
    }

    @Test
    public void toUpdatedDs3ResponseType_ComponentType_Test() {
        final Ds3ResponseType responseType = new Ds3ResponseType(
                "array", "com.spectralogic.s3.common.dao.domain.tape.TapePartition");
        final Ds3ResponseType result = toUpdatedDs3ResponseType(responseType);
        assertTrue(result.getType().equals("com.spectralogic.s3.common.dao.domain.tape.TapePartitionList"));
        assertThat(result.getComponentType(), is(nullValue()));
    }

    @Test
    public void toUpdatedDs3ResponseTypesList_NullList_Test() {
        final ImmutableList<Ds3ResponseType> result = toUpdatedDs3ResponseTypesList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toUpdatedDs3ResponseTypesList_EmptyList_Test() {
        final ImmutableList<Ds3ResponseType> result = toUpdatedDs3ResponseTypesList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toUpdatedDs3ResponseTypesList_FullList_Test() {
        final ImmutableList<Ds3ResponseType> responseTypes = createPopulatedDs3ResponseTypeList("");
        final ImmutableList<Ds3ResponseType> result = toUpdatedDs3ResponseTypesList(responseTypes);

        assertThat(result.size(), is(4));
        assertThat(result.get(0), is(responseTypes.get(0)));
        assertThat(result.get(2), is(responseTypes.get(2)));

        assertThat(result.get(1).getType(), is("SimpleComponentTypeList"));
        assertThat(result.get(1).getComponentType(), is(nullValue()));
        assertThat(result.get(3).getType(), is("com.spectralogic.s3.common.dao.domain.ds3.BucketAclList"));
        assertThat(result.get(3).getComponentType(), is(nullValue()));
    }

    @Test
    public void toUpdatedDs3ResponseCodeList_NullList_Test() {
        final ImmutableList<Ds3ResponseCode> result = toUpdatedDs3ResponseCodeList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toUpdatedDs3ResponseCodeList_EmptyList_Test() {
        final ImmutableList<Ds3ResponseCode> result = toUpdatedDs3ResponseCodeList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toUpdatedDs3ResponseCodeList_FullList_Test() {
        final ImmutableList<Ds3ResponseCode> responseTypes = createPopulatedDs3ResponseCodeList("_v1_", "_v2_");
        final ImmutableList<Ds3ResponseCode> result = toUpdatedDs3ResponseCodeList(responseTypes);

        assertThat(result.size(), is(2));

        final ImmutableList<Ds3ResponseType> firstResponseTypes = result.get(0).getDs3ResponseTypes();
        assertThat(firstResponseTypes.size(), is(4));
        assertThat(firstResponseTypes.get(0), is(responseTypes.get(0).getDs3ResponseTypes().get(0)));
        assertThat(firstResponseTypes.get(2), is(responseTypes.get(0).getDs3ResponseTypes().get(2)));

        assertThat(firstResponseTypes.get(1).getType(), is("SimpleComponentType_v1_List"));
        assertThat(firstResponseTypes.get(1).getComponentType(), is(nullValue()));
        assertThat(firstResponseTypes.get(3).getType(), is("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl_v1_List"));
        assertThat(firstResponseTypes.get(3).getComponentType(), is(nullValue()));

        final ImmutableList<Ds3ResponseType> secondResponseTypes = result.get(1).getDs3ResponseTypes();
        assertThat(secondResponseTypes.size(), is(4));
        assertThat(secondResponseTypes.get(0), is(responseTypes.get(1).getDs3ResponseTypes().get(0)));
        assertThat(secondResponseTypes.get(2), is(responseTypes.get(1).getDs3ResponseTypes().get(2)));

        assertThat(secondResponseTypes.get(1).getType(), is("SimpleComponentType_v2_List"));
        assertThat(secondResponseTypes.get(1).getComponentType(), is(nullValue()));
        assertThat(secondResponseTypes.get(3).getType(), is("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl_v2_List"));
        assertThat(secondResponseTypes.get(3).getComponentType(), is(nullValue()));
    }

    @Test
    public void toUpdatedDs3RequestResponseTypes_Test() {
        final Ds3Request request = createPopulatedDs3Request("_v1_", "_v2_");
        final Ds3Request result = toUpdatedDs3RequestResponseTypes(request);
        assertThat(result.getDs3ResponseCodes().size(), is(2));

        verifyPopulatedResponseTypeList(
                result.getDs3ResponseCodes().get(0).getDs3ResponseTypes(),
                request.getDs3ResponseCodes().get(0).getDs3ResponseTypes(),
                "_v1_");

        verifyPopulatedResponseTypeList(
                result.getDs3ResponseCodes().get(1).getDs3ResponseTypes(),
                request.getDs3ResponseCodes().get(1).getDs3ResponseTypes(),
                "_v2_");
    }

    @Test
    public void createAllUpdatedDs3RequestResponseTypes_NullList_Test() {
        final ImmutableList<Ds3Request> result = createAllUpdatedDs3RequestResponseTypes(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void createAllUpdatedDs3RequestResponseTypes_EmptyList_Test() {
        final ImmutableList<Ds3Request> result = createAllUpdatedDs3RequestResponseTypes(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void createAllUpdatedDs3RequestResponseTypes_FullList_Test() {
        final ImmutableList<Ds3Request> requests = createPopulatedDs3RequestList("_v1_", "_v2_", "_v3_", "_v4_");
        final ImmutableList<Ds3Request> result = createAllUpdatedDs3RequestResponseTypes(requests);

        assertThat(result.size(), is(2));
        assertThat(result.get(0).getDs3ResponseCodes().size(), is(2));
        verifyPopulatedResponseTypeList(
                result.get(0).getDs3ResponseCodes().get(0).getDs3ResponseTypes(),
                requests.get(0).getDs3ResponseCodes().get(0).getDs3ResponseTypes(),
                "_v1_");
        verifyPopulatedResponseTypeList(
                result.get(0).getDs3ResponseCodes().get(1).getDs3ResponseTypes(),
                requests.get(0).getDs3ResponseCodes().get(1).getDs3ResponseTypes(),
                "_v2_");

        assertThat(result.get(1).getDs3ResponseCodes().size(), is(2));
        verifyPopulatedResponseTypeList(
                result.get(1).getDs3ResponseCodes().get(0).getDs3ResponseTypes(),
                requests.get(1).getDs3ResponseCodes().get(0).getDs3ResponseTypes(),
                "_v3_");
        verifyPopulatedResponseTypeList(
                result.get(1).getDs3ResponseCodes().get(1).getDs3ResponseTypes(),
                requests.get(1).getDs3ResponseCodes().get(1).getDs3ResponseTypes(),
                "_v4_");
    }

    @Test
    public void convertResponseTypes_Test() {
        final Ds3ApiSpec apiSpec = new Ds3ApiSpec(
                createPopulatedDs3RequestList("_v1_", "_v2_", "_v3_", "_v4_"),
                createPopulatedDs3TypeMap());
        final Ds3ApiSpec result = convertResponseTypes(apiSpec);

        //Check that all Ds3Requests were updated correctly
        assertThat(result.getRequests().size(), is(2));
        assertThat(result.getRequests().get(0).getDs3ResponseCodes().size(), is(2));
        verifyPopulatedResponseTypeList(
                result.getRequests().get(0).getDs3ResponseCodes().get(0).getDs3ResponseTypes(),
                apiSpec.getRequests().get(0).getDs3ResponseCodes().get(0).getDs3ResponseTypes(),
                "_v1_");
        verifyPopulatedResponseTypeList(
                result.getRequests().get(0).getDs3ResponseCodes().get(1).getDs3ResponseTypes(),
                apiSpec.getRequests().get(0).getDs3ResponseCodes().get(1).getDs3ResponseTypes(),
                "_v2_");

        assertThat(result.getRequests().get(1).getDs3ResponseCodes().size(), is(2));
        verifyPopulatedResponseTypeList(
                result.getRequests().get(1).getDs3ResponseCodes().get(0).getDs3ResponseTypes(),
                apiSpec.getRequests().get(1).getDs3ResponseCodes().get(0).getDs3ResponseTypes(),
                "_v3_");
        verifyPopulatedResponseTypeList(
                result.getRequests().get(1).getDs3ResponseCodes().get(1).getDs3ResponseTypes(),
                apiSpec.getRequests().get(1).getDs3ResponseCodes().get(1).getDs3ResponseTypes(),
                "_v4_");

        //Check that all encapsulating Ds3Types were added correctly
        assertThat(result.getTypes().size(), is(10));
        assertTrue(result.getTypes().containsKey("com.spectralogic.s3.common.dao.domain.tape.TapePartition"));
        assertTrue(result.getTypes().containsKey("com.spectralogic.s3.common.dao.domain.tape.TapeDriveType"));
        assertTrue(result.getTypes().containsKey("SimpleComponentType_v1_List"));
        assertTrue(result.getTypes().containsKey("SimpleComponentType_v2_List"));
        assertTrue(result.getTypes().containsKey("SimpleComponentType_v3_List"));
        assertTrue(result.getTypes().containsKey("SimpleComponentType_v4_List"));
        assertTrue(result.getTypes().containsKey("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl_v1_List"));
        assertTrue(result.getTypes().containsKey("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl_v2_List"));
        assertTrue(result.getTypes().containsKey("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl_v3_List"));
        assertTrue(result.getTypes().containsKey("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl_v4_List"));
    }
}
