package com.spectralogic.ds3autogen.api;

import java.io.InputStream;

public interface Ds3SpecParser {
    Ds3ApiSpec getSpec(final InputStream stream);
}
