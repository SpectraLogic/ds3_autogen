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

package com.spectralogic.ds3autogen.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Annotation;
import com.spectralogic.ds3autogen.api.models.Ds3AnnotationElement;
import com.spectralogic.ds3autogen.api.models.Ds3Element;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

/**
 * Contains a list of static util functions for retrieving data from a Ds3Element
 */
public final class Ds3ElementUtil {

    /**
     * Gets the Xml tag name for a Ds3Element. The Xml tag name is taken from
     * within the annotations if a valid name exists, else the xml tag defaults
     * to the name of the element.
     */
    public static String getXmlTagName(final Ds3Element ds3Element) {
        final String xmlTag = getXmlTagFromAllAnnotations(ds3Element.getDs3Annotations(), ds3Element.getName());
        if (hasContent(xmlTag)) {
            return xmlTag;
        }
        return ds3Element.getName();
    }

    /**
     * Gets the Xml tag name for an element from its list of Ds3Annotations, if one
     * exists. If multiple Xml tag names are found, an exception is thrown.
     */
    protected static String getXmlTagFromAllAnnotations(
            final ImmutableList<Ds3Annotation> annotations,
            final String elementName) {
        if (isEmpty(annotations)) {
            return "";
        }
        final ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (final Ds3Annotation annotation : annotations) {
            final String curXmlName = getXmlTagFromAnnotation(annotation);
            if (hasContent(curXmlName)) {
                builder.add(curXmlName);
            }
        }
        final ImmutableList<String> xmlNames = builder.build();
        switch (xmlNames.size()) {
            case 0:
                return "";
            case 1:
                return xmlNames.get(0);
            default:
                throw new IllegalArgumentException("There are multiple xml tag names described within the annotations for the element "
                        + elementName + ": " + xmlNames.toString());
        }
    }

    /**
     * Gets the associated XmlTag from a Ds3Annotation if one exists. If the annotation
     * is not a Custom Marshaled Name, then it does not contain a valid XmlTag.
     */
    protected static String getXmlTagFromAnnotation(final Ds3Annotation annotation) {
        if (isEmpty(annotation.getDs3AnnotationElements())
                || !annotation.getName().endsWith("CustomMarshaledName")) {
            return "";
        }
        for (final Ds3AnnotationElement annotationElement : annotation.getDs3AnnotationElements()) {
            if (annotationElement.getName().equals("Value")) {
                return annotationElement.getValue();
            }
        }
        return "";
    }
}
