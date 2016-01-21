package com.spectralogic.ds3autogen.c.converters;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.c.helpers.EnumHelper;
import com.spectralogic.ds3autogen.c.models.Enum;

public class EnumConverter {
    private final Ds3Type ds3Type;

    private EnumConverter(final Ds3Type ds3Type) {
        this.ds3Type = ds3Type;
    }

    private Enum convert() {
        final ImmutableList<String> valuesList = EnumHelper.convertDs3EnumConstants(this.ds3Type.getEnumConstants());
        return new Enum(
                this.ds3Type.getName(),
                valuesList);
    }

    public static Enum toEnum(final Ds3Type ds3Type) {
        final EnumConverter converter = new EnumConverter(ds3Type);
        return converter.convert();
    }
}
