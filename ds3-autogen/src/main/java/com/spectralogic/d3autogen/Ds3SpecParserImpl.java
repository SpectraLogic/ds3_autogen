package com.spectralogic.d3autogen;

import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.spectralogic.d3autogen.models.xml.RawSpec;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.Ds3SpecParser;

import java.io.IOException;
import java.io.InputStream;

public class Ds3SpecParserImpl implements Ds3SpecParser {

    private final JacksonXmlModule module;
    private final XmlMapper mapper;

    public Ds3SpecParserImpl() {
        module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        mapper = new XmlMapper(module);
        final SimpleFilterProvider filterProvider = new SimpleFilterProvider().setFailOnUnknownId(false);
        mapper.setFilters(filterProvider);
    }

    @Override
    public Ds3ApiSpec getSpec(final InputStream stream) throws ParserException, IOException {
        return toSpec(mapper.readValue(stream, RawSpec.class));
    }

    private static Ds3ApiSpec toSpec(final RawSpec contract) {
        //TODO implement this
        return null;
    }
}
