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

package com.spectralogic.ds3autogen.python.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.HttpVerb;
import com.spectralogic.ds3autogen.api.models.Operation;
import com.spectralogic.ds3autogen.python.model.type.TypeContent;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.spectralogic.ds3autogen.utils.ClientGeneratorUtil.toCommandName;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Helper.camelToUnderscore;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.toResponseName;
import static org.junit.Assert.assertTrue;

/**
 * Contains utilities for testing the python generated code
 */
public class FunctionalTestHelper {

    /**
     * Determines if the code contains the specified request handler without request payload
     */
    public static void hasRequestHandler(
            final String requestName,
            final HttpVerb httpVerb,
            final ImmutableList<String> reqArgs,
            final ImmutableList<String> optArgs,
            final ImmutableList<String> voidArgs,
            final String code) {
        hasRequestHandler(requestName, httpVerb, reqArgs, optArgs, voidArgs, null, code);
    }

    /**
     * Determines if the code contains the specified request handler with request payload
     */
    public static void hasRequestHandler(
            final String requestName,
            final HttpVerb httpVerb,
            final ImmutableList<String> reqArgs,
            final ImmutableList<String> optArgs,
            final ImmutableList<String> voidArgs,
            final String requestPayload,
            final String code) {
        assertTrue(hasRequestHandlerDef(requestName, code));
        assertTrue(hasRequestHandlerInit(reqArgs, optArgs, requestPayload, code));
        assertTrue(hasHttpVerb(httpVerb, code));
        if (hasContent(reqArgs)) {
            for (final String arg : reqArgs) {
                assertTrue(requestHasReqParam(arg, code));
            }
        }
        if (hasContent(optArgs)) {
            for (final String arg : optArgs) {
                assertTrue(requestHasOptParam(arg, code));
            }
        }
        if (hasContent(voidArgs)) {
            for (final String arg : voidArgs) {
                assertTrue(requestHasVoidParam(arg, code));
            }
        }
    }

    /**
     * Determines if the code contains the specified HttpVerb
     */
    private static boolean hasHttpVerb(final HttpVerb httpVerb, final String code) {
        final String search = "self.http_verb = HttpVerb." + httpVerb.toString();
        return code.contains(search);
    }

    /**
     * Determines if the code contains the void argument's query param assignment
     */
    private static boolean requestHasVoidParam(final String paramName, final String code) {
        final String search = "self.query_params['" + paramName + "'] = ''";
        return code.contains(search);
    }

    /**
     * Determines if the code contains the optional query parameter assignment
     */
    private static boolean  requestHasOptParam(final String paramName, final String code) {
        final Pattern search = Pattern.compile(
                "\\s+if " + paramName + " is not None:"
                + "\\s+self\\.query_params\\['" + paramName + "'\\] = " + paramName,
                Pattern.MULTILINE | Pattern.UNIX_LINES);
        return search.matcher(code).find();
    }

    /**
     * Determines if the code contains the required parameter assignment
     */
    private static boolean requestHasReqParam(final String paramName, final String code) {
        final String search = "self." + paramName + " = " + paramName;
        return code.contains(search);
    }

    /**
     * Determines if the code contains the python request handler definition
     */
    private static boolean hasRequestHandlerDef(
            final String requestName,
            final String code) {
        final String search = "class " + requestName + "(AbstractRequest):";
        return code.contains(search);
    }

    /**
     * Determines if the code contains the python request handler init line
     */
    private static boolean hasRequestHandlerInit(
            final ImmutableList<String> reqArgs,
            final ImmutableList<String> optArgs,
            final String requestPayload,
            final String code) {
        final StringBuilder search = new StringBuilder();
        search.append("def __init__(self");
        if (hasContent(reqArgs)) {
            final String reqArgString = reqArgs.stream()
                    .collect(Collectors.joining(", "));
            search.append(", ").append(reqArgString);
        }
        if (hasContent(requestPayload)) {
            search.append(", ").append(requestPayload);
        }
        if (hasContent(optArgs)) {
            final String optArgString = optArgs.stream()
                    .map(i -> i + "=None")
                    .collect(Collectors.joining(", "));
            search.append(", ").append(optArgString);
        }
        search.append("):");
        return code.contains(search.toString());
    }

    /**
     * Determines if the code contains the operation query param assignment
     */
    public static boolean hasOperation(final Operation operation, final String code) {
        final String search = "self.query_params['operation'] = '" + operation.toString().toLowerCase() + "'";
        return code.contains(search);
    }

    /**
     * Verifies that the code contains the described model
     */
    public static void hasModelDescriptor(
            final String modelName,
            final ImmutableList<TypeContent> typeContents,
            final String code) {
        assertTrue(hasModelDescriptorDefinition(modelName, code));
        hasModelContent(typeContents, code);
    }

    /**
     * Verifies that the code contains all of the model contents
     */
    private static void hasModelContent(final ImmutableList<TypeContent> contents, final String code) {
        if (isEmpty(contents)) {
            return;
        }
        for (final TypeContent attr : contents) {
            assertTrue(code.contains(attr.toPythonCode()));
        }
    }

    /**
     * Determines if the code contains the class definition for the model
     */
    private static boolean hasModelDescriptorDefinition(
            final String modelName,
            final String code) {
        final Pattern search = Pattern.compile(
                "class " + modelName + "\\(object\\):"
                + "\\s+def __init__\\(self\\):",
                Pattern.MULTILINE | Pattern.UNIX_LINES);
        return search.matcher(code).find();
    }

    /**
     * Determines if the code contains the client class with specified requests
     */
    public static void hasClient(final ImmutableList<String> requestNames, final String code) {
        assertTrue(hasClientClass(code));
        for (final String requestName : requestNames) {
            assertTrue(hasClientCommand(requestName, code));
        }
    }

    /**
     * Determines if the code contains the client command for the specified request
     */
    public static boolean hasClientCommand(final String requestName, final String code) {
        final Pattern search = Pattern.compile(
                "def " + camelToUnderscore(toCommandName(requestName)) + "\\(self, request\\):"
                + "\\s+return " + toResponseName(requestName)
                + "\\(self\\.net_client.get_response\\(request\\), request\\)",
                Pattern.MULTILINE | Pattern.UNIX_LINES);
        return search.matcher(code).find();
    }

    /**
     * Determines if the code contains the client class
     */
    private static boolean hasClientClass(final String code) {
        final Pattern search = Pattern.compile(
                "class Client\\(object\\):"
                + "\\s+def __init__\\(self, endpoint, credentials\\):"
                + "\\s+self\\.net_client = NetworkClient\\(endpoint, credentials\\)"
                + "\\s+def get_net_client\\(self\\):"
                + "\\s+return self\\.net_client",
                Pattern.MULTILINE | Pattern.UNIX_LINES);
        return search.matcher(code).find();
    }
}
