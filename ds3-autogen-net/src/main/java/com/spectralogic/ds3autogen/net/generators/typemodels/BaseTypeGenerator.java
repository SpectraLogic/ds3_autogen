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

package com.spectralogic.ds3autogen.net.generators.typemodels;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.Ds3Annotation;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3EnumConstant;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.net.model.common.NetNullableVariable;
import com.spectralogic.ds3autogen.net.model.type.BaseType;
import com.spectralogic.ds3autogen.net.model.type.EnumConstant;
import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil;

import static com.spectralogic.ds3autogen.net.utils.NetNullableVariableUtils.createNullableVariable;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

public class BaseTypeGenerator implements TypeModelGenerator<BaseType>, TypeModelGeneratorUtils {

    @Override
    public BaseType generate(final Ds3Type ds3Type, final ImmutableMap<String, Ds3Type> typeMap) {
        final String name = NormalizingContractNamesUtil.removePath(ds3Type.getName());
        final ImmutableList<EnumConstant> enumConstants = toEnumConstantsList(ds3Type.getEnumConstants());
        final ImmutableList<NetNullableVariable> elements = toElementsList(ds3Type.getElements(), typeMap);

        return new BaseType(
                name,
                enumConstants,
                elements);
    }

    /**
     * Converts a list of Ds3EnumConstants into a list of Enum Constants
     */
    @Override
    public ImmutableList<EnumConstant> toEnumConstantsList(final ImmutableList<Ds3EnumConstant> ds3EnumConstants) {
        return getEnumConstantsList(ds3EnumConstants);
    }

    /**
     * Converts a list of Ds3Elements into a list of NetNullableVariables
     */
    @Override
    public ImmutableList<NetNullableVariable> toElementsList(
            final ImmutableList<Ds3Element> ds3Elements,
            final ImmutableMap<String, Ds3Type> typeMap) {
        if (isEmpty(ds3Elements)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<NetNullableVariable> builder = ImmutableList.builder();
        for (final Ds3Element ds3Element : ds3Elements) {
            builder.add(toElement(ds3Element, typeMap));
        }
        return builder.build();
    }

    /**
     * Converts a list of Ds3EnumConstants to a list of Enum Constants
     */
    protected static ImmutableList<EnumConstant> getEnumConstantsList(
            final ImmutableList<Ds3EnumConstant> ds3EnumConstants) {
        if (isEmpty(ds3EnumConstants)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<EnumConstant> builder = ImmutableList.builder();
        for (final Ds3EnumConstant ds3EnumConstant : ds3EnumConstants) {
            builder.add(new EnumConstant(ds3EnumConstant.getName()));
        }
        return builder.build();
    }

    /**
     * Converts a Ds3Element into an NetNullableVariable
     */
    @Override
    public NetNullableVariable toElement(
            final Ds3Element ds3Element,
            final ImmutableMap<String, Ds3Type> typeMap) {
        final String elementName = toElementName(ds3Element.getName());
        return createNullableVariable(
                elementName,
                ds3Element.getType(),
                ds3Element.getComponentType(),
                ds3Element.isNullable(),
                typeMap);
    }

    /**
     * Converts a Ds3Element name into the sdk's name for that element.
     * The base type generator does not rename any elements.
     */
    @Override
    public String toElementName(final String elementName) {
        if (isEmpty(elementName)) {
            throw new IllegalArgumentException("Element name is empty");
        }
        return elementName;
    }

    /**
     * Determines if a list of Annotations contains the Optional annotation
     */
    protected static boolean containsOptionalAnnotation(final ImmutableList<Ds3Annotation> annotations) {
        if (isEmpty(annotations)) {
            return false;
        }
        for (final Ds3Annotation annotation : annotations) {
            if (annotation.getName().endsWith(".Optional")) {
                return true;
            }
        }
        return false;
    }
}
