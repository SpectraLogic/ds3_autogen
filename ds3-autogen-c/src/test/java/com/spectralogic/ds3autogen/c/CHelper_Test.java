package com.spectralogic.ds3autogen.c;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3EnumConstant;
import com.spectralogic.ds3autogen.c.helpers.CHelper;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CHelper_Test {

    @Test
    public void testIndent() {
        final String expectedResult = "    ";
        final String result = CHelper.indent(1);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void testGetEnumValues() {
        Ds3EnumConstant alpha = new Ds3EnumConstant("ALPHA", null);
        Ds3EnumConstant bravo = new Ds3EnumConstant("BRAVO", null);
        final ImmutableList.Builder<Ds3EnumConstant> builder = ImmutableList.builder();
        builder.add(alpha);
        builder.add(bravo);
        final ImmutableList<Ds3EnumConstant> enumConstants = builder.build();
        final String expectedResult =
                "    ALPHA,\n" +
                "    BRAVO";
        final String result = CHelper.getEnumValues(enumConstants);
        assertThat(result, is(expectedResult));
    }

}
