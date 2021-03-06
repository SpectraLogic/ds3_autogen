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

package com.spectralogic.ds3autogen.go.generators.client.command

import com.spectralogic.ds3autogen.go.models.client.CustomBuildLine
import com.spectralogic.ds3autogen.go.models.client.ReaderBuildLine
import com.spectralogic.ds3autogen.go.models.client.RequestBuildLine
import org.junit.Test


import org.assertj.core.api.Assertions.assertThat

class PutObjectCommandGeneratorTest {

    private val generator = PutObjectCommandGenerator()

    @Test
    fun toHeadersBuildLineTest() {
        assertThat<RequestBuildLine>(generator.toHeadersBuildLine())
                .isNotEmpty
                .containsSame(CustomBuildLine.METADATA_BUILD_LINE)
    }

    @Test
    fun toChecksumBuildLineTest() {
        assertThat<RequestBuildLine>(generator.toChecksumBuildLine())
                .isNotEmpty
                .containsSame(CustomBuildLine.CHECKSUM_BUILD_LINE)
    }

    @Test
    fun toReaderBuildLineTest() {
        assertThat<RequestBuildLine>(generator.toReaderBuildLine())
                .isNotEmpty
                .contains(ReaderBuildLine("request.Content"))
    }
}
