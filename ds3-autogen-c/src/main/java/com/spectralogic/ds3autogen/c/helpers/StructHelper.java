package com.spectralogic.ds3autogen.c.helpers;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.c.models.Struct;
import com.spectralogic.ds3autogen.c.models.StructMember;
import com.spectralogic.ds3autogen.utils.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Queue;

import static com.spectralogic.ds3autogen.utils.Helper.indent;

public class StructHelper {
    private static final Logger LOG = LoggerFactory.getLogger(StructHelper.class);
    private StructHelper() {}

    private final static StructHelper structHelper = new StructHelper();

    public static StructHelper getInstance() {
        return structHelper;
    }

    public static String getNameUnderscores(final String name) {
        return Helper.camelToUnderscore(Helper.removeTrailingRequestHandler(Helper.unqualifiedName(name)));
    }

    public static String getDs3Type(final String name) {
        return "ds3_" + getNameUnderscores(name);
    }

    public static String formatStructMembers(final ImmutableList<StructMember> structMembers) throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();

        for (final StructMember member : structMembers) {
            outputBuilder.append(indent(1)).
                    append(member.getType()).
                    append(" ").
                    append(member.getName()).
                    append(";").
                    append("\n");
        }

        return outputBuilder.toString();
    }

    public static ImmutableList<StructMember> convertDs3Elements(ImmutableList<Ds3Element> elementsList) {
        final ImmutableList.Builder<StructMember> builder = ImmutableList.builder();
        for (final Ds3Element currentElement : elementsList) {
            String type;
            if (currentElement.getType().equals("array")) {
                type = currentElement.getComponentType();
            } else {
                type = currentElement.getType();
            }
            builder.add(new StructMember(getDs3Type(currentElement.getName()), getNameUnderscores(type)));
        }
        return builder.build();
    }

    public static boolean isPrimitive(final Struct struct) {
        for (final StructMember member : struct.getVariables()) {
            if (!Helper.isPrimitiveType(member.getType())) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsExistingStructs(final Struct struct, final Queue existingStructs) {
        for (final StructMember structMember: struct.getVariables()) {
            if (!existingStructs.contains(structMember.getType())) {
                return false;
            }
        }
        return true;
    }
}
