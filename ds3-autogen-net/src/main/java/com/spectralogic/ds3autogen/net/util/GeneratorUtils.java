package com.spectralogic.ds3autogen.net.util;

import com.spectralogic.ds3autogen.api.models.Ds3Request;

public final class GeneratorUtils {

    private GeneratorUtils() {
        //pass
    }

    public static String getRequestPath(final Ds3Request ds3Request) {
        return "/";
    }

    public static String getRequestName(final Ds3Request ds3Request) {
        final String name = ds3Request.getName();
        final int lastIndex = name.lastIndexOf(".");

        return name.substring(lastIndex + 1);
    }
}
