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

package com.spectralogic.ds3autogen.java.generators.requestmodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecEmptyImpl;
import com.spectralogic.ds3autogen.java.models.RequestConstructor;
import com.spectralogic.ds3autogen.java.models.Variable;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getCreateMultiPartUploadPart;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StreamRequestPayloadGenerator_Test {

    private final StreamRequestPayloadGenerator generator = new StreamRequestPayloadGenerator();

    @Test
    public void toConstructorArgumentsList_Test() {
        final ImmutableList<Arguments> result = generator.toConstructorArgumentsList(getCreateMultiPartUploadPart());

        assertThat(result.size(), is(5));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(1).getName(), is("ObjectName"));
        assertThat(result.get(2).getName(), is("PartNumber"));
        assertThat(result.get(3).getName(), is("UploadId"));
        assertThat(result.get(4).getName(), is("Size"));
        assertThat(result.get(4).getType(), is("long"));
    }

    @Test
    public void toConstructorList_Test() {
        final ImmutableList<RequestConstructor> result = generator.toConstructorList(getCreateMultiPartUploadPart(), "", new Ds3DocSpecEmptyImpl());
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getParameters().size(), is(6));
        assertThat(result.get(0).getParameters().get(0).getName(), is("BucketName"));
        assertThat(result.get(0).getParameters().get(1).getName(), is("ObjectName"));
        assertThat(result.get(0).getParameters().get(2).getName(), is("PartNumber"));
        assertThat(result.get(0).getParameters().get(3).getName(), is("UploadId"));
        assertThat(result.get(0).getParameters().get(4).getName(), is("Size"));
        assertThat(result.get(0).getParameters().get(5).getName(), is("Channel"));

        assertThat(result.get(1).getParameters().size(), is(6));
        assertThat(result.get(1).getParameters().get(0).getName(), is("BucketName"));
        assertThat(result.get(1).getParameters().get(1).getName(), is("ObjectName"));
        assertThat(result.get(1).getParameters().get(2).getName(), is("PartNumber"));
        assertThat(result.get(1).getParameters().get(3).getName(), is("UploadId"));
        assertThat(result.get(1).getParameters().get(4).getName(), is("Size"));
        assertThat(result.get(1).getParameters().get(5).getName(), is("Stream"));
    }

    @Test
    public void getAllImports_Test() {
        final ImmutableList<String> result = generator.getAllImports(getCreateMultiPartUploadPart(), "com.test.package");
        assertThat(result, hasItem("com.spectralogic.ds3client.utils.SeekableByteChannelInputStream"));
        assertThat(result, hasItem("java.nio.channels.SeekableByteChannel"));
        assertThat(result, hasItem("java.io.InputStream"));
    }

    @Test
    public void toClassVariableArguments_Test() {
        final ImmutableList<Variable> result = generator.toClassVariableArguments(getCreateMultiPartUploadPart());
        assertThat(result.size(), is(7));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(1).getName(), is("ObjectName"));
        assertThat(result.get(2).getName(), is("PartNumber"));
        assertThat(result.get(3).getName(), is("UploadId"));
        assertThat(result.get(4).getName(), is("Size"));
        assertThat(result.get(5).getName(), is("Stream"));
        assertThat(result.get(6).getName(), is("Channel"));
    }
}
