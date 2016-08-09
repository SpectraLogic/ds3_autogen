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

package com.spectralogic.ds3autogen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.io.ByteStreams;
import com.spectralogic.ds3autogen.models.xml.namemap.TypeNameMap;
import com.spectralogic.ds3autogen.api.Ds3NameMapperParser;
import com.spectralogic.ds3autogen.api.models.namemap.Ds3NameMapper;

import java.io.IOException;
import java.io.InputStream;

public class Ds3NameMapperParserImpl implements Ds3NameMapperParser {

    private final ObjectMapper objectMapper;

    public Ds3NameMapperParserImpl() {
        this.objectMapper = initDs3NameMapper();
    }

    private static ObjectMapper initDs3NameMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new GuavaModule());
        return objectMapper;
    }

    @Override
    public Ds3NameMapper getMap(final InputStream stream) throws IOException {
        return toMap(objectMapper.readValue(ByteStreams.toByteArray(stream), TypeNameMap.class));
    }

    private static Ds3NameMapper toMap(final TypeNameMap typeNameMap) {
        return new Ds3NameMapper(typeNameMap.getTypeMap());
    }
}
