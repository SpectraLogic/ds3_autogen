package com.spectralogic.ds3autogen.c;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.c.models.Type;
import org.junit.Test;

import java.text.ParseException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Type_Test {
    @Test
    public void testTypeIsPrimitive() throws ParseException {
        Ds3Element testElement1 = new Ds3Element("intElement", "int", null, null);
        Ds3Element testElement2 = new Ds3Element("boolElement", "boolean", null, null);
        final ImmutableList.Builder<Ds3Element> builder = ImmutableList.builder();
        builder.add(testElement1);
        builder.add(testElement2);
        Type testType = new Type("testType", null, builder.build());
        assertThat(testType.isPrimitiveType(), is(true));
    }
    @Test
    public void testTypeIsNotPrimitive() throws ParseException {
        Ds3Element testElement1 = new Ds3Element("boolElement", "boolean", null, null);
        Ds3Element testElement2 = new Ds3Element("complexElement", "com.spectralogic.s3.server.domain.UserApiBean", null, null);
        final ImmutableList.Builder<Ds3Element> builder = ImmutableList.builder();
        builder.add(testElement1);
        builder.add(testElement2);
        Type testType = new Type("testType", null, builder.build());
        assertThat(testType.isPrimitiveType(), is(false));
    }
    /* Commented out until functionality for Arrays and Sets is implemented.
    @Test
    public void testElementTypeArrayToString() throws ParseException {
        Element stringElement = new Element("arrayElement", "array", null, null);
        assertThat(CHelper.elementTypeToString(stringElement), is(""));
    }
    @Test
    public void testElementTypeSetToString() throws ParseException {
        Element stringElement = new Element("setElement", "java.util.Set", null, null);
        assertThat(CHelper.elementTypeToString(stringElement), is(""));
    }
    */
}
