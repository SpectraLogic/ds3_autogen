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

package com.spectralogic.ds3autogen.go.generators.type;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Annotation;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3AnnotationElement;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element;
import com.spectralogic.ds3autogen.go.models.type.StructElement;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JobListGenerator_Test {

    private final JobListGenerator generator = new JobListGenerator();

    private final Ds3Element jobsElement = new Ds3Element(
            "Jobs",
            "array",
            "com.spectralogic.s3.server.domain.Job",
            ImmutableList.of(
                    new Ds3Annotation(
                            "com.spectralogic.util.marshal.CustomMarshaledName",
                            ImmutableList.of(
                                    new Ds3AnnotationElement("CollectionValue", "Jobs", "java.lang.String"),
                                    new Ds3AnnotationElement("CollectionValueRenderingMode", "SINGLE_BLOCK_FOR_ALL_ELEMENTS", "com.spectralogic.util.marshal.CustomMarshaledName$CollectionNameRenderingMode"),
                                    new Ds3AnnotationElement("Value", "Job", "java.lang.String")))),
            false);

    @Test (expected = IllegalArgumentException.class)
    public void toStructElementsListNoElementsTest() {
        generator.toStructElementsList(ImmutableList.of());
    }

    @Test
    public void toStructElementsListTest() {
        final ImmutableList<StructElement> result = generator.toStructElementsList(ImmutableList.of(jobsElement));
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("Jobs"));
        assertThat(result.get(0).getType(), is("[]Job"));
        assertThat(result.get(0).getXmlNotation(), is("xml:\"Job\""));
    }
}
