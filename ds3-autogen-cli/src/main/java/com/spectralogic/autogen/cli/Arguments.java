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

package com.spectralogic.autogen.cli;


public class Arguments {
    private final String targetDir;
    private final GeneratorType type;
    private final String inputSpec;
    private final boolean help;
    private final boolean generateInternal;
    private final boolean noDoc;

    public Arguments(
            final String targetDir,
            final GeneratorType type,
            final String inputSpec,
            final boolean help,
            final boolean generateInternal,
            final boolean noDoc) {
        this.targetDir = targetDir;
        this.type = type;
        this.help = help;
        this.inputSpec = inputSpec;
        this.generateInternal = generateInternal;
        this.noDoc = noDoc;
    }

    public String getTargetDir() {
        return targetDir;
    }

    public GeneratorType getType() {
        return type;
    }

    public boolean isHelp() {
        return help;
    }

    public String getInputSpec() {
        return inputSpec;
    }

    public boolean generateInternal() {
        return generateInternal;
    }

    public boolean isNoDoc() {
        return noDoc;
    }
}
