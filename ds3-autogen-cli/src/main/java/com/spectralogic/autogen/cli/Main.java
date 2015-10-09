package com.spectralogic.autogen.cli;

import com.spectralogic.d3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.api.Ds3SpecParser;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;

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

        final Ds3ApiSpec spec = parser.getSpec(Files.newInputStream(Paths.get(args.getInputSpec())));

    }
}
