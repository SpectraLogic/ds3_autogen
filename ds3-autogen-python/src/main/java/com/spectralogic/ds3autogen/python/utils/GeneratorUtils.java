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

package com.spectralogic.ds3autogen.python.utils;

import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.enums.Classification;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.enums.Requirement;
import com.spectralogic.ds3autogen.utils.models.NotificationType;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.*;
import static com.spectralogic.ds3autogen.utils.Ds3RequestUtils.hasBucketNameInPath;
import static com.spectralogic.ds3autogen.utils.Helper.camelToUnderscore;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.removePath;
import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.*;

/**
 * Provides a series of static utility functions that are used within
 * the Python package generators to convert the data from Ds3Requests and
 * Ds3Types into models that are then used within the templates
 */
public final class GeneratorUtils {

    private GeneratorUtils() {
        //pass
    }

    /**
     * Creates the Python request path code for a Ds3 request
     */
    public static String toRequestPath(final Ds3Request ds3Request) {
        if (ds3Request.getClassification() == Classification.amazons3) {
            return getAmazonS3RequestPath(ds3Request);
        }
        //Spectra internal requests have the same path construction as SpectraDs3
        return getSpectraDs3RequestPath(ds3Request);
    }

    /**
     * Creates the Python request path code for an AmazonS3 request
     */
    protected static String getAmazonS3RequestPath(final Ds3Request ds3Request) {
        final StringBuilder builder = new StringBuilder();
        if (ds3Request.getClassification() != Classification.amazons3) {
            return builder.toString();
        }
        builder.append("'/'");
        if (ds3Request.getBucketRequirement() == Requirement.REQUIRED) {
            builder.append(" + bucket_name");
        }
        if (ds3Request.getObjectRequirement() == Requirement.REQUIRED) {
            builder.append(" + '/' + object_name");
        }
        return builder.toString();
    }

    /**
     * Creates the Python request path code for a SpectraS3 request
     */
    protected static String getSpectraDs3RequestPath(final Ds3Request ds3Request) {
        final StringBuilder builder = new StringBuilder();
        if (ds3Request.getClassification() != Classification.spectrads3) {
            return builder.toString();
        }
        if (ds3Request.getResource() == null) {
            return builder.append("'/_rest_/'").toString();
        }

        builder.append("'/_rest_/").append(ds3Request.getResource().toString().toLowerCase());
        if (isNotificationRequest(ds3Request)
                && ds3Request.includeIdInPath()
                && (getNotificationType(ds3Request) == NotificationType.DELETE
                || getNotificationType(ds3Request) == NotificationType.GET)) {
            builder.append("/'").append(" + notification_id");
        } else if (hasBucketNameInPath(ds3Request)) {
            builder.append("/'").append(" + bucket_name");
        } else if (isResourceAnArg(ds3Request.getResource(), ds3Request.includeIdInPath())) {
            final Arguments resourceArg = getArgFromResource(ds3Request.getResource());
            builder.append("/'").append(" + ").append(camelToUnderscore(resourceArg.getName()));
        } else {
            builder.append("'");
        }
        return builder.toString();
    }

    /**
     * Get parser model descriptor name for specified type
     */
    public static String getParserModelName(final String type) {
        if (isEmpty(type)) {
            return "None";
        }
        return removePath(type);
    }

    /**
     * Determines if the Ds3Request has a request payload of type FileObjectList
     */
    public static boolean hasFileObjectListPayload(final Ds3Request ds3Request) {
        return hasRequiredFileObjectListPayload(ds3Request) || isEjectStorageDomainBlobsRequest(ds3Request);
    }

    /**
     * Determines if the Ds3Request has a request payload of type FileObjectList
     */
    public static boolean hasRequiredFileObjectListPayload(final Ds3Request ds3Request) {
        return isBulkGetRequest(ds3Request)
                || isBulkPutRequest(ds3Request)
                || isPhysicalPlacementRequest(ds3Request);
    }
}
