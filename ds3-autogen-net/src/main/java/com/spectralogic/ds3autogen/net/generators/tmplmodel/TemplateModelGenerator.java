package com.spectralogic.ds3autogen.net.generators.tmplmodel;

import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.net.model.BaseRequest;

@FunctionalInterface
public interface TemplateModelGenerator<T extends BaseRequest> {
    T generate(final Ds3Request ds3Request);
}
