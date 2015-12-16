package com.spectralogic.ds3autogen.net.generators.requestmodels;

import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.net.model.request.BaseRequest;

@FunctionalInterface
public interface RequestModelGenerator<T extends BaseRequest> {
    T generate(final Ds3Request ds3Request);
}
