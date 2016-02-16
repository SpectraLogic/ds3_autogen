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

package com.spectralogic.ds3autogen.converters;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Classification;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.utils.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.spectralogic.ds3autogen.converters.RemoveDollarSignConverter.getPathOfType;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.ProjectConstants.SPECTRA_S3_NAMESPACING;

/**
 * Converts all Ds3Requests within a Ds3ApiSpec to conform to the Java module
 * naming scheme and name spacing.
 *
 * Naming Scheme:
 * All Ds3Request names should end with "Request" instead of "RequestHandler"
 * All SpectraDs3 request names should be name spaced to end with "SpectraS3Request" instead of just "Request"
 * All Ds3Request names that start with "Create" should instead start with "Put"
 */
public final class NameConverter {

    private static final Logger LOG = LoggerFactory.getLogger(NameConverter.class);

    private NameConverter() { }

    /**
     * Removes "Handler" from all request names within the spec
     * and namespaces the spectrads3 commands
     * @param spec A Ds3ApiSpec
     * @return A Ds3ApiSpec where the Ds3Request names now conform to the
     *        Java module naming schemes and name spacing
     */
     public static Ds3ApiSpec renameRequests(final Ds3ApiSpec spec) {
        if (isEmpty(spec.getRequests())) {
            LOG.info("No requests to rename");
            return spec;
        }

        final ImmutableList.Builder<Ds3Request> builder = ImmutableList.builder();
        for (final Ds3Request request : spec.getRequests()) {
            builder.add(toUpdatedDs3Request(request));
        }

        return new Ds3ApiSpec(builder.build(), spec.getTypes());
    }

    /**
     * Updates a Ds3Request to include the Java module naming scheme and
     * name spacing
     * @param request A Ds3Request
     * @return The Ds3Request with an updated Request name
     */
    protected static Ds3Request toUpdatedDs3Request(final Ds3Request request) {
        return new Ds3Request(
                toUpdatedDs3RequestName(request.getName(), request.getClassification()),
                request.getHttpVerb(),
                request.getClassification(),
                request.getBucketRequirement(),
                request.getObjectRequirement(),
                request.getAction(),
                request.getResource(),
                request.getResourceType(),
                request.getOperation(),
                request.includeIdInPath(),
                request.getDs3ResponseCodes(),
                request.getOptionalQueryParams(),
                request.getRequiredQueryParams());
    }

    /**
     * Gets the new Ds3Request name that conforms to the Java module naming scheme
     * and name spacing
     */
    protected static String toUpdatedDs3RequestName(final String requestName, final Classification classification) {
        if (classification == Classification.spectrads3) {
            final String namespaceName = requestName.replace("Request", SPECTRA_S3_NAMESPACING + "Request");
            return updateName(namespaceName);
        }
        return updateName(requestName);
    }

    /**
     * Updates the request name to remove postfix 'Handler' and change
     * prefix 'Create' to 'Put'
     */
    protected static String updateName(final String requestName) {
        if (isEmpty(requestName)) {
            return null;
        }
        return stripHandlerFromName(
                changeCreateToPut(requestName));
    }

    /**
     * Changes request names that start with 'Create' to start
     * with 'Put'
     */
    protected static String changeCreateToPut(final String requestName) {
        if (isEmpty(requestName)) {
            return null;
        }
        final String path = getPathOfType(requestName, '.');
        final String name = Helper.stripPath(requestName);
        if (name.startsWith("Create")) {
            return path + name.replace("Create", "Put");
        }
        return requestName;
    }

    /**
     * Removes any instance of a trailing "Handler" in the Ds3Request name
     * @param ds3RequestName The name of a Ds3Request
     * @return The name of the Ds3Request with "Handler" removed from the end
     */
    protected static String stripHandlerFromName(final String ds3RequestName) {
        final String nameEnding = "Handler";
        if (isEmpty(ds3RequestName)) {
            return null;
        }
        if (ds3RequestName.endsWith(nameEnding)) {
            return ds3RequestName.substring(0, ds3RequestName.length() - nameEnding.length());
        }
        return ds3RequestName;
    }
}
