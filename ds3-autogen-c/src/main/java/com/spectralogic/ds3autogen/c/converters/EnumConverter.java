package com.spectralogic.ds3autogen.c.converters;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.c.helpers.EnumHelper;
import com.spectralogic.ds3autogen.c.models.Enum;

public final class EnumConverter {
    private EnumConverter() {}

    private final static EnumConverter enumConverter = new EnumConverter();

    private Enum convert(final Ds3Type ds3Type) {
        final ImmutableList<String> valuesList = EnumHelper.convertDs3EnumConstants(ds3Type.getEnumConstants());
        return new Enum(
                ds3Type.getName(),
                valuesList);
    }

    public static Enum toEnum(final Ds3Type ds3Type) {
        return enumConverter.convert(ds3Type);
    }
}
