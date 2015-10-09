package com.spectralogic.ds3autogen.api;

public interface CodeGenerator {
    void generate(final Ds3ApiSpec spec,
                  final FileUtils fileUtils,
                  final String destDir);
}
