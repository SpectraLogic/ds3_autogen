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

package com.spectralogic.ds3autogen.java.converters;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3EnumConstant;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.java.models.Element;
import com.spectralogic.ds3autogen.java.models.EnumConstant;
import com.spectralogic.ds3autogen.java.models.Model;

import static com.spectralogic.ds3autogen.api.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.api.utils.ConverterUtil.isEmpty;

public class ModelConverter {

    private final Ds3Type ds3Type;
    private final String packageName;

    private ModelConverter(
            final Ds3Type ds3Type,
            final String packageName) {
        this.ds3Type = ds3Type;
        this.packageName = packageName;
    }

    private Model convert() {
        final String[] classParts = ds3Type.getName().split("\\.");
        return new Model(
                packageName,
                classParts[classParts.length -1],
                getAllElements(ds3Type),
                getAllEnumConstants(ds3Type.getEnumConstants()),
                getAllImports(getAllElements(ds3Type)));
    }

    public static Model toModel(
            final Ds3Type ds3Type,
            final String packageName) {
        final ModelConverter converter = new ModelConverter(ds3Type, packageName);
        return converter.convert();
    }

    private static ImmutableList<EnumConstant> getAllEnumConstants(
            final ImmutableList<Ds3EnumConstant> ds3EnumConstants) {
        if (isEmpty(ds3EnumConstants)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<EnumConstant> builder = ImmutableList.builder();
        for (final Ds3EnumConstant ds3EnumConstant : ds3EnumConstants) {
            builder.add(getEnumConstant(ds3EnumConstant));
        }
        return builder.build();
    }

    private static EnumConstant getEnumConstant(final Ds3EnumConstant ds3EnumConstant) {
        return new EnumConstant(ds3EnumConstant.getName());
    }

    private static ImmutableList<String> getAllImports(final ImmutableList<Element> elements) {
        if (isEmpty(elements)) {
            return ImmutableList.of();
        }
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (final Element element : elements) {
            if (element.getType().contains(".")) {
                builder.add(ConvertType.convertType(element.getType()));
            }
            if (hasContent(element.getComponentType())
                    && element.getComponentType().contains(".")) {
                builder.add(ConvertType.convertType(element.getComponentType()));
                builder.add("java.util.List");
            }
        }
        return builder.build().asList();
    }

    private static ImmutableList<Element> getAllElements(final Ds3Type ds3Type) {
        if (isEmpty(ds3Type.getElements())) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Element> builder = ImmutableList.builder();
        for (final Ds3Element ds3Element : ds3Type.getElements()) {
            builder.add(getElement(ds3Element));
        }
        return builder.build();
    }

    private static Element getElement(final Ds3Element ds3Element) {
        return new Element(
                ds3Element.getName(),
                ds3Element.getType(),
                ds3Element.getComponentType());
    }
}
