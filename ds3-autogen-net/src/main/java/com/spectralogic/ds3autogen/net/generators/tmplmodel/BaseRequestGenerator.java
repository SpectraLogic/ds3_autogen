package com.spectralogic.ds3autogen.net.generators.tmplmodel;

import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.net.model.BaseRequest;
import com.spectralogic.ds3autogen.net.util.GeneratorUtils;

public class BaseRequestGenerator implements TemplateModelGenerator<BaseRequest> {
    @Override
    public BaseRequest generate(final Ds3Request ds3Request) {

        final String name = GeneratorUtils.getRequestName(ds3Request);
        final String path = GeneratorUtils.getRequestPath(ds3Request);

        return new BaseRequest(name, path, ds3Request.getHttpVerb());
    }
}
