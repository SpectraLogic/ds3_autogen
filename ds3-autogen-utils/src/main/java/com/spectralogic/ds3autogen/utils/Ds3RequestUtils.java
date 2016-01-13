package com.spectralogic.ds3autogen.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Param;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Requirement;
import com.spectralogic.ds3autogen.api.models.Resource;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

public final class Ds3RequestUtils {

    private Ds3RequestUtils() {
        // pass
    }
    /**
     * Determines if the request path should contain the bucket name
     * @param ds3Request A Ds3Request
     * @return Yes if the request path for this Ds3Request should contain the bucket name,
     *         else it returns false.
     */
    public static boolean hasBucketNameInPath(final Ds3Request ds3Request) {
        return ds3Request.getBucketRequirement() == Requirement.REQUIRED
                || (ds3Request.getResource() == Resource.BUCKET && ds3Request.includeIdInPath());
    }

    /**
     * Determines if a list of Ds3Params contains a param with a given name
     * @param params List of Ds3Params
     * @param name The name of the Ds3Param that is being searched for
     * @return True if the specified Ds3Param name exists within the list of Ds3Params,
     *         else it returns false.
     */
    private static boolean hasQueryParam(
            final ImmutableList<Ds3Param> params,
            final String name) {
        if (isEmpty(params)) {
            return false;
        }
        for (final Ds3Param param : params) {
            if (param.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

}
