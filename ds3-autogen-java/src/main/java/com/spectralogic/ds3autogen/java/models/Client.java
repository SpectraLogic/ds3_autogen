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
import com.spectralogic.ds3autogen.java.helpers.JavaHelper;

public class Client {

    private final String packageName;
    private final ImmutableList<Command> commands;
    private final ImmutableList<CustomCommand> customCommands;
    private final JavaHelper javaHelper = JavaHelper.getInstance();

    public Client(
            final String packageName,
            final ImmutableList<Command> commands,
            final ImmutableList<CustomCommand> customCommands) {
        this.packageName = packageName;
        this.commands = commands;
        this.customCommands = customCommands;
    }

    public ImmutableList<Command> getCommands() {
        return commands;
    }

    public String getPackageName() {
        return packageName;
    }

    public ImmutableList<CustomCommand> getCustomCommands() {
        return customCommands;
    }

    public JavaHelper getJavaHelper() {
        return javaHelper;
    }
}
