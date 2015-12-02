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
import com.spectralogic.ds3autogen.api.models.Ds3Param;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import org.junit.Test;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class Util_Test {

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
    public void getUsedTypesNull() {
        final ImmutableSet<String> nullResult = getUsedTypes(null);
        assertTrue(nullResult.isEmpty());
    }

    @Test
    public void getUsedTypesEmpty() {
        final ImmutableSet<String> emptyResult = getUsedTypes(ImmutableList.of());
        assertTrue(emptyResult.isEmpty());
    }

    @Test
    public void getUsedTypesFull() {
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
        final ImmutableSet<String> result = getUsedTypes(requests);
        assertThat(result.size(), is(3));
        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.ds3.BucketAclPermission"));
        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.pool.PoolHealth"));
        assertTrue(result.contains("com.spectralogic.s3.common.dao.domain.pool.PoolState"));
        assertFalse(result.contains("java.util.UUID"));
        assertFalse(result.contains("long"));
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
}
