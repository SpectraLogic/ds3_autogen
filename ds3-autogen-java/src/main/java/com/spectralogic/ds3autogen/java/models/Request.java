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

package com.spectralogic.ds3autogen.java.models;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Action;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.HttpVerb;
import com.spectralogic.ds3autogen.api.models.Operation;
import com.spectralogic.ds3autogen.java.helpers.JavaHelper;
import com.spectralogic.ds3autogen.utils.Helper;

public class Request {
    private final String packageName;
    private final String name;
    private final HttpVerb verb;
    private final String path;
    private final Operation operation;
    private final Action action;

    /** List of optional arguments: used to create with-constructors */
    private final ImmutableList<Arguments> optionalArguments;

    /** List of constructor models */
    private final ImmutableList<RequestConstructor> constructors;

    /** List of variables defined within the class; also used to create getters */
    private final ImmutableList<Variable> classVariables;

    /** List of class imports required for compilation */
    private final ImmutableList<String> imports;

    private final Helper helper;
    private final JavaHelper javaHelper;

    public Request(final String packageName,
                   final String name,
                   final HttpVerb verb,
                   final String path,
                   final Operation operation,
                   final Action action,
                   final ImmutableList<Arguments> optionalArguments,
                   final ImmutableList<RequestConstructor> constructors,
                   final ImmutableList<Variable> classVariables,
                   final ImmutableList<String> imports) {
        this.packageName = packageName;
        this.name = name;
        this.verb = verb;
        this.path = path;
        this.operation = operation;
        this.action = action;
        this.optionalArguments = optionalArguments;
        this.constructors = constructors;
        this.classVariables = classVariables;
        this.imports = imports;

        this.helper = Helper.getInstance();
        this.javaHelper = JavaHelper.getInstance();
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public HttpVerb getVerb() {
        return verb;
    }

    public String getPath() {
        return path;
    }

    public ImmutableList<Arguments> getOptionalArguments() {
        return optionalArguments;
    }

    public ImmutableList<String> getImports() {
        return imports;
    }

    public Helper getHelper() {
        return helper;
    }

    public Operation getOperation() { return operation; }

    public Action getAction() {
        return action;
    }

    public JavaHelper getJavaHelper() { return javaHelper; }

    public ImmutableList<Variable> getClassVariables() {
        return classVariables;
    }

    public ImmutableList<RequestConstructor> getConstructors() {
        return constructors;
    }
}
