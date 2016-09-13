/*
 * ******************************************************************************
 *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.java.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Helper.indent;

/**
 * Used to convert optional strings into java documentation.
 * This is used to generated documentation for request handlers and client.
 */
public final class JavaDocGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(JavaDocGenerator.class);

    /**
     * Retrieves the Java documentation for the specified command.
     * This is used to generate documentation for the Ds3Client.
     * @param requestName The normalized SDK request name with no path
     * @param indent The level of indentation the documentation will be
     *               generated in, excluding the first line which does
     *               not have any indentation.
     */
    public static String toCommandDocs(
            final String requestName,
            final Ds3DocSpec docSpec,
            final int indent) {
        final Optional<String> documentation = docSpec.getRequestDocumentation(requestName);
        if (!documentation.isPresent()) {
            LOG.info("Cannot generate java documentation for request because there is no documentation descriptor: {}", requestName);
            return "";
        }
        return "/**\n"
                + indent(indent) + " * " + documentation.get() + "\n"
                + indent(indent) + " */";
    }

    /**
     * Retrieves the Java documentation for the specified request constructor.
     * This is used in java request handler generation.
     * @param requestName The normalized SDK request name with no path
     * @param params List of parameters for the constructor.
     * @param indent The level of indentation the documentation will be
     *               generated in, excluding the first line which does
     *               not have any indentation.
     */
    public static String toConstructorDocs(
            final String requestName,
            final ImmutableList<String> params,
            final Ds3DocSpec docSpec,
            final int indent) {
        final Optional<String> requestDoc = docSpec.getRequestDocumentation(requestName);
        if (!requestDoc.isPresent()) {
            LOG.info("Cannot generate java documentation for request constructor because there is no descriptor for: {}", requestName);
            return "";
        }
        return "/**\n"
                + indent(indent) + " * " + requestDoc.get() + "\n"
                + toParamListDocs(params, docSpec, indent)
                + indent(indent) + " */\n";
    }

    /**
     * Generates the javadoc for a list of parameters
     */
    protected static String toParamListDocs(
            final ImmutableList<String> params,
            final Ds3DocSpec docSpec,
            final int indent) {
        if (isEmpty(params)) {
            return "";
        }
        return params.stream()
                .map(param -> toParamDocs(param, docSpec, indent))
                .collect(Collectors.joining());
    }

    /**
     * Generates the javadoc for a parameter
     */
    protected static String toParamDocs(
            final String paramName,
            final Ds3DocSpec docSpec,
            final int indent) {
        return indent(indent) + " * @param " + paramName + " "
                + docSpec.getParamDocumentation(paramName).orElse("") + "\n";
    }


}
