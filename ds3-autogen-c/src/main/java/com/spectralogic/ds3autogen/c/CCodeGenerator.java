package com.spectralogic.ds3autogen.c;

import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.api.models.Classification;

import java.nio.file.Path;
import java.util.Map;

import java.io.StringWriter;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.MethodInvocationException;

public class CCodeGenerator implements CodeGenerator {
    @Override
    public void generate(final Ds3ApiSpec spec, final FileUtils fileUtils, final Path destDir) {

        Velocity.init();
        VelocityContext context = new VelocityContext();
        context.put( "name", new String("Velocity"));
        Template template = null;

        try {
            for (Ds3Request request : spec.getRequests()) {
                System.out.println("Generating Request[" + request.getName() + "]");
                if (request.getClassification() == Classification.amazons3) {
                    // use the amazons3 template
                } else if (request.getClassification() == Classification.spectrads3) {
                    // use the spectras3 template

                } else if (request.getClassification() == Classification.spectrainternal) /* && codeGenType != production */ {
                    // use the internal-request template
                }
            }

            for (Map.Entry<String, Ds3Type> typeEntry : spec.getTypes().entrySet()) {
                System.out.println("Generating Type[" + typeEntry.getKey() + "][" + typeEntry.getValue().getName() + "]");
            }
        } catch ( ResourceNotFoundException rnfe ) {
            // couldn't find the template
        } catch( ParseErrorException pee ) {
            // syntax error: problem parsing the template
        } catch( MethodInvocationException mie ) {
            // something invoked in the template
            // threw an exception
        } catch( Exception e ) {
            // Pokemon!
        }

        StringWriter sw = new StringWriter();

        template.merge( context, sw );
    }
}
