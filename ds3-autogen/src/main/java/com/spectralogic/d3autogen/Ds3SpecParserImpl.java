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

import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.collect.ImmutableList;
import com.spectralogic.d3autogen.models.xml.RawSpec;
import com.spectralogic.ds3autogen.api.Ds3SpecParser;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;

import java.io.IOException;
import java.io.InputStream;

public class Ds3SpecParserImpl implements Ds3SpecParser {

    private final JacksonXmlModule module;
    private final XmlMapper mapper;

    public Ds3SpecParserImpl() {
        module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        mapper = new XmlMapper(module);
        mapper.registerModule(new GuavaModule());
        final SimpleFilterProvider filterProvider = new SimpleFilterProvider().setFailOnUnknownId(false);
        mapper.setFilters(filterProvider);
    }

    @Override
    public Ds3ApiSpec getSpec(final InputStream stream) throws ParserException, IOException {
        return toSpec(mapper.readValue(stream, RawSpec.class));
    }

    private static Ds3ApiSpec toSpec(final RawSpec contract) {
        final Ds3ApiSpec ds3ApiSpec = new Ds3ApiSpec(contract.getContract().getDs3Requests(), null);
        return ds3ApiSpec;
    }
}
