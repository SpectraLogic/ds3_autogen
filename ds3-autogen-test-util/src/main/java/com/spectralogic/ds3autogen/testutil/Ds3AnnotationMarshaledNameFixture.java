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

package com.spectralogic.ds3autogen.testutil;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Annotation;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3AnnotationElement;

/**
 * Contains fixtures for generating different types of Annotation lists
 */
public final class Ds3AnnotationMarshaledNameFixture {

    public static Ds3Annotation createCustomMarshaledNameAnnotation() {
        return new Ds3Annotation(
                "com.spectralogic.util.marshal.CustomMarshaledName",
                ImmutableList.of(
                        new Ds3AnnotationElement("CollectionValue", "TestCollectionValue", "java.lang.String"),
                        new Ds3AnnotationElement(
                                "CollectionValueRenderingMode",
                                "UNDEFINED",
                                "com.spectralogic.util.marshal.CustomMarshaledName$CollectionNameRenderingMode"),
                        new Ds3AnnotationElement("Value", "Error", "java.lang.String")));
    }

    public static Ds3Annotation createNonCustomMarshaledNameAnnotation() {
        return new Ds3Annotation(
                "com.spectralogic.util.bean.lang.SortBy",
                ImmutableList.of(
                        new Ds3AnnotationElement("Direction", "ASCENDING", "com.spectralogic.util.bean.lang.SortBy$Direction"),
                        new Ds3AnnotationElement("Value", "3", "java.lang.Integer")));
    }

    public static Ds3Annotation createSimpleNameAnnotation() {
        return new Ds3Annotation("com.spectralogic.util.bean.lang.Optional", null);
    }
}
