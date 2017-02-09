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

package com.spectralogic.ds3autogen.python.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.spectralogic.ds3autogen.python.helpers.PythonHelper.pythonIndent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Helper.capFirst;
import static com.spectralogic.ds3autogen.utils.Helper.underscoreToCamel;

/**
 * Used to convert optional strings into python documentation.
 * This is used to generate documentation for requests and the client.
 */
public final class PythonDocGeneratorUtil {

    private static final Logger LOG = LoggerFactory.getLogger(PythonDocGeneratorUtil.class);

    private static final String OPEN_CLOSE_DOC = "'''\n";

    private PythonDocGeneratorUtil() {
        //Pass
    }

    /**
     * Retrieves the python documentation for the specified request
     * @param requestName The normalized SDK request name with no path.
     * @param indent The level of indentation the documentation will be
     *               generated in, excluding the first line which does
     *               not have any indentation.
     */
    public static String toCommandDocs(
            final String requestName,
            final Ds3DocSpec docSpec,
            final int indent) {
        final Optional<String> descriptor = docSpec.getRequestDocumentation(requestName);
        if (!descriptor.isPresent()) {
            LOG.info("Cannot generate documentation for request because there is no documentation descriptor: {}", requestName);
            return "";
        }
        return OPEN_CLOSE_DOC
                + pythonIndent(indent) + descriptor.get() + "\n"
                + pythonIndent(indent) + OPEN_CLOSE_DOC;
    }

    /**
     * Retrieves the python documentation for the request constructor
     * @param requestName The normalized SDK request name with no path.
     * @param params List of parameters for this constructor.
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
            LOG.info("Cannot generate documentation for request constructor because there is no descriptor for: {}", requestName);
            return "";
        }
        return OPEN_CLOSE_DOC
                + pythonIndent(indent) + requestDoc.get() + "\n"
                + toParamListDocs(params, docSpec, indent)
                + pythonIndent(indent) + OPEN_CLOSE_DOC;
    }

    /**
     * Generates the python documentation for a list of parameters
     */
    protected static String toParamListDocs(
            final ImmutableList<String> params,
            final Ds3DocSpec docSpec,
            final int indent) {
        if (isEmpty(params)) {
            return "";
        }
        return params.stream()
                .map(name -> toParamDocs(
                        name,
                        docSpec.getParamDocumentation(capFirst(underscoreToCamel(name))).orElse(""),
                        indent))
                .collect(Collectors.joining());
    }

    /**
     * Generates the python documentation for a parameter
     */
    protected static String toParamDocs(
            final String paramName,
            final String descriptor,
            final int indent) {
        return pythonIndent(indent) + "`" + paramName + "` " + descriptor + "\n";
    }
}
