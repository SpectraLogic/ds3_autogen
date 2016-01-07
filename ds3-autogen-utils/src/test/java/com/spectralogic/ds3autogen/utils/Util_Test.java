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

package com.spectralogic.ds3autogen.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.*;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.createPopulatedDs3ResponseCodeList;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.createPopulatedDs3ResponseTypeList;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class Util_Test {

    @Test
    public void enumsEqual_test() {
        assertTrue(enumsEqual(Action.DELETE, Action.DELETE));
        assertTrue(enumsEqual(HttpVerb.GET, HttpVerb.GET));
        assertFalse(enumsEqual(Action.DELETE, Action.BULK_DELETE));
        assertFalse(enumsEqual(null, Action.DELETE));
        assertFalse(enumsEqual(Action.DELETE, null));
        assertFalse(enumsEqual(null, null));
    }

    @Test
    public void converterUtilNullList() {
        final ImmutableList<String> nullList = null;
        assertTrue(isEmpty(nullList));
        assertFalse(hasContent(nullList));
    }

    @Test
    public void convertUtilEmptyList() {
        final ImmutableList<String> emptyList = ImmutableList.of();
        assertTrue(isEmpty(emptyList));
        assertFalse(hasContent(emptyList));
    }
    @Test
    public void convertUtilFullList() {
        final ImmutableList<String> fullList = ImmutableList.of("One", "Two", "Three");
        assertTrue(hasContent(fullList));
        assertFalse(isEmpty(fullList));
    }

    @Test
    public void convertUtilNullMap() {
        final ImmutableMap<String, String> nullMap = null;
        assertTrue(isEmpty(nullMap));
        assertFalse(hasContent(nullMap));
    }
    @Test
    public void convertUtilEmptyMap() {
        final ImmutableMap<String, String> emptyMap = ImmutableMap.of();
        assertTrue(isEmpty(emptyMap));
        assertFalse(hasContent(emptyMap));
    }

    @Test
    public void convertUtilFullMap() {
        final ImmutableMap<String, String> fullMap = ImmutableMap.of(
                "key1", "value1",
                "key2", "value2");
        assertTrue(hasContent(fullMap));
        assertFalse(isEmpty(fullMap));
    }

    @Test
    public void convertUtilNullSet() {
        final ImmutableSet<String> nullSet = null;
        assertTrue(isEmpty(nullSet));
        assertFalse(hasContent(nullSet));
    }

    @Test
    public void convertUtilEmptySet() {
        final ImmutableSet<String> nullSet = ImmutableSet.of();
        assertTrue(isEmpty(nullSet));
        assertFalse(hasContent(nullSet));
    }

    @Test
    public void convertUtilEmptyFullSet() {
        final ImmutableSet<String> fullSet = ImmutableSet.of("One", "two", "three");
        assertTrue(hasContent(fullSet));
        assertFalse(isEmpty(fullSet));
    }

    @Test
    public void convertUtilNullString() {
        final String nullString = null;
        assertTrue(isEmpty(nullString));
        assertFalse(hasContent(nullString));
    }

    @Test
    public void convertUtilEmptyString() {
        final String emptyString = "";
        assertTrue(isEmpty(emptyString));
        assertFalse(hasContent(emptyString));
    }

    @Test
    public void convertUtilFullString() {
        final String fullString = "Hello World";
        assertTrue(hasContent(fullString));
        assertFalse(isEmpty(fullString));
    }

    @Test
    public void removeSpectraInternalRequestsNull() {
        final ImmutableList<Ds3Request> nullResult = removeSpectraInternalRequests(null);
        assertTrue(nullResult.isEmpty());
    }

    @Test
    public void removeSpectraInternalRequestsEmpty() {
        final ImmutableList<Ds3Request> emptyResult = removeSpectraInternalRequests(ImmutableList.of());
        assertTrue(emptyResult.isEmpty());
    }

    @Test
    public void removeSpectraInternalRequestsFull() {
        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                new Ds3Request("Request1", null, Classification.amazons3, null, null, null, null, null, null, null, null, null),
                new Ds3Request("Request2", null, Classification.spectrainternal, null, null, null, null, null, null, null, null, null),
                new Ds3Request("Request3", null, Classification.spectrads3, null, null, null, null, null, null, null, null, null));
        final ImmutableList<Ds3Request> result = removeSpectraInternalRequests(requests);
        assertThat(result.size(), is(2));
        assertTrue(result.get(0).getClassification() != Classification.spectrainternal);
        assertTrue(result.get(1).getClassification() != Classification.spectrainternal);
    }

    @Test
    public void getUsedTypesFromParamsNull() {
        final ImmutableSet<String> nullResult = getUsedTypesFromParams(null);
        assertTrue(nullResult.isEmpty());
    }

    @Test
    public void getUsedTypesFromParamsEmpty() {
        final ImmutableSet<String> emptyResult = getUsedTypesFromParams(ImmutableList.of());
        assertTrue(emptyResult.isEmpty());
    }

    @Test
    public void getUsedTypesFromParamsFull() {
        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("Type1", "java.util.UUID"),
                new Ds3Param("Type2", "com.spectralogic.s3.common.dao.domain.ds3.BucketAclPermission"),
                new Ds3Param("Type3", "com.spectralogic.s3.common.dao.domain.ds3.BucketAclPermission"),
                new Ds3Param("Type4", "long"),
                new Ds3Param("Type5", "com.spectralogic.s3.common.dao.domain.pool.PoolHealth")
        );
        final ImmutableSet<String> fullResult = getUsedTypesFromParams(params);
        assertThat(fullResult.size(), is(2));
        assertTrue(fullResult.contains("com.spectralogic.s3.common.dao.domain.ds3.BucketAclPermission"));
        assertTrue(fullResult.contains("com.spectralogic.s3.common.dao.domain.pool.PoolHealth"));
        assertFalse(fullResult.contains("java.util.UUID"));
        assertFalse(fullResult.contains("long"));
    }

    @Test
    public void getUsedTypesFromRequests_NullList_Test() {
        final ImmutableSet<String> nullResult = getUsedTypesFromRequests(null);
        assertTrue(nullResult.isEmpty());
    }

    @Test
    public void getUsedTypesFromRequests_EmptyList_Test() {
        final ImmutableSet<String> emptyResult = getUsedTypesFromRequests(ImmutableList.of());
        assertTrue(emptyResult.isEmpty());
    }

    @Test
    public void getUsedTypesFromRequests_FullList_Test() {
        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                new Ds3Request(null, null, null, null, null, null, null, null, null,
                        createPopulatedDs3ResponseCodeList("_v1", "_v2"),
                        ImmutableList.of(
                                new Ds3Param(null, "java.util.UUID"),
                                new Ds3Param(null, "com.spectralogic.s3.common.dao.domain.ds3.BucketAclPermission")),
                        ImmutableList.of(
                                new Ds3Param(null, "long"),
                                new Ds3Param(null, "com.spectralogic.s3.common.dao.domain.pool.PoolHealth"))),
                new Ds3Request(null, null, null, null, null, null, null, null, null, null,
                        ImmutableList.of(
                                new Ds3Param(null, "com.spectralogic.s3.common.dao.domain.ds3.BucketAclPermission"),
                                new Ds3Param(null, "com.spectralogic.s3.common.dao.domain.pool.PoolState")),
                        ImmutableList.of()));
        final ImmutableSet<String> result = getUsedTypesFromRequests(requests);

        assertThat(result.size(), is(7));
        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.ds3.BucketAclPermission"));
        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.pool.PoolHealth"));
        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.pool.PoolState"));

        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.tape.TapePartition_v1"));
        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.tape.TapePartition_v2"));
        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl_v1"));
        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl_v2"));
    }

    @Test
    public void removeUsedTypesEmpty() {
        assertTrue(removeUnusedTypes(null, null).isEmpty());
        assertTrue(removeUnusedTypes(ImmutableMap.of(), null).isEmpty());
        assertTrue(removeUnusedTypes(null, ImmutableList.of()).isEmpty());
        assertTrue(removeUnusedTypes(ImmutableMap.of(), ImmutableList.of()).isEmpty());
    }

    @Test
    public void removeUsedTypesFull() {
        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                new Ds3Request(null, null, null, null, null, null, null, null, null, null,
                        ImmutableList.of(
                                new Ds3Param(null, "java.util.UUID"),
                                new Ds3Param(null, "com.spectralogic.s3.common.dao.domain.ds3.BucketAclPermission")),
                        ImmutableList.of(
                                new Ds3Param(null, "long"),
                                new Ds3Param(null, "com.spectralogic.s3.common.dao.domain.pool.PoolHealth"))),
                new Ds3Request(null, null, null, null, null, null, null, null, null, null,
                        ImmutableList.of(
                                new Ds3Param(null, "com.spectralogic.s3.common.dao.domain.ds3.BucketAclPermission"),
                                new Ds3Param(null, "com.spectralogic.s3.common.dao.domain.pool.PoolState")),
                        ImmutableList.of()));

        final ImmutableMap<String, Ds3Type> types = ImmutableMap.of(
                "com.spectralogic.s3.common.dao.domain.tape.TapeState", new Ds3Type(null, null, null),
                "com.spectralogic.s3.common.dao.domain.ds3.BucketAclPermission", new Ds3Type(null, null, null),
                "com.spectralogic.s3.common.dao.domain.pool.PoolState", new Ds3Type(null, null, null),
                "com.spectralogic.s3.common.dao.domain.tape.TapeType", new Ds3Type(null, null, null),
                "com.spectralogic.s3.common.dao.domain.pool.PoolHealth", new Ds3Type(null, null, null));

        final ImmutableMap<String, Ds3Type> result = removeUnusedTypes(types, requests);
        assertThat(result.size(), is(3));
        assertTrue(result.containsKey("com.spectralogic.s3.common.dao.domain.ds3.BucketAclPermission"));
        assertTrue(result.containsKey("com.spectralogic.s3.common.dao.domain.pool.PoolHealth"));
        assertTrue(result.containsKey("com.spectralogic.s3.common.dao.domain.pool.PoolState"));
        assertFalse(result.containsKey("com.spectralogic.s3.common.dao.domain.tape.TapeState"));
        assertFalse(result.containsKey("com.spectralogic.s3.common.dao.domain.tape.TapeType"));
    }

    @Test
    public void converterUtilIncludeType() {
        assertTrue(includeType("com.spectralogic.s3.common.dao.domain.tape.TapeState"));
        assertFalse(includeType("java.util.UUID"));
        assertFalse(includeType(""));
        assertFalse(includeType(null));
    }

    @Test
    public void converterUtilIsEnum() {
        assertFalse(isEnum(new Ds3Type("typeName", null, null)));
        assertFalse(isEnum(new Ds3Type("typeName", ImmutableList.of(), ImmutableList.of())));

        final ImmutableList<Ds3Element> ds3Elements = ImmutableList.of(
                new Ds3Element(null, null, null, null),
                new Ds3Element(null, null, null, null));

        final ImmutableList<Ds3EnumConstant> ds3EnumConstants = ImmutableList.of(
                new Ds3EnumConstant(null, null),
                new Ds3EnumConstant(null, null));

        assertTrue(isEnum(new Ds3Type("typeName", ds3Elements, ds3EnumConstants)));
        assertTrue(isEnum(new Ds3Type("typeName", null, ds3EnumConstants)));
        assertTrue(isEnum(new Ds3Type("typeName", ImmutableList.of(), ds3EnumConstants)));
        assertFalse(isEnum(new Ds3Type("typeName", ds3Elements, null)));
        assertFalse(isEnum(new Ds3Type("typeName", ds3Elements, ImmutableList.of())));
    }

    @Test
    public void converterUtilGetUsedTypesFromTypeEmpty() {
        final ImmutableSet<String> nullResult = getUsedTypesFromType(new Ds3Type("typeName", null, null));
        assertThat(nullResult.size(), is(0));

        final ImmutableSet<String> emptyResult = getUsedTypesFromType(new Ds3Type("typeName", ImmutableList.of(), ImmutableList.of()));
        assertThat(emptyResult.size(), is(0));
    }

    @Test
    public void converterUtilGetUsedTypesFromTypeEnumTypes() {
        final ImmutableList<Ds3Element> ds3Elements = ImmutableList.of(
                new Ds3Element(null, "java.util.UUID", null, null),
                new Ds3Element(null, "com.spectralogic.s3.common.dao.domain.tape.TapeState", null, null),
                new Ds3Element(null, "array", "com.spectralogic.s3.common.dao.domain.pool.PoolState", null),
                new Ds3Element(null, "array", "com.spectralogic.s3.common.dao.domain.tape.TapeState", null));

        final ImmutableList<Ds3EnumConstant> ds3EnumConstants = ImmutableList.of(
                new Ds3EnumConstant(null, null),
                new Ds3EnumConstant(null, null));

        final ImmutableSet<String> nullElementsResult = getUsedTypesFromType(new Ds3Type("typeName", null, ds3EnumConstants));
        assertThat(nullElementsResult.size(), is(0));

        final ImmutableSet<String> emptyElementsResult = getUsedTypesFromType(new Ds3Type("typeName", ImmutableList.of(), ds3EnumConstants));
        assertThat(emptyElementsResult.size(), is(0));

        final ImmutableSet<String> fullElementsResult = getUsedTypesFromType(new Ds3Type("typeName", ds3Elements, ds3EnumConstants));
        assertThat(fullElementsResult.size(), is(0));
    }

    @Test
    public void converterUtilGetUsedTypesFromType() {
        final ImmutableList<Ds3Element> ds3Elements = ImmutableList.of(
                new Ds3Element(null, "java.util.UUID", null, null),
                new Ds3Element(null, "com.spectralogic.s3.common.dao.domain.tape.TapeState", null, null),
                new Ds3Element(null, "array", "com.spectralogic.s3.common.dao.domain.pool.PoolState", null),
                new Ds3Element(null, "array", "com.spectralogic.s3.common.dao.domain.tape.TapeState", null));

        final ImmutableSet<String> nullEnumResult = getUsedTypesFromType(new Ds3Type("typeName", ds3Elements, null));
        assertThat(nullEnumResult.size(), is(2));
        assertTrue(nullEnumResult.contains("com.spectralogic.s3.common.dao.domain.pool.PoolState"));
        assertTrue(nullEnumResult.contains("com.spectralogic.s3.common.dao.domain.tape.TapeState"));

        final ImmutableSet<String> emptyEnumResult = getUsedTypesFromType(new Ds3Type("typeName", ds3Elements, ImmutableList.of()));
        assertThat(emptyEnumResult.size(), is(2));
        assertTrue(emptyEnumResult.contains("com.spectralogic.s3.common.dao.domain.pool.PoolState"));
        assertTrue(emptyEnumResult.contains("com.spectralogic.s3.common.dao.domain.tape.TapeState"));
    }

    @Test
    public void converterUtilGetUsedTypesFromAllTypesEmpty() {
        final ImmutableMap<String, Ds3Type> typeMap = ImmutableMap.of(
                "type1", new Ds3Type("type1", null, null),
                "type2", new Ds3Type("type2", null, null));

        final ImmutableSet nullResult = getUsedTypesFromAllTypes(typeMap, null);
        assertThat(nullResult.size(), is(0));

        final ImmutableSet emptyResult = getUsedTypesFromAllTypes(typeMap, ImmutableSet.of());
        assertThat(emptyResult.size(), is(0));
    }

    @Test
    public void converterUtilGetUsedTypesFromAllTypes() {
        final String parentType = "com.spectralogic.s3.common.dao.domain.pool.Parent";
        final String childType = "com.spectralogic.s3.common.dao.domain.pool.Child";
        final String grandchildType = "com.spectralogic.s3.common.dao.domain.pool.Grandchild";

        final String tapeState = "com.spectralogic.s3.common.dao.domain.tape.TapeState";
        final String poolHealth = "com.spectralogic.s3.common.dao.domain.pool.PoolHealth";
        final String permission = "com.spectralogic.s3.common.dao.domain.ds3.BucketAclPermission";

        final Ds3Type parentDs3Type = new Ds3Type(
                parentType,
                ImmutableList.of(new Ds3Element("Child", childType, null, null)),
                null);
        final Ds3Type childDs3Type = new Ds3Type(
                childType,
                ImmutableList.of(
                        new Ds3Element("Grandchild", grandchildType, null, null),
                        new Ds3Element("TapeState", "array", tapeState, null)),
                null);
        final Ds3Type grandchildDs3Type = new Ds3Type(
                grandchildType,
                ImmutableList.of(new Ds3Element("PoolHealth", poolHealth, null, null)),
                null);

        final ImmutableMap.Builder<String, Ds3Type> typeMapBuilder = ImmutableMap.builder();
        typeMapBuilder.put(parentType, parentDs3Type);
        typeMapBuilder.put(childType, childDs3Type);
        typeMapBuilder.put(grandchildType, grandchildDs3Type);
        typeMapBuilder.put(tapeState, new Ds3Type(tapeState, null, null));
        typeMapBuilder.put(poolHealth, new Ds3Type(poolHealth, null, null));
        typeMapBuilder.put(permission, new Ds3Type(permission, null, null));

        final ImmutableSet<String> usedTypes = ImmutableSet.of(parentType);
        final ImmutableSet<String> result = getUsedTypesFromAllTypes(typeMapBuilder.build(), usedTypes);

        assertThat(result.size(), is(5));
        assertTrue(result.contains(parentType));
        assertTrue(result.contains(childType));
        assertTrue(result.contains(grandchildType));
        assertTrue(result.contains(tapeState));
        assertTrue(result.contains(poolHealth));
        assertFalse(result.contains(permission));
    }

    @Test (expected = IllegalArgumentException.class)
    public void getTypeFromResponseType_SimpleType_Test() {
        getTypeFromResponseType(new Ds3ResponseType("SimpleType", null));
    }

    @Test
    public void getTypeFromResponseType_SpectraType_Test() {
        final String result = getTypeFromResponseType(
                new Ds3ResponseType("com.spectralogic.s3.server.domain.HttpErrorResultApiBean", null));
        assertThat(result, is("com.spectralogic.s3.server.domain.HttpErrorResultApiBean"));
    }
    @Test
    public void getTypeFromResponseType_SpectraComponentType_Test() {
        final String result = getTypeFromResponseType(
                new Ds3ResponseType("array", "com.spectralogic.s3.server.domain.HttpErrorResultApiBean"));
        assertThat(result, is("com.spectralogic.s3.server.domain.HttpErrorResultApiBean"));
    }

    @Test
    public void includeResponseType_Test() {
        assertTrue(includeResponseType(new Ds3ResponseType("com.spectralogic.s3.server.domain.HttpErrorResultApiBean", null)));
        assertTrue(includeResponseType(new Ds3ResponseType("array", "com.spectralogic.s3.server.domain.HttpErrorResultApiBean")));
        assertFalse(includeResponseType(new Ds3ResponseType("SimpleType", null)));
        assertFalse(includeResponseType(new Ds3ResponseType("SimpleType", "ComponentType")));
    }

    @Test
    public void getUsedTypesFromResponseTypes_NullList_Test() {
        final ImmutableSet<String> result = getUsedTypesFromResponseTypes(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getUsedTypesFromResponseTypes_EmptyList_Test() {
        final ImmutableSet<String> result = getUsedTypesFromResponseTypes(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void getUsedTypesFromResponseTypes_FullList_Test() {
        final ImmutableList<Ds3ResponseType> responseTypes = createPopulatedDs3ResponseTypeList("");
        final ImmutableSet<String> result = getUsedTypesFromResponseTypes(responseTypes);
        assertThat(result.size(), is(2));
        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.tape.TapePartition"));
        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl"));
    }

    @Test
    public void getUsedTypesFromResponses_NullList_Test() {
        final ImmutableSet<String> result = getUsedTypesFromResponseCodes(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getUsedTypesFromResponses_EmptyList_Test() {
        final ImmutableSet<String> result = getUsedTypesFromResponseCodes(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void getUsedTypesFromResponses_FullList_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = createPopulatedDs3ResponseCodeList("_v1", "_v2");
        final ImmutableSet<String> result = getUsedTypesFromResponseCodes(responseCodes);

        assertThat(result.size(), is(4));
        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.tape.TapePartition_v1"));
        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.tape.TapePartition_v2"));
        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl_v1"));
        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl_v2"));
    }
}
