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

package com.spectralogic.ds3autogen.net.generators.parsers.typeset;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.net.generators.parsers.type.BaseTypeParserGenerator;
import com.spectralogic.ds3autogen.net.generators.parsers.type.JobListParserGenerator;
import com.spectralogic.ds3autogen.net.generators.parsers.type.TypeParserGenerator;
import com.spectralogic.ds3autogen.net.model.typeparser.BaseTypeParserSet;
import com.spectralogic.ds3autogen.net.model.typeparser.TypeParser;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEnum;
import static com.spectralogic.ds3autogen.utils.Ds3TypeClassificationUtil.isChecksumType;
import static com.spectralogic.ds3autogen.utils.Ds3TypeClassificationUtil.isJobsApiBean;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.removePath;

public class BaseTypeParserSetGenerator implements TypeParserSetGenerator<BaseTypeParserSet> {

    @Override
    public BaseTypeParserSet generate(
            final ImmutableMap<String, Ds3Type> typeMap) {
        final ImmutableList<TypeParser> typeParsers = toTypeParserList(typeMap);
        final ImmutableList<String> enumParsers = toEnumList(typeMap);

        return new BaseTypeParserSet(
                typeParsers,
                enumParsers);
    }

    /**
     * Retrieves the list of enum type names from within a Ds3Types list
     */
    protected static ImmutableList<String> toEnumList(final ImmutableMap<String, Ds3Type> typeMap) {
        if (isEmpty(typeMap)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (final Ds3Type ds3Type : typeMap.values().asList()) {
            if (isEnum(ds3Type) && !isChecksumType(ds3Type)) {
                builder.add(removePath(ds3Type.getName()));
            }
        }
        return builder.build();
    }

    /**
     * Converts all non-enum types within a Ds3Types list into a list of TypeParsers
     */
    protected static ImmutableList<TypeParser> toTypeParserList(
            final ImmutableMap<String, Ds3Type> typeMap) {
        if (isEmpty(typeMap)) {
            return ImmutableList.of();
        }

        final ImmutableList.Builder<TypeParser> builder = ImmutableList.builder();
        for (final Ds3Type ds3Type : typeMap.values().asList()) {
            if (!isEnum(ds3Type)) {
                builder.add(toTypeParser(ds3Type));
            }
        }
        return builder.build();
    }

    /**
     * Converts a Ds3Type into a TypeParser
     */
    protected static TypeParser toTypeParser(
            final Ds3Type ds3Type) {
        final TypeParserGenerator<?> typeGenerator = getTypeParserGenerator(ds3Type);
        return typeGenerator.generate(ds3Type);
    }

    /**
     * Retrieves the type parser generator for the specified Ds3Type
     */
    protected static TypeParserGenerator getTypeParserGenerator(final Ds3Type ds3Type) {
        if (isJobsApiBean(ds3Type)) {
            return new JobListParserGenerator();
        }
        return new BaseTypeParserGenerator();
    }
}
