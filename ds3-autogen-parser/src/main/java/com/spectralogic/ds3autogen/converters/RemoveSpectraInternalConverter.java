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

package com.spectralogic.ds3autogen.converters;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.enums.Classification;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

/**
 * Removes Spectra Internal requests from Ds3ApiSpec
 */
public final class RemoveSpectraInternalConverter {

    private static final Logger LOG = LoggerFactory.getLogger(RemoveSpectraInternalConverter.class);

    private RemoveSpectraInternalConverter() {
        //Pass
    }

    /**
     * Removes all Spectra Internal requests form a Spec if generateInternal is not specified
     */
    public static Ds3ApiSpec removeInternalRequestsFromSpec(final Ds3ApiSpec spec, final boolean generateInternal) {
        if (generateInternal) {
            LOG.info("Generating Spectra Internal requests");
            return spec;
        }
        LOG.info("Removing Spectra Internal requests from Ds3ApiSpec");
        return new Ds3ApiSpec(
                removeSpectraInternalRequests(spec.getRequests()),
                spec.getTypes());
    }

    /**
     * Removes all Spectra Internal requests form a list of Ds3Requests
     */
    protected static ImmutableList<Ds3Request> removeSpectraInternalRequests(
            final ImmutableList<Ds3Request> requests) {
        if (isEmpty(requests)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Ds3Request> builder = ImmutableList.builder();
        for (final Ds3Request request : requests) {
            if (request.getClassification() != Classification.spectrainternal) {
                builder.add(request);
            }
        }
        return builder.build();
    }
}
