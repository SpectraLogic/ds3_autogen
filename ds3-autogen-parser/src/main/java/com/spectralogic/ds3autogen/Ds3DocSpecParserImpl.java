/*
 * ******************************************************************************
 *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
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
import com.spectralogic.ds3autogen.api.Ds3DocSpecParser;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.models.xml.docspec.RawDocSpec;

import java.io.IOException;
import java.io.InputStream;

import static com.spectralogic.ds3autogen.docspec.DocSpecConverter.toDs3DocSpec;

/**
 * Parses an input stream to create a Ds3DocSpec with normalized command names
 */
public class Ds3DocSpecParserImpl implements Ds3DocSpecParser {

    private final ObjectMapper objectMapper;
    private final NameMapper nameMapper;

    //TODO add additional constructor with no parameters that uses default name mapper once one is created
    public Ds3DocSpecParserImpl(final NameMapper nameMapper) {
        this.objectMapper = initDs3DocSpec();
        this.nameMapper = nameMapper;
    }

    private static ObjectMapper initDs3DocSpec() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new GuavaModule());
        return objectMapper;
    }

    @Override
    public Ds3DocSpec getDocSpec(final InputStream stream) throws IOException {
        return toDs3DocSpec(
                objectMapper.readValue(ByteStreams.toByteArray(stream), RawDocSpec.class),
                nameMapper);
    }
}
