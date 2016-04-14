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
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.*;
import com.spectralogic.ds3autogen.java.converters.ConvertType;
import com.spectralogic.ds3autogen.java.models.Element;
import com.spectralogic.ds3autogen.java.models.EnumConstant;
import com.spectralogic.ds3autogen.java.models.Model;
import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

public class BaseTypeGenerator implements TypeModelGenerator<Model>, TypeGeneratorUtil {

    @Override
    public Model generate(final Ds3Type ds3Type, final String packageName) {
        final String modelName = NormalizingContractNamesUtil.removePath(ds3Type.getName());
        final String nameToMarshal = toNameToMarshal(ds3Type);
        final ImmutableList<Element> elements = toElementList(ds3Type.getElements());
        final ImmutableList<EnumConstant> enumConstants = toEnumConstantList(ds3Type.getEnumConstants());
        final ImmutableList<String> imports = getAllImports(ds3Type);
        return new Model(
                packageName,
                modelName,
                nameToMarshal,
                elements,
                enumConstants,
                imports);
    }

    /**
     * Gets the NameToMarshal value that describes this Ds3Type. This refers to
     * the xml encapsulating tag for the payload described by this model
     */
    @Override
    public String toNameToMarshal(final Ds3Type ds3Type) {
        return ds3Type.getNameToMarshal();
    }

    /**
     * Converts a list of Ds3Elements into a list of Element models
     * @param ds3Elements A list of Ds3Elements
     * @return A list of Element models
     */
    @Override
    public ImmutableList<Element> toElementList(final ImmutableList<Ds3Element> ds3Elements) {
        if (isEmpty(ds3Elements)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Element> builder = ImmutableList.builder();
        for (final Ds3Element ds3Element : ds3Elements) {
            builder.add(toElement(ds3Element));
        }
        return builder.build();
    }

    /**
     * Converts a Ds3Element into an Element model
     * @param ds3Element A Ds3Element
     * @return An Element model
     */
    @Override
    public Element toElement(final Ds3Element ds3Element) {
        return new Element(
                ds3Element.getName(),
                getXmlTagName(ds3Element),
                toElementAsAttribute(ds3Element.getDs3Annotations()),
                hasWrapperAnnotations(ds3Element.getDs3Annotations()),
                ds3Element.getType(),
                ds3Element.getComponentType());
    }

    /**
     * Determines if the element associated with this list of annotations
     * has an Xml wrapper
     */
    protected static boolean hasWrapperAnnotations(
            final ImmutableList<Ds3Annotation> annotations) {
        if (isEmpty(annotations)) {
            return false;
        }
        for (final Ds3Annotation annotation : annotations) {
            if (annotation.getName().endsWith("CustomMarshaledName")
                    && hasWrapperAnnotationElements(annotation.getDs3AnnotationElements())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the element associated with this list of annotation elements
     * has an Xml wrapper
     */
    protected static boolean hasWrapperAnnotationElements(
            final ImmutableList<Ds3AnnotationElement> annotationElements) {
        if (isEmpty(annotationElements)) {
            return false;
        }
        for (final Ds3AnnotationElement annotationElement : annotationElements) {
            if (annotationElement.getName().equals("CollectionValue")
                    && hasContent(annotationElement.getValue())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if an element is an attribute
     */
    protected static boolean toElementAsAttribute(final ImmutableList<Ds3Annotation> annotations) {
        if (isEmpty(annotations)) {
            return false;
        }
        for (final Ds3Annotation annotation : annotations) {
            if (annotation.getName().endsWith("MarshalXmlAsAttribute")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the Xml tag name for a Ds3Element. The Xml tag name is taken from
     * within the annotations if a valid name exists, else the xml tag defaults
     * to the name of the element.
     */
    protected static String getXmlTagName(final Ds3Element ds3Element) {
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

    /**
     * Converts a list of Ds3EnumConstants into a list of EnumConstant models
     * @param ds3EnumConstants A list of Ds3EnumConstants
     * @return A list of EnumConstant models
     */
    @Override
    public ImmutableList<EnumConstant> toEnumConstantList(
            final ImmutableList<Ds3EnumConstant> ds3EnumConstants) {
        if (isEmpty(ds3EnumConstants)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<EnumConstant> builder = ImmutableList.builder();
        for (final Ds3EnumConstant ds3EnumConstant : ds3EnumConstants) {
            builder.add(toEnumConstant(ds3EnumConstant));
        }
        return builder.build();
    }

    /**
     * Converts a Ds3EnumConstant into an EnumConstant model
     * @param ds3EnumConstant A Ds3EnumConstant
     * @return An EnumConstant model
     */
    protected static EnumConstant toEnumConstant(final Ds3EnumConstant ds3EnumConstant) {
        return new EnumConstant(ds3EnumConstant.getName());
    }

    /**
     * Gets all the required imports that the Model will need in order to properly
     * generate the java model code
     */
    @Override
    public ImmutableList<String> getAllImports(final Ds3Type ds3Type) {
        //If this is an enum, then there are no imports
        if (hasContent(ds3Type.getEnumConstants())) {
            return ImmutableList.of();
        }
        return getImportsFromDs3Elements(ds3Type.getElements());
    }

    /**
     * Gets all the required imports that the elements will need in order to properly
     * generate the java model
     */
    protected static ImmutableList<String> getImportsFromDs3Elements(final ImmutableList<Ds3Element> elements) {
        if (isEmpty(elements)) {
            return ImmutableList.of();
        }
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (final Ds3Element element : elements) {
            if (element.getType().contains(".")
                    && !ConvertType.isModelName(element.getType())) {
                builder.add(ConvertType.toModelName(element.getType()));
            }
            if (hasContent(element.getComponentType())
                    && element.getComponentType().contains(".")) {
                if (!ConvertType.isModelName(element.getComponentType())) {
                    builder.add(ConvertType.toModelName(element.getComponentType()));
                }
                builder.add("java.util.List");
                builder.add("java.util.ArrayList");
                builder.add("com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper");
            }
            if (toElementAsAttribute(element.getDs3Annotations())) {
                builder.add("com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty");
            }
        }
        return builder.build().asList();
    }
}
