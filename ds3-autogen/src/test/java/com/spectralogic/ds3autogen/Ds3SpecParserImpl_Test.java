package com.spectralogic.ds3autogen;

import com.spectralogic.d3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.Ds3SpecParser;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class Ds3SpecParserImpl_Test {

    @Test
    public void SingleRequestHandler() throws IOException, ParserException {
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(Ds3SpecParserImpl_Test.class.getResourceAsStream("/specs/singleRequestHandler.xml"));
        assertThat(spec, is(notNullValue()));
        assertThat(spec.getRequests(), is(notNullValue()));
    }
}
