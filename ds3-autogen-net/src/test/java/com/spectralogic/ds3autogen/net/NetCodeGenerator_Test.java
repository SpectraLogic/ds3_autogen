package com.spectralogic.ds3autogen.net;

import com.spectralogic.d3autogen.Ds3SpecParserImpl;
import com.spectralogic.d3autogen.Ds3TypeMapperParserImpl;
import com.spectralogic.ds3autogen.api.*;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.Ds3TypeMapper;
import com.spectralogic.ds3autogen.net.utils.TestHelper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NetCodeGenerator_Test {

    private final static Logger LOG = LoggerFactory.getLogger(NetCodeGenerator_Test.class);

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void simpleRequest() throws IOException, ParserException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final Path requestPath = Paths.get("./Ds3/Calls/GetObjectRequestHandler.cs");
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);

        when(fileUtils.getOutputFile(requestPath)).thenReturn(outputStream);

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(NetCodeGenerator_Test.class.getResourceAsStream("/input/singleRequestHandler.xml"));
        final CodeGenerator codeGenerator = new NetCodeGenerator();

        final Ds3TypeMapperParser typeParser = new Ds3TypeMapperParserImpl();
        final Ds3TypeMapper typeMapper = typeParser.getMap();

        codeGenerator.generate(spec, typeMapper, fileUtils, Paths.get("."));

        final String generatedCode = new String(outputStream.toByteArray());
        LOG.info("Generated code:\n" + generatedCode);

        assertTrue(TestHelper.extendsClass("GetObjectRequestHandler", "Ds3Request", generatedCode));

    }
}
