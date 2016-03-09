/*
 * ******************************************************************************
 *   Copyright 2014-2015 Spectra Logic Corporation. All Rights Reserved.
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

import org.apache.commons.cli.*;

public class CLI {

    final private Options options;

    private CLI() {
        this.options = new Options();
        final Option language = new Option("l", true, "Select the language to generate");
        final Option directory = new Option("d", true, "Directory to write generated code to");
        final Option inputSpec = new Option("i", true, "The spec file for the DS3 API");
        final Option help = new Option("h", false, "Print usage");
        final Option spectraInternal = new Option("internal", false, "Generate Spectra Internal requests");
        options.addOption(language);
        options.addOption(directory);
        options.addOption(inputSpec);
        options.addOption(help);
        options.addOption(spectraInternal);
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

        final String directory = cmd.getOptionValue("d");
        final GeneratorType language = processLanguageArg(cmd);
        final String inputSpec = cmd.getOptionValue("i");
        final boolean help = cmd.hasOption("h");
        final boolean spectraInternal = cmd.hasOption("internal");

        final Arguments arguments = new Arguments(directory, language, inputSpec, help, spectraInternal);

        validateArguments(arguments);

        return arguments;
    }

    private GeneratorType processLanguageArg(final CommandLine cmd) {
        try {
            return Guards.returnIfNull(cmd.getOptionValue("l").toUpperCase(), GeneratorType::valueOf);
        } catch (final NullPointerException e) {
            return null;
        } catch (final Exception e) {
            throw new IllegalArgumentException(cmd.getOptionValue("l") + " is not a supported language");
        }
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
