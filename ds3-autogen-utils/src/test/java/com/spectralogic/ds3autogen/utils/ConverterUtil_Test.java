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
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ConverterUtil_Test {

    @Test
    public void enumsEqual_Test() {
        assertTrue(enumsEqual(Action.DELETE, Action.DELETE));
        assertTrue(enumsEqual(HttpVerb.GET, HttpVerb.GET));
        assertFalse(enumsEqual(Action.DELETE, Action.BULK_DELETE));
        assertFalse(enumsEqual(null, Action.DELETE));
        assertFalse(enumsEqual(Action.DELETE, null));
        assertFalse(enumsEqual(null, null));
    }

    @Test
    public void converterUtil_NullList_Test() {
        final ImmutableList<String> nullList = null;
        assertTrue(isEmpty(nullList));
        assertFalse(hasContent(nullList));
    }

    @Test
    public void convertUtil_EmptyList_Test() {
        final ImmutableList<String> emptyList = ImmutableList.of();
        assertTrue(isEmpty(emptyList));
        assertFalse(hasContent(emptyList));
    }
    @Test
    public void convertUtil_FullList_Test() {
        final ImmutableList<String> fullList = ImmutableList.of("One", "Two", "Three");
        assertTrue(hasContent(fullList));
        assertFalse(isEmpty(fullList));
    }

    @Test
    public void convertUtil_NullMap_Test() {
        final ImmutableMap<String, String> nullMap = null;
        assertTrue(isEmpty(nullMap));
        assertFalse(hasContent(nullMap));
    }
    @Test
    public void convertUtil_EmptyMap_Test() {
        final ImmutableMap<String, String> emptyMap = ImmutableMap.of();
        assertTrue(isEmpty(emptyMap));
        assertFalse(hasContent(emptyMap));
    }

    @Test
    public void convertUtil_FullMap_Test() {
        final ImmutableMap<String, String> fullMap = ImmutableMap.of(
                "key1", "value1",
                "key2", "value2");
        assertTrue(hasContent(fullMap));
        assertFalse(isEmpty(fullMap));
    }

    @Test
    public void convertUtil_NullSet_Test() {
        final ImmutableSet<String> nullSet = null;
        assertTrue(isEmpty(nullSet));
        assertFalse(hasContent(nullSet));
    }

    @Test
    public void convertUtil_EmptySet_Test() {
        final ImmutableSet<String> nullSet = ImmutableSet.of();
        assertTrue(isEmpty(nullSet));
        assertFalse(hasContent(nullSet));
    }

    @Test
    public void convertUtil_FullSet_Test() {
        final ImmutableSet<String> fullSet = ImmutableSet.of("One", "two", "three");
        assertTrue(hasContent(fullSet));
        assertFalse(isEmpty(fullSet));
    }

    @Test
    public void convertUtil_NullString_Test() {
        final String nullString = null;
        assertTrue(isEmpty(nullString));
        assertFalse(hasContent(nullString));
    }

    @Test
    public void convertUtil_EmptyString_Test() {
        final String emptyString = "";
        assertTrue(isEmpty(emptyString));
        assertFalse(hasContent(emptyString));
    }

    @Test
    public void convertUtil_FullString_Test() {
        final String fullString = "Hello World";
        assertTrue(hasContent(fullString));
        assertFalse(isEmpty(fullString));
    }

    @Test
    public void getUsedTypesFromParams_NullList_Test() {
        final ImmutableSet<String> nullResult = getUsedTypesFromParams(null);
        assertTrue(nullResult.isEmpty());
    }

    @Test
    public void getUsedTypesFromParams_EmptyList_Test() {
        final ImmutableSet<String> emptyResult = getUsedTypesFromParams(ImmutableList.of());
        assertTrue(emptyResult.isEmpty());
    }

    @Test
    public void getUsedTypesFromParams_FullList_Test() {
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
                createDs3RequestTestData(
                        false,
                        createPopulatedDs3ResponseCodeList("_v1", "_v2"),
                        ImmutableList.of(
                                createDs3ParamTestData("java.util.UUID"),
                                createDs3ParamTestData("com.spectralogic.s3.common.dao.domain.ds3.BucketAclPermission")),
                        ImmutableList.of(
                                createDs3ParamTestData("long"),
                                createDs3ParamTestData("com.spectralogic.s3.common.dao.domain.pool.PoolHealth"))),
                createDs3RequestTestData(
                        false,
                        ImmutableList.of(
                                createDs3ParamTestData("com.spectralogic.s3.common.dao.domain.ds3.BucketAclPermission"),
                                createDs3ParamTestData("com.spectralogic.s3.common.dao.domain.pool.PoolState")),
                        ImmutableList.of())
                );

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
    public void removeUnusedTypes_EmptyList_Test() {
        assertTrue(removeUnusedTypes(null, null).isEmpty());
        assertTrue(removeUnusedTypes(ImmutableMap.of(), null).isEmpty());
        assertTrue(removeUnusedTypes(null, ImmutableList.of()).isEmpty());
        assertTrue(removeUnusedTypes(ImmutableMap.of(), ImmutableList.of()).isEmpty());
    }

    @Test
    public void removeUnusedTypes_FullList_Test() {
        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                createDs3RequestTestData(
                        false,
                        ImmutableList.of(
                                createDs3ParamTestData("java.util.UUID"),
                                createDs3ParamTestData("com.spectralogic.s3.common.dao.domain.ds3.BucketAclPermission")),
                        ImmutableList.of(
                                createDs3ParamTestData("long"),
                                createDs3ParamTestData("com.spectralogic.s3.common.dao.domain.pool.PoolHealth"))),
                createDs3RequestTestData(
                        false,
                        ImmutableList.of(
                                createDs3ParamTestData("com.spectralogic.s3.common.dao.domain.ds3.BucketAclPermission"),
                                createDs3ParamTestData("com.spectralogic.s3.common.dao.domain.pool.PoolState")),
                        ImmutableList.of())
                );

        final ImmutableMap<String, Ds3Type> types = ImmutableMap.of(
                "com.spectralogic.s3.common.dao.domain.tape.TapeState", createEmptyDs3Type(),
                "com.spectralogic.s3.common.dao.domain.ds3.BucketAclPermission", createEmptyDs3Type(),
                "com.spectralogic.s3.common.dao.domain.pool.PoolState", createEmptyDs3Type(),
                "com.spectralogic.s3.common.dao.domain.tape.TapeType", createEmptyDs3Type(),
                "com.spectralogic.s3.common.dao.domain.pool.PoolHealth", createEmptyDs3Type());

        final ImmutableMap<String, Ds3Type> result = removeUnusedTypes(types, requests);
        assertThat(result.size(), is(3));
        assertTrue(result.containsKey("com.spectralogic.s3.common.dao.domain.ds3.BucketAclPermission"));
        assertTrue(result.containsKey("com.spectralogic.s3.common.dao.domain.pool.PoolHealth"));
        assertTrue(result.containsKey("com.spectralogic.s3.common.dao.domain.pool.PoolState"));
        assertFalse(result.containsKey("com.spectralogic.s3.common.dao.domain.tape.TapeState"));
        assertFalse(result.containsKey("com.spectralogic.s3.common.dao.domain.tape.TapeType"));
    }

    @Test
    public void removeUnusedTypes_EncapsulatingTypes_Test() {
        final String parentType = "com.spectralogic.Test.Parent";
        final String childType = "com.spectralogic.Test.Child";
        final String grandchildType = "com.spectralogic.Test.Grandchild";

        final Ds3Type parentDs3Type = createDs3TypeTestData(
                parentType,
                createDs3ElementListTestData("Child", childType));
        final Ds3Type childDs3Type = createDs3TypeTestData(
                childType,
                createDs3ElementListTestData("Grandchild", grandchildType));
        final Ds3Type grandchildDs3Type = createDs3TypeTestData(grandchildType);

        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                createDs3RequestTestData(
                        false,
                        ImmutableList.of(
                                createDs3ParamTestData("java.util.UUID"),
                                new Ds3Param(null, parentType)),
                        null));

        final ImmutableMap<String, Ds3Type> typeMap = ImmutableMap.of(
                parentType, parentDs3Type,
                childType, childDs3Type,
                grandchildType, grandchildDs3Type,
                "com.spectralogic.Test.UnusedType", createEmptyDs3Type());

        final ImmutableMap<String, Ds3Type> result = removeUnusedTypes(typeMap, requests);
        assertThat(result.size(), is(3));
        assertTrue(result.containsKey(parentType));
        assertTrue(result.containsKey(childType));
        assertTrue(result.containsKey(grandchildType));
    }

    @Test
    public void includeType_Test() {
        assertTrue(includeType("com.spectralogic.s3.common.dao.domain.tape.TapeState"));
        assertFalse(includeType("java.util.UUID"));
        assertFalse(includeType(""));
        assertFalse(includeType(null));
    }

    @Test
    public void isEnum_Test() {
        assertFalse(isEnum(createDs3TypeTestData("typeName")));
        assertFalse(isEnum(new Ds3Type("typeName", null, ImmutableList.of(), ImmutableList.of())));

        assertTrue(isEnum(new Ds3Type("typeName", null, createEmptyDs3ElementList(), createEmptyDs3EnumConstantList())));
        assertTrue(isEnum(new Ds3Type("typeName", null, null, createEmptyDs3EnumConstantList())));
        assertTrue(isEnum(new Ds3Type("typeName", null, ImmutableList.of(), createEmptyDs3EnumConstantList())));
        assertFalse(isEnum(new Ds3Type("typeName", null, createEmptyDs3ElementList(), null)));
        assertFalse(isEnum(new Ds3Type("typeName", null, createEmptyDs3ElementList(), ImmutableList.of())));
    }

    @Test
    public void getUsedTypesFromType_EmptyType_Test() {
        final ImmutableSet<String> nullResult = getUsedTypesFromType(createDs3TypeTestData("typeName"));
        assertThat(nullResult.size(), is(0));

        final ImmutableSet<String> emptyResult = getUsedTypesFromType(new Ds3Type("typeName", null, ImmutableList.of(), ImmutableList.of()));
        assertThat(emptyResult.size(), is(0));
    }

    @Test
    public void getUsedTypesFromType_EnumTypes_Test() {
        final ImmutableList<Ds3Element> ds3Elements = ImmutableList.of(
                createDs3ElementTestData("java.util.UUID"),
                createDs3ElementTestData("com.spectralogic.s3.common.dao.domain.tape.TapeState"),
                createDs3ElementTestData("array", "com.spectralogic.s3.common.dao.domain.pool.PoolState"),
                createDs3ElementTestData("array", "com.spectralogic.s3.common.dao.domain.tape.TapeState"));

        final ImmutableSet<String> nullElementsResult = getUsedTypesFromType(new Ds3Type("typeName", null, null, createEmptyDs3EnumConstantList()));
        assertThat(nullElementsResult.size(), is(0));

        final ImmutableSet<String> emptyElementsResult = getUsedTypesFromType(new Ds3Type("typeName", null, ImmutableList.of(), createEmptyDs3EnumConstantList()));
        assertThat(emptyElementsResult.size(), is(0));

        final ImmutableSet<String> fullElementsResult = getUsedTypesFromType(new Ds3Type("typeName", null, ds3Elements, createEmptyDs3EnumConstantList()));
        assertThat(fullElementsResult.size(), is(0));
    }

    @Test
    public void getUsedTypesFromType_Test() {
        final ImmutableList<Ds3Element> ds3Elements = ImmutableList.of(
                createDs3ElementTestData("java.util.UUID"),
                createDs3ElementTestData("com.spectralogic.s3.common.dao.domain.tape.TapeState"),
                createDs3ElementTestData("array", "com.spectralogic.s3.common.dao.domain.pool.PoolState"),
                createDs3ElementTestData("array", "com.spectralogic.s3.common.dao.domain.tape.TapeState"));

        final ImmutableSet<String> nullEnumResult = getUsedTypesFromType(createDs3TypeTestData("typeName", ds3Elements));
        assertThat(nullEnumResult.size(), is(2));
        assertTrue(nullEnumResult.contains("com.spectralogic.s3.common.dao.domain.pool.PoolState"));
        assertTrue(nullEnumResult.contains("com.spectralogic.s3.common.dao.domain.tape.TapeState"));

        final ImmutableSet<String> emptyEnumResult = getUsedTypesFromType(new Ds3Type("typeName", null, ds3Elements, ImmutableList.of()));
        assertThat(emptyEnumResult.size(), is(2));
        assertTrue(emptyEnumResult.contains("com.spectralogic.s3.common.dao.domain.pool.PoolState"));
        assertTrue(emptyEnumResult.contains("com.spectralogic.s3.common.dao.domain.tape.TapeState"));
    }

    @Test
    public void getUsedTypesFromAllTypes_EmptySet_Test() {
        final ImmutableMap<String, Ds3Type> typeMap = ImmutableMap.of(
                "type1", createDs3TypeTestData("type1"),
                "type2", createDs3TypeTestData("type2"));

        final ImmutableSet nullResult = getUsedTypesFromAllTypes(typeMap, null);
        assertThat(nullResult.size(), is(0));

        final ImmutableSet emptyResult = getUsedTypesFromAllTypes(typeMap, ImmutableSet.of());
        assertThat(emptyResult.size(), is(0));
    }

    @Test
    public void getUsedTypesFromAllTypes_FullSet_Test() {
        final String parentType = "com.spectralogic.s3.common.dao.domain.pool.Parent";
        final String childType = "com.spectralogic.s3.common.dao.domain.pool.Child";
        final String grandchildType = "com.spectralogic.s3.common.dao.domain.pool.Grandchild";

        final String tapeState = "com.spectralogic.s3.common.dao.domain.tape.TapeState";
        final String poolHealth = "com.spectralogic.s3.common.dao.domain.pool.PoolHealth";
        final String permission = "com.spectralogic.s3.common.dao.domain.ds3.BucketAclPermission";

        final Ds3Type parentDs3Type = createDs3TypeTestData(
                parentType,
                ImmutableList.of(createDs3ElementTestData("Child", childType)));
        final Ds3Type childDs3Type = createDs3TypeTestData(
                childType,
                ImmutableList.of(
                        createDs3ElementTestData("Grandchild", grandchildType),
                        createDs3ElementTestData("TapeState", "array", tapeState)));
        final Ds3Type grandchildDs3Type = createDs3TypeTestData(
                grandchildType,
                ImmutableList.of(createDs3ElementTestData("PoolHealth", poolHealth)));

        final ImmutableMap.Builder<String, Ds3Type> typeMapBuilder = ImmutableMap.builder();
        typeMapBuilder.put(parentType, parentDs3Type);
        typeMapBuilder.put(childType, childDs3Type);
        typeMapBuilder.put(grandchildType, grandchildDs3Type);
        typeMapBuilder.put(tapeState, createDs3TypeTestData(tapeState));
        typeMapBuilder.put(poolHealth, createDs3TypeTestData(poolHealth));
        typeMapBuilder.put(permission, createDs3TypeTestData(permission));

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
