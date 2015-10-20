package com.spectralogic.ds3autogen.c.utils;

import com.spectralogic.ds3autogen.api.FileUtils;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

public class TestFileUtilImpl implements FileUtils {

    private final static Logger LOG = LoggerFactory.getLogger(TestFileUtilImpl.class);

    private final TemporaryFolder tempFolder;

    public TestFileUtilImpl(final TemporaryFolder tempFolder) {
        this.tempFolder = tempFolder;
    }

    @Override
    public OutputStream getOutputFile(final Path path) throws IOException {

        final Path parentPath = path.getParent();

        final String[] pathParts = parentPath.toString().split("/|\\\\");
        try {
            tempFolder.newFolder(pathParts);
        } catch(final RuntimeException|IOException e) {
            LOG.error("Attempted to create folder that already exists -- ignoring", e);
        }

        return new FileOutputStream(tempFolder.newFile(path.toString()));
    }
}
