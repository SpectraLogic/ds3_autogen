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

package com.spectralogic.ds3autogen.net.generators.parsers.type;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Annotation;
import com.spectralogic.ds3autogen.api.models.Ds3AnnotationElement;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.net.generators.parsers.element.NullableElement;
import com.spectralogic.ds3autogen.net.generators.parsers.element.NullableListElement;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createDs3ElementTestData;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import static com.spectralogic.ds3autogen.net.generators.parsers.type.JobListParserGenerator.toJobsNullableElement;

public class JobListParserGenerator_Test {

    private final JobListParserGenerator generator = new JobListParserGenerator();

    private Ds3Element getJobsElement() {
        return new Ds3Element(
                "Jobs",
                "array",
                "com.spectralogic.s3.server.domain.JobList",
                ImmutableList.of(
                        new Ds3Annotation(
                                "com.spectralogic.util.marshal.CustomMarshaledName",
                                ImmutableList.of(
                                        new Ds3AnnotationElement("CollectionValue", "Jobs", "java.lang.String"),
                                        new Ds3AnnotationElement("CollectionValueRenderingMode", "SINGLE_BLOCK_FOR_ALL_ELEMENTS", "com.spectralogic.util.marshal.CustomMarshaledName$CollectionNameRenderingMode"),
                                        new Ds3AnnotationElement("Value", "Job", "java.lang.String")))),
                false);
    }

    @Test
    public void toJobsNullableElement_Test() {
        final NullableElement result = toJobsNullableElement(getJobsElement());
        assertThat(result, instanceOf(NullableListElement.class));
        assertThat(result.printParseElement(), is("Jobs = element.Elements(\"Job\").Select(ParseJobList).ToList()"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void toJobsNullableElement_Error_Test() {
        toJobsNullableElement(createDs3ElementTestData("TestName", "TestType"));
    }

    @Test
    public void toNullableElementsList_NullList_Test() {
        final ImmutableList<NullableElement> result = generator.toNullableElementsList(null, false);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toNullableElementsList_EmptyList_Test() {
        final ImmutableList<NullableElement> result = generator.toNullableElementsList(ImmutableList.of(), false);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toNullableElementsList_FullList_Test() {
        final ImmutableList<Ds3Element> elements = ImmutableList.of(getJobsElement());
        final ImmutableList<NullableElement> result = generator.toNullableElementsList(elements, false);
        assertThat(result.size(), is(1));
        assertThat(result.get(0), instanceOf(NullableListElement.class));
    }
}
