package com.spectralogic.ds3autogen.net.generators.responsemodels;

import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.net.model.response.BaseResponse;

@FunctionalInterface
public interface ResponseModelGenerator <T extends BaseResponse> {
    T generate(final Ds3Request request);
}
