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
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.java.models.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Ds3TypeClassificationUtil.COMMON_PREFIX_ELEMENT;

/**
 * Special cases the data from a Ds3Type containing a "CommonPrefixes" element
 * to conform to generation requirements. This changes the CommonPrefixes element
 * to reference a non-generated class CommonPrefixes instead of the specified array
 * of strings to handle the issue caused by each prefix having its own block.
 */
public class CommonPrefixGenerator extends BaseTypeGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(CommonPrefixGenerator.class);

    /**
     * Converts a list of Ds3Elements into a list of Element models, while special casing the
     * CommonPrefixes element to refer to a list of CommonPrefixes objects (as opposed to a list
     * of strings specified within the contract).
     */
    @Override
    public ImmutableList<Element> toElementList(final ImmutableList<Ds3Element> ds3Elements) {
        if (isEmpty(ds3Elements)) {
            LOG.error("There are no elements when the CommonPrefixes element was expected");
            return ImmutableList.of();
        }

        final ImmutableList.Builder<Element> builder = ImmutableList.builder();
        for (final Ds3Element ds3Element : ds3Elements) {
            if (ds3Element.getName().equals(COMMON_PREFIX_ELEMENT)) {
                final Ds3Element commonPrefixes = new Ds3Element("CommonPrefixes", "array", "CommonPrefixes", false);
                builder.add(toElement(commonPrefixes));
            } else {
                builder.add(toElement(ds3Element));
            }
        }
        return builder.build();
    }

    /**
     * Gets all the required imports that the Model will need in order to properly
     * generate the java model code, including importing for common prefix
     */
    @Override
    public ImmutableList<String> getAllImports(final Ds3Type ds3Type) {
        //If this is an enum, then there are no imports
        if (hasContent(ds3Type.getEnumConstants())) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<String> builder = ImmutableList.builder();
        builder.addAll(getImportsFromDs3Elements(ds3Type.getElements()));
        builder.add("com.spectralogic.ds3client.models.common.CommonPrefixes");
        return builder.build();
    }
}
