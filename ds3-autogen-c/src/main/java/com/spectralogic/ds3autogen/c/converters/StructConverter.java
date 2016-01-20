package com.spectralogic.ds3autogen.c.converters;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.c.helpers.StructHelper;
import com.spectralogic.ds3autogen.c.models.Struct;
import com.spectralogic.ds3autogen.c.models.StructMember;

import java.text.ParseException;

public class StructConverter {
    private final Ds3Type ds3Type;

    private StructConverter(final Ds3Type ds3Type) {
        this.ds3Type = ds3Type;
    }

    private Struct convert() throws ParseException {
        ImmutableList<StructMember> variablesList = StructHelper.convertDs3Elements(this.ds3Type.getElements());
        return new Struct(
                this.ds3Type.getName(),
                variablesList);
    }

    public static Struct toStruct(final Ds3Type ds3Type) throws ParseException {
        final StructConverter converter = new StructConverter(ds3Type);
        return converter.convert();
    }
}
