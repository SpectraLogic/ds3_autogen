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

package com.spectralogic.ds3autogen.c.models;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Annotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Element {
    private static final Logger LOG = LoggerFactory.getLogger(Element.class);

    private final String name;
    private final String type;
    private final String componentType;
    private final ImmutableList<Ds3Annotation> annotations;

    public Element(
            final String name,
            final String type,
            final String componentType,
            final ImmutableList<Ds3Annotation> annotations) {
        this.name = name;
        this.type = type;
        this.componentType = componentType;
        this.annotations = annotations;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String getComponentType() {
        return this.componentType;
    }

    public ImmutableList<Ds3Annotation> getAnnotations() {
        return this.annotations;
    }
}
