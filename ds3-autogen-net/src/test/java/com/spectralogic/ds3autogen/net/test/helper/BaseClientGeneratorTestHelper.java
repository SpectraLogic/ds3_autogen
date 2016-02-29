package com.spectralogic.ds3autogen.net.test.helper;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseType;

/**
 * Contains static utilities for testing the BaseClientGenerator
 */
public class BaseClientGeneratorTestHelper {

    /**
     * Creates a list of populated response codes for testing purposes. If hasPayload is true,
     * then one of the response types will be non-null. Else, both non-error response types
     * will have a type of "null"
     */
    public static ImmutableList<Ds3ResponseCode> createTestResponseCodes(final boolean hasPayload) {
        final ImmutableList.Builder<Ds3ResponseCode> builder = ImmutableList.builder();

        if (hasPayload) {
            builder.add(new Ds3ResponseCode(
                    200,
                    ImmutableList.of(
                            new Ds3ResponseType("com.spectralogic.s3.server.domain.JobWithChunksApiBean", null))));
        } else {
            builder.add(new Ds3ResponseCode(
                    200,
                    ImmutableList.of(
                            new Ds3ResponseType("null", null))));
        }

        builder.add(new Ds3ResponseCode(
                204,
                ImmutableList.of(
                        new Ds3ResponseType("null", null))));

        builder.add(new Ds3ResponseCode(
                404,
                ImmutableList.of(
                        new Ds3ResponseType("com.spectralogic.s3.server.domain.HttpErrorResultApiBean", null))));

        return builder.build();
    }

    /**
     * Creates a Ds3Request with the speicifed name and response codes
     */
    public static Ds3Request createTestRequest(final String requestName, final boolean hasPayload) {
        return new Ds3Request(
                requestName,
                null, null, null, null, null, null, null, null, false,
                createTestResponseCodes(hasPayload),
                null, null);
    }
}
