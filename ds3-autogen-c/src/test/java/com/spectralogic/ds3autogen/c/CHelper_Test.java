package com.spectralogic.ds3autogen.c;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3EnumConstant;
import com.spectralogic.ds3autogen.c.helpers.CHelper;
import org.junit.Test;

import java.text.ParseException;

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

    @Test
    public void testElementTypeStringToString() throws ParseException {
        Ds3Element testElement = new Ds3Element("stringElement", "java.lang.String", null, null);
        assertThat(CHelper.elementTypeToString(testElement), is("ds3_str*"));
    }
    @Test
    public void testElementTypeDateToString() throws ParseException {
        Ds3Element testElement = new Ds3Element("dateElement", "java.util.Date", null, null);
        assertThat(CHelper.elementTypeToString(testElement), is("ds3_str*"));
    }
    @Test
    public void testElementTypeUuidToString() throws ParseException {
        Ds3Element testElement = new Ds3Element("uuidElement", "java.util.UUID", null, null);
        assertThat(CHelper.elementTypeToString(testElement), is("ds3_str*"));
    }
    @Test
    public void testElementTypeDoubleToString() throws ParseException {
        Ds3Element testElement = new Ds3Element("doubleElement", "double", null, null);
        assertThat(CHelper.elementTypeToString(testElement), is("double"));
    }
    @Test
    public void testElementTypeLongToString1() throws ParseException {
        Ds3Element testElement = new Ds3Element("longElement1", "java.lang.Long", null, null);
        assertThat(CHelper.elementTypeToString(testElement), is("uint64_t"));
    }
    @Test
    public void testElementTypeLongToString2() throws ParseException {
        Ds3Element testElement = new Ds3Element("longElement2", "long", null, null);
        assertThat(CHelper.elementTypeToString(testElement), is("uint64_t"));
    }
    @Test
    public void testElementTypeIntToString1() throws ParseException {
        Ds3Element testElement = new Ds3Element("intElement1", "int", null, null);
        assertThat(CHelper.elementTypeToString(testElement), is("int"));
    }
    @Test
    public void testElementTypeIntToString2() throws ParseException {
        Ds3Element testElement = new Ds3Element("intElement2", "java.lang.Integer", null, null);
        assertThat(CHelper.elementTypeToString(testElement), is("int"));
    }
    /* TODO
    @Test
    public void testElementTypeArrayToString() throws ParseException {
        Ds3Element stringElement = new Ds3Element("arrayElement", "array", null, null);
        assertThat(CHelper.elementTypeToString(stringElement), is(""));
    }
    @Test
    public void testElementTypeSetToString() throws ParseException {
        Ds3Element stringElement = new Ds3Element("setElement", "java.util.Set", null, null);
        assertThat(CHelper.elementTypeToString(stringElement), is(""));
    }
    */
    @Test
    public void testElementTypeBooleanToString2() throws ParseException {
        Ds3Element testElement = new Ds3Element("booleanElement", "boolean", null, null);
        assertThat(CHelper.elementTypeToString(testElement), is("ds3_bool"));
    }
    @Test
    public void testElementTypeDs3ResponseToString2() throws ParseException {
        Ds3Element testElement = new Ds3Element("ds3Element", "com.spectralogic.s3.common.platform.lang.BuildInformation", null, null);
        assertThat(CHelper.elementTypeToString(testElement), is("ds3_build_information*"));
    }
    @Test
    public void testIsBasicType() {
        Ds3Element testElement = new Ds3Element("basicElement", "boolean", null, null);
        assertThat(CHelper.isBasicElementType(testElement), is(true));
    }
    @Test
    public void testIsBasicTypeNegative() {
        Ds3Element testElement = new Ds3Element("basicElement", "com.spectralogic.s3.server.domain.UserApiBean", null, null);
        assertThat(CHelper.isBasicElementType(testElement), is(false));
    }
}
