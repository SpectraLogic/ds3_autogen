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

package com.spectralogic.ds3autogen;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.converters.TypeConverter;

import static com.spectralogic.ds3autogen.converters.NameConverter.renameRequests;
import static com.spectralogic.ds3autogen.converters.RemoveDollarSignConverter.removeDollarSigns;
import static com.spectralogic.ds3autogen.converters.RemoveSpectraInternalConverter.removeInternalRequestsFromSpec;
import static com.spectralogic.ds3autogen.converters.ResponseTypeConverter.convertResponseTypes;
import static com.spectralogic.ds3autogen.converters.UpdateElementsConverter.updateElementsInSpec;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;


/**
 * Creates a simple interface from which to launch the various Ds3ApiSpec converters
 * with guaranteed proper order of execution.
 */
public final class Ds3SpecNormalizer {

    private Ds3SpecNormalizer() { }

    /**
     * Normalizes the spec to conform to Autogen standards. This renames requests and types,
     * and removes or includes spectra internal requests based on specified parameters.
     * @param spec The spec to be normalized
     * @param generateInternal Whether the spectra internal requests should be generated
     * @return Spec with normalized data
     */
    public static Ds3ApiSpec convertSpec(
            final Ds3ApiSpec spec,
            final boolean generateInternal) {
        verifySingleResponsePayloadRequests(spec.getRequests());

        final TypeConverter typeConverter = new TypeConverter();

        return typeConverter.modifyTypes( //Converts contract types to sdk types as specified in file typeMap.json
                updateElementsInSpec( //Updates Ds3Elements to account for ExcludeFromMarshaler values
                renameRequests( //Rename requests from RequestHandler to Request
                convertResponseTypes( //Converts response types with components into new encapsulating types
                removeDollarSigns( //Converts all type names containing '$' into proper type names
                removeInternalRequestsFromSpec(spec, generateInternal)))))); //Removes/keeps spectra internal requests
    }

    /**
     * Verifies that all response codes within the list of requests do not have multiple
     * response types. If multiple response types for a given response code is found,
     * then an exception is thrown.
     */
    protected static void verifySingleResponsePayloadRequests(final ImmutableList<Ds3Request> requests) {
        if (isEmpty(requests)) {
            return;
        }

        for (final Ds3Request request : requests) {
            verifySingleResponsePayload(request.getDs3ResponseCodes(), request.getName());
        }
    }

    /**
     * Verifies that each response code in a list does not have multiple response types.
     * If multiple response types are found, then an exception is thrown
     */
    protected static void verifySingleResponsePayload(final ImmutableList<Ds3ResponseCode> responseCodes, final String requestName) {
        if (isEmpty(responseCodes)) {
            return;
        }

        for (final Ds3ResponseCode responseCode : responseCodes) {
            if (hasContent(responseCode.getDs3ResponseTypes())
                    && responseCode.getDs3ResponseTypes().size() > 1) {
                throw new IllegalArgumentException("Request has multiple response types for a single response code:" + requestName);
            }
        }
    }
}
