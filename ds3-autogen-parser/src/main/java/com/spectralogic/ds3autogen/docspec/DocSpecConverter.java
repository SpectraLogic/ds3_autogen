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

package com.spectralogic.ds3autogen.docspec;

import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.NameMapper;
import com.spectralogic.ds3autogen.utils.NormalizeNameUtil;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.models.xml.docspec.ParamDescriptor;
import com.spectralogic.ds3autogen.models.xml.docspec.RawDocSpec;
import com.spectralogic.ds3autogen.models.xml.docspec.RequestDescriptor;

import java.util.List;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Helper.stripPath;

/**
 * Converts a RawDocSpec into a Ds3DocSpec
 */
public class DocSpecConverter {

    private final RawDocSpec rawDocSpec;
    private final NameMapper nameMapper;

    private DocSpecConverter(final RawDocSpec rawDocSpec, final NameMapper nameMapper) {
        this.rawDocSpec = rawDocSpec;
        this.nameMapper = nameMapper;
    }

    private Ds3DocSpec convert() {
        return new Ds3DocSpecImpl(
                toRequestDocs(rawDocSpec.getRequestDescriptors(), nameMapper),
                toParamDocs(rawDocSpec.getParamDescriptors())
        );
    }

    /**
     * Converts a RawDocSpec into a Ds3DocSpec and normalizes all command names
     */
    public static Ds3DocSpec toDs3DocSpec(final RawDocSpec rawDocSpec, final NameMapper nameMapper) {
        final DocSpecConverter converter = new DocSpecConverter(rawDocSpec, nameMapper);
        return converter.convert();
    }

    /**
     * Converts a list of ParamDescriptors into a param documentation map
     * @return Map of parameter names (key) and descriptions (value)
     */
    protected static ImmutableMap<String, String> toParamDocs(
            final List<ParamDescriptor> paramDescriptors) {
        if (isEmpty(paramDescriptors)) {
            return ImmutableMap.of();
        }
        final ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        for (final ParamDescriptor pd : paramDescriptors) {
            builder.put(pd.getName(), pd.getDescription());
        }
        return builder.build();
    }

    /**
     * Converts a list of RequestDescriptors into a request documentation map
     * @return Map of normalized request names (key) and descriptions (value)
     */
    protected static ImmutableMap<String, String> toRequestDocs(
            final List<RequestDescriptor> requestDescriptors,
            final NameMapper nameMapper) {
        if (isEmpty(requestDescriptors)) {
            return ImmutableMap.of();
        }
        final ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        for (final RequestDescriptor rd : requestDescriptors) {
            builder.put(
                    normalizeRequestName(rd, nameMapper),
                    rd.getDescription());
        }
        return builder.build();
    }

    /**
     * Gets the normalized request name of the RequestDescriptor and strips the path
     */
    protected static String normalizeRequestName(
            final RequestDescriptor requestDescriptor,
            final NameMapper nameMapper) {
        return stripPath(NormalizeNameUtil.normalizeRequestName(
                requestDescriptor.getName(),
                requestDescriptor.getClassification(),
                nameMapper));
    }
}
