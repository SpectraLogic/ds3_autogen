package com.spectralogic.ds3autogen.c;

import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.ResourceUtils;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.api.models.Classification;
import com.spectralogic.ds3autogen.c.descriptors.*;
import com.spectralogic.ds3autogen.c.models.*;

import java.nio.file.Path;
import java.util.Map;

import java.io.StringWriter;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.MethodInvocationException;

public class CCodeGenerator implements CodeGenerator {
    @Override
    public void generate(final Ds3ApiSpec spec, final FileUtils fileUtils, final Path destDir) {
        System.out.println("CCodeGenerator::generate");
        generateInitRequest(spec);
    }

    public void generateInitRequest(final Ds3ApiSpec spec) {
        VelocityEngine ve = new VelocityEngine();
        ve.init();

        Velocity.init();
        VelocityContext context = new VelocityContext();
        Template template = null;

        try {
            for (Ds3Request ds3Request : spec.getRequests()) {
                System.out.println("Generating Request[" + ds3Request.getName() + "]");
                if (ds3Request.getClassification() == Classification.amazons3) {
                    // use the amazons3 template
                    System.out.println("amazonS3 ds3Request");
                    final Request request = RequestDescriptor.toRequest(ds3Request);
                    context.put("request", request);

                    final String templateName = "./src/main/resources/templates/AmazonS3InitRequestHandler.tmplt";
                    System.out.println("Loading template " + templateName);
                    template = ve.getTemplate(templateName);
                    System.out.println("Template loaded!");
                } else if (ds3Request.getClassification() == Classification.spectrads3) {
                    // use the spectras3 template
                    System.out.println("amazonS3 request");

                } else if (ds3Request.getClassification() == Classification.spectrainternal) /* && codeGenType != production */ {
                    System.out.println("spectra internal request");
                }

                StringWriter sw = new StringWriter();

                if (sw != null) {
                    System.out.println("merging template");
                    template.merge(context, sw);
                }
                System.out.println(sw.toString());
                // write to file /ds3_c_sdk/ds3_init_$request.getName().c 
            }

            /*
            for (Map.Entry<String, Ds3Type> typeEntry : spec.getTypes().entrySet()) {
                System.out.println("Generating Type[" + typeEntry.getKey() + "][" + typeEntry.getValue().getName() + "]");
            }
            */
        } catch ( ResourceNotFoundException rnfe ) {
            // couldn't find the template
            System.out.println("Couldn't find template");
        } catch( ParseErrorException pee ) {
            // syntax error: problem parsing the template
            System.out.println("Problem parsing template");
        } catch( MethodInvocationException mie ) {
            // something invoked in the template
            // threw an exception
            System.out.println("Something invoked in the template threw an exception");
        } catch( Exception e ) {
            // Pokemon!
            System.out.println("!!!Pokemon exception!!!\n" + e);
        }


    }
}
