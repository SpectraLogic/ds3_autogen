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

import com.spectralogic.ds3autogen.api.Ds3SpecParser;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.ResponseTypeNotFoundException;
import com.spectralogic.ds3autogen.api.TypeRenamingConflictException;
import com.spectralogic.ds3autogen.api.models.*;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class Ds3SpecParserImpl_Test {

    @Test
    public void SingleRequestHandler() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException {
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(Ds3SpecParserImpl_Test.class.getResourceAsStream("/specs/singleRequestHandler.xml"));
        assertThat(spec, is(notNullValue()));
        assertThat(spec.getRequests().size(), is(1));

        final Ds3Request ds3Request = spec.getRequests().get(0);
        assertThat(ds3Request.getName(), is("com.spectralogic.s3.server.handler.reqhandler.amazons3.GetObjectRequest"));
        assertThat(ds3Request.getHttpVerb(), is(HttpVerb.GET));
        assertThat(ds3Request.getClassification(), is(Classification.amazons3));
        assertThat(ds3Request.getBucketRequirement(), is(Requirement.REQUIRED));
        assertThat(ds3Request.getObjectRequirement(), is(Requirement.REQUIRED));
        assertThat(ds3Request.getAction(), is(nullValue()));
        assertThat(ds3Request.getResource(), is(nullValue()));
        assertThat(ds3Request.getResourceType(), is(nullValue()));
        assertThat(ds3Request.getOperation(), is(nullValue()));

        assertThat(ds3Request.getDs3ResponseCodes().size(), is(1));
        assertThat(ds3Request.getDs3ResponseCodes().get(0).getCode(), is(200));
        assertThat(ds3Request.getDs3ResponseCodes().get(0).getDs3ResponseTypes().size(), is(1));
        assertThat(ds3Request.getDs3ResponseCodes().get(0).getDs3ResponseTypes().get(0).getType(), is("java.lang.String"));
        assertThat(ds3Request.getDs3ResponseCodes().get(0).getDs3ResponseTypes().get(0).getComponentType(), is(nullValue()));

        assertThat(ds3Request.getOptionalQueryParams().size(), is(0));
        assertThat(ds3Request.getRequiredQueryParams().size(), is(1));
        assertThat(ds3Request.getRequiredQueryParams().get(0).getName(), is("Id"));
        assertThat(ds3Request.getRequiredQueryParams().get(0).getType(), is("java.util.UUID"));
    }

    @Test
    public void twoRequestHandlers() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException {
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(Ds3SpecParserImpl_Test.class.getResourceAsStream("/specs/twoRequestHandlers.xml"));
        assertThat(spec, is(notNullValue()));
        assertThat(spec.getRequests(), is(notNullValue()));
        assertThat(spec.getRequests().size(), is(2));
        assertThat(spec.getRequests().get(0).getName(), is("com.spectralogic.s3.server.handler.reqhandler.amazons3.GetObjectRequest"));
        assertThat(spec.getRequests().get(1).getName(), is("com.spectralogic.s3.server.handler.reqhandler.amazons3.AbortMultiPartUploadRequest"));
    }

    @Test
    public void twoRequestHandlersAndOneType() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException {
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(Ds3SpecParserImpl_Test.class.getResourceAsStream("/specs/twoRequestHandlersAndOneType.xml"));
        assertThat(spec, is(notNullValue()));
        assertThat(spec.getTypes(), is(notNullValue()));
        assertThat(spec.getTypes().size(), is(1));
        assertThat(spec.getTypes().containsKey("com.spectralogic.s3.common.dao.domain.ds3.Priority"), is(true));

        final Ds3Type ds3Type = spec.getTypes().get("com.spectralogic.s3.common.dao.domain.ds3.Priority");
        assertThat(ds3Type, is(notNullValue()));
        assertThat(ds3Type.getName(), is("com.spectralogic.s3.common.dao.domain.ds3.Priority"));

        assertThat(ds3Type.getElements(), is(notNullValue()));
        assertThat(ds3Type.getElements().size(), is(1));
        assertThat(ds3Type.getElements().get(0).getName(), is("SpecifiableByUser"));
        assertThat(ds3Type.getElements().get(0).getType(), is("boolean"));
        assertThat(ds3Type.getElements().get(0).getComponentType(), is(nullValue()));
        assertThat(ds3Type.getElements().get(0).getDs3Annotations().size(), is(0));

        assertThat(ds3Type.getEnumConstants(), is(notNullValue()));
        assertThat(ds3Type.getEnumConstants().size(), is(6));
        assertThat(ds3Type.getEnumConstants().get(0).getName(), is("CRITICAL"));
        assertThat(ds3Type.getEnumConstants().get(0).getDs3Properties(), is(notNullValue()));
        assertThat(ds3Type.getEnumConstants().get(0).getDs3Properties().get(0).getName(), is("SpecifiableByUser"));
        assertThat(ds3Type.getEnumConstants().get(0).getDs3Properties().get(0).getValue(), is("false"));
        assertThat(ds3Type.getEnumConstants().get(0).getDs3Properties().get(0).getValueType(), is("boolean"));
    }

    @Test
    public void twoRequestHandlersAndTwoTypes() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException {
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(Ds3SpecParserImpl_Test.class.getResourceAsStream("/specs/twoRequestHandlersAndTwoTypes.xml"));
        assertThat(spec, is(notNullValue()));
        assertThat(spec.getTypes(), is(notNullValue()));
        assertThat(spec.getTypes().size(), is(2));
        assertThat(spec.getTypes().containsKey("com.spectralogic.s3.common.dao.domain.ds3.Priority"), is(true));
        assertThat(spec.getTypes().containsKey("com.spectralogic.s3.common.dao.domain.tape.TapeFailure"), is(true));
        assertThat(spec.getTypes().get("com.spectralogic.s3.common.dao.domain.ds3.Priority"), is(notNullValue()));
        assertThat(spec.getTypes().get("com.spectralogic.s3.common.dao.domain.tape.TapeFailure"), is(notNullValue()));
    }

    @Test
    public void fullXml() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException {
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(Ds3SpecParserImpl_Test.class.getResourceAsStream("/specs/fullXml.xml"));
        assertThat(spec, is(notNullValue()));
        assertThat(spec.getRequests(), is(notNullValue()));
        assertThat(spec.getTypes(), is(notNullValue()));
    }
}
