package com.spectralogic.ds3autogen.net.model.response;

import com.google.common.collect.ImmutableList;

public class BaseResponse {

    private final String name;
    private final ImmutableList<Integer> expectedStatusCodes;

    public BaseResponse(final String name, final ImmutableList<Integer> expectedStatusCodes) {
        this.name = name;
        this.expectedStatusCodes = expectedStatusCodes;
    }

    public String getName() {
        return name;
    }

    public ImmutableList<Integer> getExpectedStatusCodes() {
        return expectedStatusCodes;
    }
}
