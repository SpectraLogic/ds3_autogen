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

package com.spectralogic.ds3autogen.go.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Annotation;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3AnnotationElement;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3EnumConstant;

/**
 * Contains model fixtures used in testing Go module
 */
public class GoModelFixturesUtil {

    private final static Ds3Annotation CUSTOM_MARSHALED_NAME = new Ds3Annotation(
            "com.spectralogic.util.marshal.CustomMarshaledName",
            ImmutableList.of(
                    new Ds3AnnotationElement("CollectionValue", "TestCollectionValue", "java.lang.String"),
                    new Ds3AnnotationElement(
                            "CollectionValueRenderingMode",
                            "UNDEFINED",
                            "com.spectralogic.util.marshal.CustomMarshaledName$CollectionNameRenderingMode"),
                    new Ds3AnnotationElement("Value", "CustomMarshaledName", "java.lang.String")));

    private final static Ds3Annotation COMMON_PREFIX_ANNOTATION = new Ds3Annotation(
            "com.spectralogic.util.marshal.CustomMarshaledName",
            ImmutableList.of(
                    new Ds3AnnotationElement("CollectionValue", "CommonPrefixes", "java.lang.String"),
                    new Ds3AnnotationElement(
                            "CollectionValueRenderingMode",
                            "BLOCK_FOR_EVERY_ELEMENT",
                            "com.spectralogic.util.marshal.CustomMarshaledName$CollectionNameRenderingMode"),
                    new Ds3AnnotationElement("Value", "Prefix", "java.lang.String")));

    private final static Ds3Annotation ATTR_ANNOTATION = new Ds3Annotation("com.spectralogic.util.marshal.MarshalXmlAsAttribute", null);

    public final static Ds3Element STR_ELEMENT = new Ds3Element("StringElement", "java.lang.String", "", false);
    public final static Ds3Element STR_PTR_ELEMENT = new Ds3Element("StringPtrElement", "java.lang.String", "", true);
    public final static Ds3Element INT_ELEMENT = new Ds3Element("IntElement", "int", "", false);
    public final static Ds3Element LIST_ELEMENT = new Ds3Element("ElementList", "array", "com.test.TestType", false);
    public final static Ds3Element ENUM_ELEMENT = new Ds3Element("EnumElement", "com.test.TestEnum", "", false);
    public final static Ds3Element ENUM_PTR_ELEMENT = new Ds3Element("EnumPtrElement", "com.test.TestEnum", "", true);
    public final static Ds3Element LIST_WITH_ENCAPS_TAG_ELEMENT = new Ds3Element("ElementListWithEncpsTag", "array", "com.test.TestType", ImmutableList.of(CUSTOM_MARSHALED_NAME), false);
    public final static Ds3Element DS3_TYPE_ELEMENT = new Ds3Element("Ds3TypeElement", "com.test.TestDs3Type", "", false);
    public final static Ds3Element LIST_ENUM_ELEMENT = new Ds3Element("ListEnumElement", "array", "com.test.TestEnum", false);
    public final static Ds3Element COMMON_PREFIX_ELEMENT = new Ds3Element("CommonPrefixes", "array", "java.lang.String", ImmutableList.of(COMMON_PREFIX_ANNOTATION), false);

    public final static Ds3Element STR_ATTR = new Ds3Element("StringAttribute", "java.lang.String", "", ImmutableList.of(ATTR_ANNOTATION), false);
    public final static Ds3Element STR_PTR_ATTR = new Ds3Element("StringPtrAttribute", "java.lang.String", "", ImmutableList.of(ATTR_ANNOTATION), true);
    public final static Ds3Element INT_ATTR = new Ds3Element("IntAttribute", "int", "", ImmutableList.of(ATTR_ANNOTATION), false);
    public final static Ds3Element ENUM_ATTR = new Ds3Element("EnumAttribute", "com.test.TestEnum", "", ImmutableList.of(ATTR_ANNOTATION), false);
    public final static Ds3Element ENUM_PTR_ATTR = new Ds3Element("EnumPtrAttribute", "com.test.TestEnum", "", ImmutableList.of(ATTR_ANNOTATION), true);

    public final static Ds3EnumConstant ENUM_CONSTANT = new Ds3EnumConstant("ENUM_CONST_ONE", ImmutableList.of());
}
