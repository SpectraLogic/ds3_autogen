/*
 * ******************************************************************************
 *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.docspec;

import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.NameMapper;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.api.models.enums.Classification;
import com.spectralogic.ds3autogen.models.xml.docspec.ParamDescriptor;
import com.spectralogic.ds3autogen.models.xml.docspec.RawDocSpec;
import com.spectralogic.ds3autogen.models.xml.docspec.RequestDescriptor;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.spectralogic.ds3autogen.docspec.DocSpecConverter.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DocSpecConverter_Test {

    private static final String TEST_NAME_MAPPER_FILE = "/testTypeNameMap.json";

    private static final String AMAZON_NAME_BEFORE = "com.test.AmazonBeforeRequestHandler";
    private static final String AMAZON_NAME_AFTER = "AmazonAfterRequest";
    private static final String AMAZON_DESCRIPTOR = "This is how you use this amazon request";

    private static final String SPECTRA_NAME_BEFORE = "com.test.SpectraBeforeRequestHandler";
    private static final String SPECTRA_NAME_AFTER = "SpectraAfterSpectraS3Request";
    private static final String SPECTRA_DESCRIPTOR = "This is how you use this spectra request";

    private RequestDescriptor createSpectraRequestDescriptor() {
        final RequestDescriptor requestDescriptor = new RequestDescriptor();
        requestDescriptor.setName(SPECTRA_NAME_BEFORE);
        requestDescriptor.setClassification(Classification.spectrads3);
        requestDescriptor.setDescription(SPECTRA_DESCRIPTOR);
        return  requestDescriptor;
    }

    private RequestDescriptor createAmazonRequestDescriptor() {
        final RequestDescriptor requestDescriptor = new RequestDescriptor();
        requestDescriptor.setName(AMAZON_NAME_BEFORE);
        requestDescriptor.setClassification(Classification.amazons3);
        requestDescriptor.setDescription(AMAZON_DESCRIPTOR);
        return  requestDescriptor;
    }

    private ParamDescriptor createParamDescriptor(final String postfix) {
        final ParamDescriptor pd = new ParamDescriptor();
        pd.setName("Name" + postfix);
        pd.setDescription("Description" + postfix);
        return pd;
    }

    @Test
    public void normalizeRequestName_Spectra_Test() throws IOException, ParserException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);
        final String result = normalizeRequestName(createSpectraRequestDescriptor(), nameMapper);
        assertThat(result, is(SPECTRA_NAME_AFTER));
    }

    @Test
    public void normalizeRequestName_Amazon_Test() throws IOException, ParserException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);
        final String result = normalizeRequestName(createAmazonRequestDescriptor(), nameMapper);
        assertThat(result, is(AMAZON_NAME_AFTER));
    }

    @Test
    public void toRequestDocs_NullList_Test() {
        final ImmutableMap<String, String> result = toRequestDocs(null, null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toRequestDocs_EmptyList_Test() {
        final ImmutableMap<String, String> result = toRequestDocs(new ArrayList<>(), null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toRequestDocs_Test() throws IOException, ParserException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final List<RequestDescriptor> rds = Arrays.asList(
                createAmazonRequestDescriptor(),
                createSpectraRequestDescriptor());

        final ImmutableMap<String, String> result = toRequestDocs(rds, nameMapper);
        assertThat(result.size(), is(2));
        assertThat(result.get(AMAZON_NAME_AFTER), is(AMAZON_DESCRIPTOR));
        assertThat(result.get(SPECTRA_NAME_AFTER), is(SPECTRA_DESCRIPTOR));
    }

    @Test
    public void toParamDocs_NullList_Test() {
        final ImmutableMap<String, String> result = toParamDocs(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toParamDocs_EmptyList_Test() {
        final ImmutableMap<String, String> result = toParamDocs(new ArrayList<>());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toParamDocs_Test() {
        final List pds = Arrays.asList(
                createParamDescriptor("1"),
                createParamDescriptor("2"));

        final ImmutableMap<String, String> result = toParamDocs(pds);

        assertThat(result.size(), is(2));
        assertThat(result.get("Name1"), is("Description1"));
        assertThat(result.get("Name2"), is("Description2"));
    }

    @Test
    public void toDs3DocSpec_Test() throws IOException, ParserException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);
        final RawDocSpec rawDocSpec = new RawDocSpec();
        rawDocSpec.setRequestDescriptors(
                Arrays.asList(createAmazonRequestDescriptor(), createSpectraRequestDescriptor()));
        rawDocSpec.setParamDescriptors(
                Arrays.asList(createParamDescriptor("1"), createParamDescriptor("2")));

        final Ds3DocSpec result = toDs3DocSpec(rawDocSpec, nameMapper);
        assertThat(result.getCommandDocumentation(AMAZON_NAME_AFTER).get(), is(AMAZON_DESCRIPTOR));
        assertThat(result.getCommandDocumentation(SPECTRA_NAME_AFTER).get(), is(SPECTRA_DESCRIPTOR));
        assertThat(result.getParamDocumentation("Name1").get(), is("Description1"));
        assertThat(result.getParamDocumentation("Name2").get(), is("Description2"));
    }
}
