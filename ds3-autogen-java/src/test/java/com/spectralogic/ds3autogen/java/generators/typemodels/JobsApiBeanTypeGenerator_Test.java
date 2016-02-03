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

package com.spectralogic.ds3autogen.java.generators.typemodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Annotation;
import com.spectralogic.ds3autogen.api.models.Ds3AnnotationElement;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.java.models.Element;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JobsApiBeanTypeGenerator_Test {

    private static final JobsApiBeanTypeGenerator generator = new JobsApiBeanTypeGenerator();

    @Test
    public void toNameToMarshal_Test() {
        assertThat(generator.toNameToMarshal(null), is("Jobs"));
    }

    @Test
    public void toElement_JobsElement_Test() {
        final ImmutableList<Ds3AnnotationElement> annotationElements = ImmutableList.of(
                new Ds3AnnotationElement("CollectionValue", "Jobs", "java.lang.String"),
                new Ds3AnnotationElement(
                        "CollectionValueRenderingMode",
                        "SINGLE_BLOCK_FOR_ALL_ELEMENTS",
                        "com.spectralogic.util.marshal.CustomMarshaledName$CollectionNameRenderingMode"),
                new Ds3AnnotationElement("Value", "Job", "java.lang.String"));

        final ImmutableList<Ds3Annotation> annotations = ImmutableList.of(
                new Ds3Annotation(
                        "com.spectralogic.util.marshal.CustomMarshaledName",
                        annotationElements));

        final Ds3Element ds3Element = new Ds3Element(
                "Jobs",
                "array",
                "com.spectralogic.s3.server.domain.JobApiBean",
                annotations);

        final Element result = generator.toElement(ds3Element);
        assertThat(result.hasWrapper(), is(false));
    }

    @Test
    public void toElement_NotJobsElement_Test() {
        final ImmutableList<Ds3AnnotationElement> annotationElements = ImmutableList.of(
                new Ds3AnnotationElement("CollectionValue", "NotJobs", "java.lang.String"),
                new Ds3AnnotationElement(
                        "CollectionValueRenderingMode",
                        "SINGLE_BLOCK_FOR_ALL_ELEMENTS",
                        "com.spectralogic.util.marshal.CustomMarshaledName$CollectionNameRenderingMode"),
                new Ds3AnnotationElement("Value", "NotJob", "java.lang.String"));

        final ImmutableList<Ds3Annotation> annotations = ImmutableList.of(
                new Ds3Annotation(
                        "com.spectralogic.util.marshal.CustomMarshaledName",
                        annotationElements));

        final Ds3Element ds3Element = new Ds3Element(
                "NotJobs",
                "array",
                "com.spectralogic.s3.server.domain.JobApiBean",
                annotations);

        final Element result = generator.toElement(ds3Element);
        assertThat(result.hasWrapper(), is(true));
    }
}
