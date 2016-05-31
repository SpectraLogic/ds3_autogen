package com.spectralogic.ds3autogen.python.generators.response;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.Ds3Type;

public interface ResponseModelGeneratorUtils {

    /**
     * Gets expected response codes
     */
    ImmutableList<Integer> getStatusCodes(
            final ImmutableList<Ds3ResponseCode> ds3ResponseCodes,
            final String requestName);

    /**
     * Gets the python code that parses the response payload
     */
    String toParseResponsePayload(
            final Ds3Request ds3Request,
            final ImmutableMap<String, Ds3Type> typeMap);
}
