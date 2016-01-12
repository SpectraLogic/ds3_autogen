package com.spectralogic.ds3autogen.c.helpers;

import com.spectralogic.ds3autogen.utils.Helper;

public class RequestHelper {
    private final static RequestHelper requestHelper = new RequestHelper();

    public static RequestHelper getInstance() {
        return requestHelper;
    }

    public static String getNameRoot(final String name) {
        return Helper.removeTrailingRequestHandler(Helper.unqualifiedName(name));
    }

    public static String getNameRootUnderscores(final String name) {
        return Helper.camelToUnderscore(getNameRoot(name));
    }
}
