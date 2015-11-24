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

package com.spectralogic.ds3autogen;

import com.spectralogic.ds3autogen.api.FileUtils;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class FileUtilsImpl implements FileUtils {
    @Override
    public OutputStream getOutputFile(final Path path) throws IOException {
        createDirectories(path.getParent());
        return new BufferedOutputStream(
                Files.newOutputStream(path, CREATE, TRUNCATE_EXISTING));
    }

    private void createDirectories(final Path path) throws IOException {
        if (Files.exists(path)) {
            return;
        }
        Files.createDirectories(path);
    }
}
