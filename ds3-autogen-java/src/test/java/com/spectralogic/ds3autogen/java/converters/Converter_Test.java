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

package com.spectralogic.ds3autogen.java.converters;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class Converter_Test {

    @Test
    public void nameConverterStripHandlerFromName() {
        assertThat(NameConverter.stripHandlerFromName("com.spectralogic.ds3client.commands.GetBucketRequestHandler"),
                is("com.spectralogic.ds3client.commands.GetBucketRequest"));

        assertThat(NameConverter.stripHandlerFromName("com.spectralogic.ds3client.commands.GetHandlerBucketRequest"),
                is("com.spectralogic.ds3client.commands.GetHandlerBucketRequest"));

        assertThat(NameConverter.stripHandlerFromName(null), is(nullValue()));
        assertThat(NameConverter.stripHandlerFromName(""), is(nullValue()));
    }

    @Test
    public void clientConverterGetCommands() {
        assertTrue(ClientConverter.toCommandList(null).isEmpty());
        assertTrue(ClientConverter.toCommandList(ImmutableList.of()).isEmpty());
    }
}
