package com.spectralogic.ds3autogen.api;

import java.io.OutputStream;
import java.nio.file.Path;

public interface FileUtils {
    OutputStream getOutputFile(final Path path);
}
