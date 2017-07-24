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

package com.spectralogic.ds3autogen.go.generators.parser;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Annotation;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3AnnotationElement;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element;
import com.spectralogic.ds3autogen.go.models.parser.ParseChildNodeAddToSlice;
import com.spectralogic.ds3autogen.go.models.parser.ParseElement;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JobListParserGenerator_Test {

    private static final JobListParserGenerator generator = new JobListParserGenerator();

    @Test
    public void toChildNodeTest() {
        final ParseElement expected = new ParseChildNodeAddToSlice("Job", "jobList", "Jobs", "Job");

        final ImmutableList<Ds3AnnotationElement> annotationElements = ImmutableList.of(
                new Ds3AnnotationElement("CollectionValue", "Jobs", "java.lang.String"),
                new Ds3AnnotationElement("CollectionValueRenderingMode", "SINGLE_BLOCK_FOR_ALL_ELEMENTS", "com.spectralogic.util.marshal.CollectionNameRenderingMode"),
                new Ds3AnnotationElement("Value", "Job", "java.lang.String")
        );

        final Ds3Annotation annotation = new Ds3Annotation("com.spectralogic.util.marshal.CustomMarshaledName", annotationElements);

        final Ds3Element jobsElement = new Ds3Element("Jobs", "array", "com.spectralogic.s3.server.domain.Job", ImmutableList.of(annotation), false);

        final ParseElement result = generator.toChildNode(jobsElement, "JobList", ImmutableMap.of());
        assertThat(result, is(expected));
    }
}
