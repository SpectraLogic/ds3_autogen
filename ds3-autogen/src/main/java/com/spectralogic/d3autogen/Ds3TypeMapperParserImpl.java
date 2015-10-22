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
import com.spectralogic.d3autogen.models.xml.TypeMap;
import com.spectralogic.ds3autogen.api.Ds3TypeMapperParser;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.models.Ds3TypeMapper;

import java.io.IOException;
import java.io.InputStream;

public class Ds3TypeMapperParserImpl implements Ds3TypeMapperParser {

    private final Ds3TypeMapper ds3TypeMapper;

    public Ds3TypeMapperParserImpl() throws IOException {
        this.ds3TypeMapper = initDs3TypeMapper();
    }

    private static Ds3TypeMapper initDs3TypeMapper() throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new GuavaModule());
        final InputStream inputStream = Ds3TypeMapperParserImpl.class.getResourceAsStream("/typeMap.json");
        return toMap(objectMapper.readValue(ByteStreams.toByteArray(inputStream), TypeMap.class));
    }

    @Override
    public Ds3TypeMapper getMap() throws ParserException, IOException {
        return this.ds3TypeMapper;
    }

    private static Ds3TypeMapper toMap(final TypeMap typeMap) {
        final Ds3TypeMapper ds3TypeMapper = new Ds3TypeMapper(typeMap.getTypeMap());
        return ds3TypeMapper;
    }
}
