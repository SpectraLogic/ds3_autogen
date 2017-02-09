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

package com.spectralogic.ds3autogen.testutil.logging;

import org.slf4j.Logger;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Used to log generated code based on file type. This is used in unit tests
 * to control which files are logged for easier reading.
 */
public class GeneratedCodeLogger {

    /** Where the generated code should be logged */
    private final Logger LOG;
    /** The type of file that should be logged */
    private final FileTypeToLog fileTypeToLog;

    public GeneratedCodeLogger(final FileTypeToLog fileTypeToLog, final Logger LOG) {
        this.LOG = LOG;
        this.fileTypeToLog = fileTypeToLog;
    }

    /**
     * Logs the specified file if its file type matches the desired
     * type to be logged
     * @param code The generated code
     * @param fileType The file type of the generated code
     */
    public void logFile(final String code, final FileTypeToLog fileType) {
        if (fileTypeToLog == fileType || fileTypeToLog == FileTypeToLog.ALL) {
            LOG.info(getLogMessage(code));
        }
    }

    /**
     * Creates the message that will be logged, including the generated code
     */
    private String getLogMessage(final String code) {
        if (isEmpty(code)) {
            return "Generated code: <EMPTY>";
        }
        return "Generated code:\n" + code;
    }
}
