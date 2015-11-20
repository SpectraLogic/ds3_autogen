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

package com.spectralogic.d3autogen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.io.ByteStreams;
import com.spectralogic.d3autogen.models.xml.TypeNameMap;
import com.spectralogic.ds3autogen.api.Ds3NameMapperParser;
import com.spectralogic.ds3autogen.api.models.Ds3NameMapper;

import java.io.IOException;
import java.io.InputStream;

public class Ds3NameMapperParserImpl implements Ds3NameMapperParser {

    private final Ds3NameMapper ds3NameMapper;

    public Ds3NameMapperParserImpl() throws IOException {
        this.ds3NameMapper = initDs3NameMapper();
    }

    private static Ds3NameMapper initDs3NameMapper() throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new GuavaModule());
        final InputStream inputStream = Ds3NameMapperParserImpl.class.getResourceAsStream("/typeNameMap.json");
        return toMap(objectMapper.readValue(ByteStreams.toByteArray(inputStream), TypeNameMap.class));
    }

    @Override
    public Ds3NameMapper getMap() {
        return this.ds3NameMapper;
    }

    private static Ds3NameMapper toMap(final TypeNameMap typeNameMap) {
        final Ds3NameMapper ds3NameMapper = new Ds3NameMapper(typeNameMap.getTypeMap());
        return ds3NameMapper;
    }
}
