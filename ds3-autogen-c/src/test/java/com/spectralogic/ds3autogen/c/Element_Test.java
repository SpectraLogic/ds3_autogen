package com.spectralogic.ds3autogen.c;

import com.spectralogic.ds3autogen.c.models.Element;
import org.junit.Test;

import java.text.ParseException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Element_Test {
    @Test
    public void testIsBasicType() {
        Element testElement = new Element("basicElement", "boolean", null, null);
        assertThat(testElement.isPrimitiveType(), is(true));
    }
    @Test
    public void testIsBasicTypeNegative() {
        Element testElement = new Element("basicElement", "com.spectralogic.s3.server.domain.UserApiBean", null, null);
        assertThat(testElement.isPrimitiveType(), is(false));
    }
    @Test
    public void testElementTypeStringToString() throws ParseException {
        Element testElement = new Element("stringElement", "java.lang.String", null, null);
        assertThat(testElement.getDs3Type(), is("ds3_str*"));
    }
    @Test
    public void testElementTypeDateToString() throws ParseException {
        Element testElement = new Element("dateElement", "java.util.Date", null, null);
        assertThat(testElement.getDs3Type(), is("ds3_str*"));
    }
    @Test
    public void testElementTypeUuidToString() throws ParseException {
        Element testElement = new Element("uuidElement", "java.util.UUID", null, null);
        assertThat(testElement.getDs3Type(), is("ds3_str*"));
    }
    @Test
    public void testElementTypeDoubleToString() throws ParseException {
        Element testElement = new Element("doubleElement", "double", null, null);
        assertThat(testElement.getDs3Type(), is("double"));
    }
    @Test
    public void testElementTypeLongToString1() throws ParseException {
        Element testElement = new Element("longElement1", "java.lang.Long", null, null);
        assertThat(testElement.getDs3Type(), is("uint64_t"));
    }
    @Test
    public void testElementTypeLongToString2() throws ParseException {
        Element testElement = new Element("longElement2", "long", null, null);
        assertThat(testElement.getDs3Type(), is("uint64_t"));
    }
    @Test
    public void testElementTypeIntToString1() throws ParseException {
        Element testElement = new Element("intElement1", "int", null, null);
        assertThat(testElement.getDs3Type(), is("int"));
    }
    @Test
    public void testElementTypeIntToString2() throws ParseException {
        Element testElement = new Element("intElement2", "java.lang.Integer", null, null);
        assertThat(testElement.getDs3Type(), is("int"));
    }
    @Test
    public void testElementTypeBooleanToString2() throws ParseException {
        Element testElement = new Element("booleanElement", "boolean", null, null);
        assertThat(testElement.getDs3Type(), is("ds3_bool"));
    }
    @Test
    public void testElementTypeDs3ResponseToString2() throws ParseException {
        Element testElement = new Element("BuildInformation", "com.spectralogic.s3.common.platform.lang.BuildInformation", null, null);
        assertThat(testElement.getDs3Type(), is("ds3_build_information_response*"));
    }
}
