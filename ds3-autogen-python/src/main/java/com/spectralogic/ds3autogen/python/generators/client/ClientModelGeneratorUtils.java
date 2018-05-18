package com.spectralogic.ds3autogen.python.generators.client;

public interface ClientModelGeneratorUtils {

    /** Gets a string containing a comma separated list of the command's function parameters */
    String getFunctionParameters();

    /** Gets a string containing a comma separated list of parameters passed to the response constructor */
    String getResponseParameters();
}
