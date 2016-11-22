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

package com.spectralogic.ds3autogen.net.generators.parsers.type;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element;
import com.spectralogic.ds3autogen.net.generators.parsers.element.NullableElement;

import static com.spectralogic.ds3autogen.net.utils.NetNullableElementUtils.createNullableElement;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Ds3ElementUtil.getXmlTagName;
import static com.spectralogic.ds3autogen.utils.Ds3ElementUtil.isAttribute;

/**
 * Generates the Type Parser model for the JobList type (i.e. JobsApiBean)
 */
public class JobListParserGenerator extends BaseTypeParserGenerator {

    /**
     * Converts a list of Ds3Elements into a list of Nullable Element
     */
    @Override
    public ImmutableList<NullableElement> toNullableElementsList(
            final ImmutableList<Ds3Element> ds3Elements,
            final boolean isObjectsType) {
        if (isEmpty(ds3Elements)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<NullableElement> builder = ImmutableList.builder();
        for (final Ds3Element ds3Element : ds3Elements) {
            if (ds3Element.getName().equals("Jobs")) {
                builder.add(toJobsNullableElement(ds3Element));
            } else {
                builder.add(toNullableElement(ds3Element, isObjectsType));
            }
        }
        return builder.build();
    }

    /**
     * Converts the Jobs Ds3Element into the Jobs Nullable Element.
     * Assumption: ds3Element describes the Jobs element
     */
    protected static NullableElement toJobsNullableElement(final Ds3Element ds3Element) {
        if (!ds3Element.getName().equals("Jobs")) {
            throw new IllegalArgumentException("Cannot generate the Jobs Nullable element from the Ds3Element: " + ds3Element.getName());
        }
        return createNullableElement(
                toNullableElementName(ds3Element.getName(), false),
                ds3Element.getType(),
                ds3Element.getComponentType(),
                ds3Element.getNullable(),
                getXmlTagName(ds3Element),
                null,
                isAttribute(ds3Element.getDs3Annotations()));
    }
}
