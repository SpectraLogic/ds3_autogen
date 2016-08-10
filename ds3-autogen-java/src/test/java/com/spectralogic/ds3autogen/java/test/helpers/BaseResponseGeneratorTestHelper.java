package com.spectralogic.ds3autogen.java.test.helpers;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseType;

/**
 * This class provides utilities for testing Response Converter
 */
public final class BaseResponseGeneratorTestHelper {

    /**
     * Creates a populated Ds3ResponseCode with a non-error response code
     */
    public static Ds3ResponseCode createPopulatedResponseCode(final String variation) {
        return new Ds3ResponseCode(
                200,
                ImmutableList.of(
                        new Ds3ResponseType("com.spectralogic.Test.Type" + variation, null)));
    }

    /**
     * Creates a populated Ds3ResponseCode with a null response type
     */
    public static Ds3ResponseCode createPopulatedNullResponseCode() {
        return new Ds3ResponseCode(
                200,
                ImmutableList.of(
                        new Ds3ResponseType("null", null)));
    }

    /**
     * Creates a populated Ds3ResponseCode with a 400 response code
     */
    public static Ds3ResponseCode createPopulatedErrorResponseCode(final String variation) {
        return new Ds3ResponseCode(
                400,
                ImmutableList.of(
                        new Ds3ResponseType("com.spectralogic.Test.Type" + variation, null)));
    }
}
