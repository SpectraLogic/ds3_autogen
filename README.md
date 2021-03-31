# ds3_autogen

[![Build
Status](https://travis-ci.com/SpectraLogic/ds3_autogen.svg)](https://travis-ci.com/SpectraLogic/ds3_autogen)
[![Apache V2 License](http://img.shields.io/badge/license-Apache%20V2-blue.svg)](https://github.com/SpectraLogic/ds3_autogen/blob/master/LICENSE.md)

## Install and Run

To install and run the Autogen project from the project directory:

1. In the command line, `cd` to the main project directory `ds3_autogen` and install the cli module:
  * `./gradlew clean ds3-autogen-cli:install`

2. You can launch the cli using the following command. Here is an example of launching the help menu:
  * `./ds3-autogen-cli/build/install/ds3-autogen-cli/bin/ds3-autogen-cli -h`

If you want to run and generate the project cleanly in a separate file:

1. In the command line, `cd` to the main project directory `ds3_autogen` and create a tar
  * `./gradlew clean distTar`

2. `cd` to the desired folder and run:
  * `tar -xvf <path to autogen root folder>/ds3_autogen/ds3-autogen-cli/build/distributions/ds3-autogen-cli-1.0.0-SNAPSHOT.tar`

3. The Autogen project can then be run with the following command. Here is an example of launching the help menu:
  * `./ds3-autogen-cli-1.0.0-SNAPSHOT/bin/ds3-autogen-cli -h`

For specific instructions on how to generate and integrate each languages SDK files, see the individual module README:
* [C module](ds3-autogen-c/README.md)
* [Java module](ds3-autogen-java/README.md)
* [NET module](ds3-autogen-net/README.md)
* [Python module](ds3-autogen-python/README.md)
* [Python3 module](ds3-autogen-python3/README.md)
* [Go module](ds3-autogen-go/README.md)

## Arguments

The command line has 3 required arguments and 3 optional arguments.

### Required Arguments
* `-d` The directory where the generated code will  be written to. If the directory does not exist, it will be created.
* `-i` The name of the spec file for the DS3 API to be generated. Some versions of the spec can be found in the `contracts` folder.
* `-l` The programming language that will be generated. Options are `C`, `JAVA`, `NET`, `PYTHON`, and `PYTHON3`. The language is case insensitive.

### Optional Arguments
* `-h` Prints the proper usage. If this is option is specified, all other options are ignored and no code is generated.
* `-internal` Generates code for the Spectra Internal commands. The functionality of generated internal commands is not guaranteed.
* `--no-doc` Generates the commands excluding documentation. Documentation is generated based on the default `Ds3DocSpec`.

## Tests

To run the Autogen tests for a given language module, use the following command with `language` substituted for the
desired language module (c, java, net, python, python3 or go):
  * `./gradlew clean ds3-autogen-language:test`
