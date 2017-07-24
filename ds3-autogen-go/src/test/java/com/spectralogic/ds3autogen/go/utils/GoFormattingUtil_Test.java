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

package com.spectralogic.ds3autogen.go.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GoFormattingUtil_Test {

    @Test
    public void goIndentTest() {
        assertThat(GoFormattingUtilKt.goIndent(-1), is(""));
        assertThat(GoFormattingUtilKt.goIndent(0), is(""));
        assertThat(GoFormattingUtilKt.goIndent(1), is("    "));
        assertThat(GoFormattingUtilKt.goIndent(2), is("        "));
        assertThat(GoFormattingUtilKt.goIndent(3), is("            "));
    }
}
