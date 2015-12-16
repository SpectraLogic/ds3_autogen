package com.spectralogic.ds3autogen.net.generators.requestmodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.net.NetHelper;
import com.spectralogic.ds3autogen.net.model.request.BaseRequest;
import com.spectralogic.ds3autogen.net.util.GeneratorUtils;

public class BaseRequestGenerator implements RequestModelGenerator<BaseRequest> {
    @Override
    public BaseRequest generate(final Ds3Request ds3Request) {

        final String name = GeneratorUtils.toRequestName(ds3Request);
        final String path = GeneratorUtils.toRequestPath(ds3Request);
        final ImmutableList<Arguments> requiredArgs = GeneratorUtils.getRequiredArgs(ds3Request);

        return new BaseRequest(NetHelper.getInstance(), name, path, ds3Request.getHttpVerb(), requiredArgs);
    }
}
