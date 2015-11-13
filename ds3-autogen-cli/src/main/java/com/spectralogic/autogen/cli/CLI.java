package com.spectralogic.autogen.cli;

import org.apache.commons.cli.*;

public class CLI {

    final private Options options;

    private CLI() {
        this.options = new Options();
        final Option language = new Option("l", true, "Select the language to generate");
        final Option directory = new Option("d", true, "Directory to write generated code to");
        final Option inputSpec = new Option("i", true, "The spec file for the DS3 API");
        final Option help = new Option("h", false, "Print usage");
        options.addOption(language);
        options.addOption(directory);
        options.addOption(inputSpec);
        options.addOption(help);
    }

    public static Arguments getArguments(final String[] args) throws Exception {

        final CLI cli = new CLI();

        final Arguments arguments = cli.processArgs(args);

        if (arguments.isHelp()) {
            cli.printUsage();
            System.exit(0);
        }

        return arguments;
    }

    private Arguments processArgs(final String[] args) throws Exception {
        final CommandLineParser parser = new BasicParser();
        final CommandLine cmd = parser.parse(options, args);

        final Arguments arguments = new Arguments();

        if (cmd.hasOption("d")) {
            arguments.setTargetDir(cmd.getOptionValue("d"));
        }
        if (cmd.hasOption("l")) {
            arguments.setType(Guards.returnIfNull(cmd.getOptionValue("l").toUpperCase(), GeneratorType::valueOf));
        }
        if (cmd.hasOption("i")) {
            arguments.setInputSpec(cmd.getOptionValue("i"));
        }
        if (cmd.hasOption("h")) {
            arguments.setHelp(true);
        }

        validateArguments(arguments);

        return arguments;
    }

    private void validateArguments(final Arguments arguments) throws MissingArgumentException {
        if (arguments.isHelp()) return; // Nothing else to verify
        if (arguments.getTargetDir() == null) throw new MissingArgumentException("'-d' is a required argument");
        if (arguments.getType() == null) throw new MissingArgumentException("'-l' is a required argument");
        if (arguments.getInputSpec() == null) throw new MissingArgumentException("'-i' is a required argument");
    }

    private void printUsage() {
        final HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("ds3_generator", options);
    }
}
