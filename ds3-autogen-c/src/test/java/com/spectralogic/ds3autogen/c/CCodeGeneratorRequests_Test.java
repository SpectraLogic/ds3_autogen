package com.spectralogic.ds3autogen.c;

import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.api.Ds3SpecParser;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.ResponseTypeNotFoundException;
import com.spectralogic.ds3autogen.api.TypeRenamingConflictException;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.c.models.Request;
import com.spectralogic.ds3autogen.c.models.Source;
import com.spectralogic.ds3autogen.utils.TestFileUtilsImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;

public class CCodeGeneratorRequests_Test {
    final static Logger LOG = LoggerFactory.getLogger(CCodeGenerator_Test.class);

    /************************* AMAZON S3 *****************************/

    @Test
    public void testGenerateAmazonS3DeleteRequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException {
        final String inputSpecFile = "/input/AmazonS3DeleteRequest.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = new Source(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getAllStructs(spec, ImmutableSet.of()), CCodeGenerator.getAllRequests(spec));
        for(final Request request : source.getRequests()) {
            LOG.debug("Request " + request.getName());
            for(final Arguments arg: request.getRequiredArguments()) {
                LOG.debug("Required arg " + arg.getName() + " : " + arg.getType());
            }
            for(final Arguments arg: request.getOptionalArguments()) {
                LOG.debug("Optional arg " + arg.getName() + " : " + arg.getType());
            }
        }

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "AmazonS3RequestHandler.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        //assertTrue(output.contains("ds3_request* s3_init_delete_bucket_request(const char* bucket_name) {"));
        //assertTrue(output.contains("    return (ds3_request*) _common_request_init(HTTP_DELETE, _build_path(\"/\", bucket_name, NULL));"));
        //assertTrue(output.contains("}"));
    }
}
