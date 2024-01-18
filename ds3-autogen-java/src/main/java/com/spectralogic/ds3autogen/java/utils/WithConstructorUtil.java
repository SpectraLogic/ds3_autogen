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

package com.spectralogic.ds3autogen.java.utils;

import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.utils.Helper;

import static com.spectralogic.ds3autogen.java.helpers.JavaHelper.argToString;
import static com.spectralogic.ds3autogen.java.helpers.JavaHelper.getType;
import static com.spectralogic.ds3autogen.java.helpers.JavaHelper.paramAssignmentRHS;
import static com.spectralogic.ds3autogen.utils.Helper.*;

/**
 * Common utils for creating with-constructors
 */
public final class WithConstructorUtil {

    /***
     * Creates the Java code for the first line of a with constructor.
     * Example: public MyRequestName withMyOptionalParameter(final MyArgType myArg) {
     */
    public static String withConstructorFirstLine(final Arguments arg, final String requestName) {
        return indent(1) + "public " + requestName + " with" + capFirst(arg.getName()) +
                "(final " + getType(arg) + " " + uncapFirst(arg.getInternalName()) + ") {\n";
    }

    /**
     * Creates the Java code for assigning a class variable to a function
     * parameter of the same name.
     * Example: this.myVariable = myVariable;
     */
    public static String argAssignmentLine(final Arguments arg) {
        return "this." + uncapFirst(arg.getInternalName()) + " = " + paramAssignmentRHS(arg) + ";\n";
    }

    /**
     * Creates the Java code for updating the query param list.
     * Example: this.updateQueryParam("myArg", MyArgType.toString());
     */
    public static String updateQueryParamLine(final String name, final String type) {
        return "this.updateQueryParam(\"" + camelToUnderscore(name) + "\", " + type + ");\n";
    }

    /**
     * Creates the Java code for putting a query param to the query params list.
     * Example: this.getQueryParams().put("myArg", MyArgType.toString());
     */
    public static String putQueryParamLine(final Arguments arg) {
        return putQueryParamLine(arg.getName(), queryParamArgToString(arg));
    }

    /**
     * Creates the Java code for putting a query param to the query params list.
     * Example: this.getQueryParams().put("myArg", MyArgType.toString());
     */
    public static String putQueryParamLine(final String name, final String type) {
        final StringBuilder builder = new StringBuilder();
        builder.append("this.getQueryParams().put(\"");
        if (name.equalsIgnoreCase("BucketName")) {
            builder.append("bucket_id");
        } else {
            builder.append(Helper.camelToUnderscore(name));
        }
        builder.append("\", ")
                .append(type)
                .append(");");
        return builder.toString();
    }

    /**
     * Creates the Java code for converting an Argument within a query param line.
     * If the argument is Delimiter, then it is not escaped. If the argument is
     * of type String, then it is escaped. All other arguments return a string
     * containing the Java code for converting said argument to a String.
     */
    protected static String queryParamArgToString(final Arguments arg) {
        if (arg.getName().equalsIgnoreCase("Delimiter")
                || arg.getName().equalsIgnoreCase("BucketId")
                || arg.getName().equalsIgnoreCase("BucketName")) {
            return uncapFirst(arg.getName());
        }
        return argToString(arg);
    }

    /**
     * Creates the Java code for removing a query param from the query params list.
     * Example: this.getQueryParams().remove(\"myArg\");
     */
    public static String removeQueryParamLine(final String name) {
        return "this.getQueryParams().remove(\"" + Helper.camelToUnderscore(name) + "\");\n";
    }
}
