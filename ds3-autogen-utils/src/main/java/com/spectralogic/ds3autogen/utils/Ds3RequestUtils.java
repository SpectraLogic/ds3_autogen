package com.spectralogic.ds3autogen.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Param;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Requirement;
import com.spectralogic.ds3autogen.api.models.Resource;

public final class Ds3RequestUtils {

    private Ds3RequestUtils() {
        // pass
    }

    public static boolean hasBucketNameInPath(final Ds3Request ds3Request) {
        return !queryParamsContain(ds3Request.getRequiredQueryParams(), "Name")
                && (ds3Request.getBucketRequirement() == Requirement.REQUIRED
                || ds3Request.getResource() == Resource.BUCKET);
    }


    public static boolean queryParamsContain(
            final ImmutableList<Ds3Param> params,
            final String name) {
        if (params == null) {
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
