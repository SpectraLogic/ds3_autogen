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

package com.spectralogic.ds3autogen.net.generators.typeparsers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.net.model.common.NullableVariable;
import com.spectralogic.ds3autogen.net.model.typeparser.BaseTypeParser;
import com.spectralogic.ds3autogen.net.model.typeparser.TypeParser;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.removePath;

public class BaseTypeParserGenerator implements TypeParserModelGenerator<BaseTypeParser> {

    @Override
    public BaseTypeParser generate(
            final ImmutableMap<String, Ds3Type> typeMap) {
        final ImmutableList<TypeParser> typeParsers = toTypeParserList(typeMap);

        return new BaseTypeParser(typeParsers);
    }

    //TODO unit test
    /**
     * Converts a list of Ds3Types into a list of TypeParsers
     */
    protected static ImmutableList<TypeParser> toTypeParserList(
            final ImmutableMap<String, Ds3Type> typeMap) {
        if (isEmpty(typeMap)) {
            return ImmutableList.of();
        }

        final ImmutableList.Builder<TypeParser> builder = ImmutableList.builder();
        for (final Ds3Type ds3Type : typeMap.values().asList()) {
            builder.add(toTypeParser(ds3Type, typeMap));
        }
        return builder.build();
    }

    //TODO unit test
    /**
     * Converts a Ds3Type into a TypeParser
     */
    protected static TypeParser toTypeParser(
            final Ds3Type ds3Type,
            final ImmutableMap<String, Ds3Type> typeMap) {
        final String typeName = removePath(ds3Type.getName());
        final ImmutableList<NullableVariable> variables = toNullableVariablesList(ds3Type.getElements(), typeMap);

        return new TypeParser(typeName, variables);
    }

    //TODO unit test
    /**
     * Converts a list of Ds3Elements into a list of NullableVariables
     */
    protected static ImmutableList<NullableVariable> toNullableVariablesList(
            final ImmutableList<Ds3Element> ds3Elements,
            final ImmutableMap<String, Ds3Type> typeMap) {
        if (isEmpty(ds3Elements)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<NullableVariable> builder = ImmutableList.builder();
        for (final Ds3Element ds3Element : ds3Elements) {
            builder.add(toNullableVariable(ds3Element, typeMap));
        }
        return builder.build();
    }

    //TODO unit test
    /**
     * Converts a Ds3Element into a NullableVariable
     */
    protected static NullableVariable toNullableVariable(
            final Ds3Element ds3Element,
            final ImmutableMap<String, Ds3Type> typeMap) {
        final String name = ds3Element.getName();
        final String type = "";

        return null;
    }
}
