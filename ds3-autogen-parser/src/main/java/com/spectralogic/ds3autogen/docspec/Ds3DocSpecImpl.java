/*
 * ******************************************************************************
 *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
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
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Implements the Ds3DocSpec.
 */
public class Ds3DocSpecImpl implements Ds3DocSpec{

    private static final Logger LOG = LoggerFactory.getLogger(Ds3DocSpecImpl.class);

    /** Map of request name (key) and request descriptor (value) */
    private final ImmutableMap<String, String> requestDocs;

    /** Map of parameter name (key) and parameter descriptor (value) */
    private final ImmutableMap<String, String> paramDocs;

    public Ds3DocSpecImpl(
            final ImmutableMap<String, String> requestDocs,
            final ImmutableMap<String, String> paramDocs) {
        this.requestDocs = requestDocs;
        this.paramDocs = paramDocs;
    }

    /**
     * Retrieves the command descriptor for the specified request
     */
    @Override
    public Optional<String> getRequestDocumentation(final String requestName) {
        final Optional<String> descriptor = Optional.ofNullable(requestDocs.get(requestName));
        if (!descriptor.isPresent()) {
            LOG.warn("Request does not have a descriptor: {}", requestName);
        }
        return descriptor;
    }

    /**
     * Retrieves the specified parameter descriptor
     */
    @Override
    public Optional<String> getParamDocumentation(final String paramName) {
        final Optional<String> descriptor = Optional.ofNullable(paramDocs.get(paramName));
        if (!descriptor.isPresent()) {
            LOG.warn("Parameter does not have a descriptor: {}", paramName);
        }
        return descriptor;
    }
}
