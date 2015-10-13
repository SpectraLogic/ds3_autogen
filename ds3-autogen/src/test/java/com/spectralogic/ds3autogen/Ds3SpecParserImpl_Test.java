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

import com.spectralogic.d3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.Ds3SpecParser;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class Ds3SpecParserImpl_Test {

    @Test
    public void SingleRequestHandler() throws IOException, ParserException {
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(Ds3SpecParserImpl_Test.class.getResourceAsStream("/specs/singleRequestHandler.xml"));
        assertThat(spec, is(notNullValue()));
        assertThat(spec.getRequests().size(), is(1));
        assertThat(spec.getRequests().get(0).getName(), is("com.spectralogic.s3.server.handler.reqhandler.amazons3.GetObjectRequestHandler"));
    }

    @Test
    public void twoRequestHandlers() throws IOException, ParserException {
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(Ds3SpecParserImpl_Test.class.getResourceAsStream("/specs/twoRequestHandlers.xml"));
        assertThat(spec, is(notNullValue()));
        assertThat(spec.getRequests(), is(notNullValue()));
        assertThat(spec.getRequests().size(), is(2));
        assertThat(spec.getRequests().get(0).getName(), is("com.spectralogic.s3.server.handler.reqhandler.amazons3.GetObjectRequestHandler"));
        assertThat(spec.getRequests().get(1).getName(), is("com.spectralogic.s3.server.handler.reqhandler.amazons3.AbortMultiPartUploadRequestHandler"));
    }
}
