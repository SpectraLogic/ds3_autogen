package com.spectralogic.ds3autogen.c.converters;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.c.helpers.StructHelper;
import com.spectralogic.ds3autogen.c.models.Struct;
import com.spectralogic.ds3autogen.c.models.StructMember;

import java.text.ParseException;

public final class StructConverter {
    private StructConverter() {}

    private final static StructConverter structConverter = new StructConverter();

    private Struct convert(final Ds3Type ds3Type) throws ParseException {
        final ImmutableList<StructMember> variablesList = StructHelper.convertDs3Elements(ds3Type.getElements());
        return new Struct(
                ds3Type.getName(),
                variablesList);
    }

    public static Struct toStruct(final Ds3Type ds3Type) throws ParseException {
        return structConverter.convert(ds3Type);
    }
}
