/*
 * ******************************************************************************
 *   Copyright 2014-2017 Spectra Logic Corporation. All Rights Reserved.
 *   Licensed under the Apache License, Version 2.0 (the "License"). You may not use
 *   this file except in compliance with the License. A copy of the License is located at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   or in the "license" file accompanying this file.
 *   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 *   CONDITIONS OF ANY KIND, either express or implied. See the License for the
 *   specific language governing permissions and limitations under the License.
 * ****************************************************************************
 */

package com.spectralogic.autogen.cli;

import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecEmptyImpl;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecParserImpl;
import com.spectralogic.ds3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.NameMapper;
import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.Ds3DocSpecParser;
import com.spectralogic.ds3autogen.api.Ds3SpecParser;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ApiSpec;
import com.spectralogic.ds3autogen.c.CCodeGenerator;
import com.spectralogic.ds3autogen.java.JavaCodeGenerator;
import com.spectralogic.ds3autogen.net.NetCodeGenerator;
import com.spectralogic.ds3autogen.python.PythonCodeGenerator;
import com.spectralogic.ds3autogen.python3.Python3CodeGenerator;
import com.spectralogic.ds3autogen.utils.FileUtilsImpl;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(final String[] args) {

        try {
            final Arguments arguments = getArgs(args);
            final Main main = new Main(arguments);

            runMain(main);

        } catch(final Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static Arguments getArgs(final String[] args) throws Exception {
        try {
            return CLI.getArguments(args);
        } catch (final Exception e) {
            throw new Exception("Encountered an error when parsing arguments", e);
        }
    }

    private static void runMain(final Main main) throws Exception {
        try {
            main.run();
        } catch(final Exception e) {
            throw new Exception("Encountered an error when generating code", e);
        }
    }

    private final Arguments args;

    private Main(final Arguments args) {
        this.args = args;
    }

    public void run() throws Exception {
        final Ds3SpecParser parser = new Ds3SpecParserImpl();

        System.out.println("Generating " + args.getType().toString() + " ds3 sdk code for the spec " + args.getInputSpec() );

        //TODO make the input file also accept a url that we can read from

        final Ds3ApiSpec spec = parser.getSpec(
                Files.newInputStream(Paths.get(args.getInputSpec())),
                args.generateInternal());

        final CodeGenerator generator;
        switch (args.getType()) {
            case C:
                generator = new CCodeGenerator();
                break;
            case JAVA:
                generator = new JavaCodeGenerator();
                break;
            case NET:
                generator = new NetCodeGenerator();
                break;
            case PYTHON:
                generator = new PythonCodeGenerator();
                break;
            case PYTHON3:
                generator = new Python3CodeGenerator();
                break;
            default:
                throw new IllegalArgumentException("Unknown generator typeName " + args.getType().toString());
        }
        final FileUtils fileUtils = new FileUtilsImpl();


        final Ds3DocSpec docSpec;
        if (args.isNoDoc()) {
            docSpec = new Ds3DocSpecEmptyImpl();
        } else {
            final Ds3DocSpecParser docSpecParser = new Ds3DocSpecParserImpl(new NameMapper());
            docSpec = docSpecParser.getDocSpec();
        }

        generator.generate(spec, fileUtils, Paths.get(args.getTargetDir()), docSpec);
    }
}
