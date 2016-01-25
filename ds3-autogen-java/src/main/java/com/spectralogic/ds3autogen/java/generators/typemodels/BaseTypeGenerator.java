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
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3EnumConstant;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.java.converters.ConvertType;
import com.spectralogic.ds3autogen.java.models.Element;
import com.spectralogic.ds3autogen.java.models.EnumConstant;
import com.spectralogic.ds3autogen.java.models.Model;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

public class BaseTypeGenerator implements TypeModelGenerator<Model> {

    @Override
    public Model generate(final Ds3Type ds3Type, final String packageName) {
        final String modelName = toModelName(ds3Type.getName());
        final ImmutableList<Element> elements = toElementList(ds3Type.getElements());
        final ImmutableList<EnumConstant> enumConstants = toEnumConstantList(ds3Type.getEnumConstants());
        final ImmutableList<String> imports = getAllImports(ds3Type);
        return new Model(
                packageName,
                modelName,
                elements,
                enumConstants,
                imports);
    }

    /**
     * Converts a Ds3Type name into a Model name by removing the path.
     */
    protected static String toModelName(final String ds3TypeName) {
        if (isEmpty(ds3TypeName)) {
            return "";
        }
        final String[] classParts = ds3TypeName.split("\\.");
        return classParts[classParts.length -1];
    }

    /**
     * Converts a list of Ds3Elements into al ist of Element models
     * @param ds3Elements A list of Ds3Elements
     * @return A list of Element models
     */
    protected static ImmutableList<Element> toElementList(final ImmutableList<Ds3Element> ds3Elements) {
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
    protected static Element toElement(final Ds3Element ds3Element) {
        return new Element(
                ds3Element.getName(),
                ds3Element.getType(),
                ds3Element.getComponentType());
    }

    /**
     * Converts a list of Ds3EnumConstants into a list of EnumConstant models
     * @param ds3EnumConstants A list of Ds3EnumConstants
     * @return A list of EnumConstant models
     */
    protected ImmutableList<EnumConstant> toEnumConstantList(
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
    protected ImmutableList<String> getAllImports(final Ds3Type ds3Type) {
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
            if (element.getType().contains(".")) {
                builder.add(ConvertType.toModelName(element.getType()));
            }
            if (hasContent(element.getComponentType())
                    && element.getComponentType().contains(".")) {
                builder.add(ConvertType.toModelName(element.getComponentType()));
                builder.add("java.util.List");
                builder.add("com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper");
            }
        }
        return builder.build().asList();
    }
}
