package com.spectralogic.ds3autogen.net.helpers;

import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3TypeMapper;
import com.spectralogic.ds3autogen.net.model.Request;

public class RequestConverter {
    public static Request toRequest(final Ds3Request ds3Request, final Ds3TypeMapper ds3TypeMapper, final String commandsNamespace) {
        return new Request(getName(ds3Request));
    }

    private static String getName(final Ds3Request ds3Request) {
        final String name = ds3Request.getName();
        final int lastIndex = name.lastIndexOf(".");

        return name.substring(lastIndex + 1);
    }
}
