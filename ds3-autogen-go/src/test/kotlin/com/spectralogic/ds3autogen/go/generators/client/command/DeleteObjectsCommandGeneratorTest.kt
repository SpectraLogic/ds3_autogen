package com.spectralogic.ds3autogen.go.generators.client.command

import com.spectralogic.ds3autogen.go.models.client.ReadCloserBuildLine
import com.spectralogic.ds3autogen.go.models.client.RequestBuildLine
import org.assertj.core.api.Assertions
import org.junit.Test

class DeleteObjectsCommandGeneratorTest {

    private val generator = DeleteObjectsCommandGenerator()

    @Test
    fun toReaderBuildLineTest() {
        Assertions.assertThat<RequestBuildLine>(generator.toReaderBuildLine())
                .isNotEmpty
                .contains(ReadCloserBuildLine("buildDeleteObjectsPayload(request.ObjectNames)"))
    }
}