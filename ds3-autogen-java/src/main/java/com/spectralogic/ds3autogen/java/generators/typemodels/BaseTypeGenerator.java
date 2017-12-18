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

package com.spectralogic.ds3autogen.java.generators.typemodels;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3EnumConstant;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type;
import com.spectralogic.ds3autogen.java.converters.ConvertType;
import com.spectralogic.ds3autogen.java.models.Element;
import com.spectralogic.ds3autogen.java.models.EnumConstant;
import com.spectralogic.ds3autogen.java.models.Model;
import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;

import java.util.Collection;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Ds3ElementUtil.getXmlTagName;
import static com.spectralogic.ds3autogen.utils.Ds3ElementUtil.hasWrapperAnnotations;
import static com.spectralogic.ds3autogen.utils.Ds3ElementUtil.isAttribute;

public class BaseTypeGenerator implements TypeModelGenerator<Model>, TypeGeneratorUtil {

    @Override
    public Model generate(final Ds3Type ds3Type, final String packageName) {
        final String modelName = NormalizingContractNamesUtil.removePath(ds3Type.getName());
        final String nameToMarshal = toNameToMarshal(ds3Type);
        final ImmutableList<Element> elements = toElementList(ds3Type.getElements());
        final ImmutableList<EnumConstant> enumConstants = toEnumConstantList(ds3Type.getEnumConstants());
        final ImmutableSet<String> imports = getAllImports(ds3Type);
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
                isAttribute(ds3Element.getDs3Annotations()),
                hasWrapperAnnotations(ds3Element.getDs3Annotations()),
                ds3Element.getType(),
                ds3Element.getComponentType());
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
    public ImmutableSet<String> getAllImports(final Ds3Type ds3Type) {
        //If this is an enum, then there are no imports
        if (hasContent(ds3Type.getEnumConstants())) {
            return ImmutableSet.of();
        }
        return getImportsFromAllDs3Elements(ds3Type.getElements());
    }

    /**
     * Gets all the required imports that the elements will need in order to properly
     * generate the java model
     */
    static ImmutableSet<String> getImportsFromAllDs3Elements(final ImmutableList<Ds3Element> elements) {
        if (isEmpty(elements)) {
            return ImmutableSet.of();
        }

        return elements.stream()
                .map(BaseTypeGenerator::getImportsFromDs3Element)
                .flatMap(Collection::stream)
                .collect(GuavaCollectors.immutableSet());
    }

    /**
     * Retrieves the required imports that the Ds3Element needs in order to properly generate the java model
     */
    private static ImmutableSet<String> getImportsFromDs3Element(final Ds3Element ds3Element) {
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();

        // Retrieve import for spectra defined model
        if (ds3Element.getType().contains(".")
                && !ConvertType.isModelName(ds3Element.getType())
                && !isJavaPrimitiveType(ds3Element.getType())) {
            builder.add(ConvertType.toModelName(ds3Element.getType()));
        }

        // Retrieve import for spectra defined model within component type
        if (hasContent(ds3Element.getComponentType())
                && ds3Element.getComponentType().contains(".")
                && !isJavaPrimitiveType(ds3Element.getComponentType())) {
            if (!ConvertType.isModelName(ds3Element.getComponentType())) {
                builder.add(ConvertType.toModelName(ds3Element.getComponentType()));
            }
            builder.add("java.util.List");
            builder.add("java.util.ArrayList");
            builder.add("com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper");
        }

        // Retrieve XmlProperty import if applicable
        if (isAttribute(ds3Element.getDs3Annotations())) {
            builder.add("com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty");
        }
        return builder.build();
    }

    /**
     * Determines if a contract type represents a primitive or an object of a primitive.
     * Used to determine if an import of the type is necessary.
     */
    private static boolean isJavaPrimitiveType(final String type) {
        switch (type) {
            case "java.lang.String":
            case "java.lang.Double":
            case "double":
            case "java.lang.Long":
            case "long":
            case "java.lang.Integer":
            case "int":
            case "boolean":
                return true;
            default:
                return false;
        }
    }
}
