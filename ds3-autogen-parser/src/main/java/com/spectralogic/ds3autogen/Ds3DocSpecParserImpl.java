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
import com.google.common.collect.ImmutableMap;
import com.google.common.io.ByteStreams;
import com.spectralogic.ds3autogen.api.Ds3DocSpecParser;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecImpl;
import com.spectralogic.ds3autogen.models.xml.docspec.RawDocSpec;

import java.io.IOException;
import java.io.InputStream;

import static com.spectralogic.ds3autogen.docspec.DocSpecConverter.toDs3DocSpec;

/**
 * Parses an input stream to create a Ds3DocSpec with normalized command names
 */
public class Ds3DocSpecParserImpl implements Ds3DocSpecParser {

    private static final String DEFAULT_DOC_SPEC_FILE = "/commandDocumentation.json";

    private static final ObjectMapper objectMapper = initDs3DocSpec();
    private final NameMapper nameMapper;

    /**
     * Creates a Ds3DocSpecParserImpl with the default NameMapper
     * see {@link NameMapper#DEFAULT_TYPE_NAME_MAP_FILE}
     */
    public Ds3DocSpecParserImpl() throws IOException, ParserException {
        this.nameMapper = new NameMapper();
    }

    public Ds3DocSpecParserImpl(final NameMapper nameMapper) {
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

    //TODO test
    /**
     * Creates a Ds3DocSpec with the default input file specified by {@link #DEFAULT_DOC_SPEC_FILE}
     */
    @Override
    public Ds3DocSpec getDocSpec() throws IOException {
        final InputStream inputStream = Ds3DocSpecParserImpl.class.getResourceAsStream(DEFAULT_DOC_SPEC_FILE);
        return getDocSpec(inputStream);
    }
}
