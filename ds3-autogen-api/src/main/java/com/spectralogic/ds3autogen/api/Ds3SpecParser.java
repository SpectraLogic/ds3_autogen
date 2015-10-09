package com.spectralogic.ds3autogen.api;

import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;

import java.io.IOException;
import java.io.InputStream;

public interface Ds3SpecParser {
    Ds3ApiSpec getSpec(final InputStream stream) throws ParserException, IOException;
}
