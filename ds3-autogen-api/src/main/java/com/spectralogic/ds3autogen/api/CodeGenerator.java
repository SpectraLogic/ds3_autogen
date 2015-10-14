package com.spectralogic.ds3autogen.api;

import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;

import java.nio.file.Path;

public interface CodeGenerator {
    void generate(final Ds3ApiSpec spec,
                  final FileUtils fileUtils,
                  final Path destDir);
}
