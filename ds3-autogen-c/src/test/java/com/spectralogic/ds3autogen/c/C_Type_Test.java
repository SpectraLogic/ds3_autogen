package com.spectralogic.ds3autogen.c;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.c.helpers.C_TypeHelper;
import com.spectralogic.ds3autogen.c.helpers.StructHelper;
import com.spectralogic.ds3autogen.c.models.*;
import freemarker.core.ParseException;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class C_Type_Test {
    @Test(expected=java.text.ParseException.class)
    public void testStructTypeDs3ResponseToString() throws ParseException, java.text.ParseException {
        final Ds3Element testElement = new Ds3Element("BuildInformation", "com.spectralogic.s3.common.platform.lang.BuildInformation", null);
        final C_Type c_type = C_TypeHelper.convertDs3ElementType(testElement);
        assertThat(c_type.getTypeRoot(), is("ds3_build_information_response"));
        assertThat(c_type.toString(), is("ds3_build_information_response*"));
    }
    @Test
    public void testStructMemberTypeStringToString() throws ParseException, java.text.ParseException {
        final Ds3Element testElement = new Ds3Element("stringElement", "java.lang.String", null);
        final C_Type c_type = C_TypeHelper.convertDs3ElementType(testElement);
        assertThat(c_type.getTypeRoot(), is("ds3_str"));
        assertThat(c_type.toString(), is("ds3_str*"));
    }
    @Test
    public void testStructMemberTypeDateToString() throws ParseException, java.text.ParseException {
        final Ds3Element testElement = new Ds3Element("dateElement", "java.util.Date", null);
        final C_Type c_type = C_TypeHelper.convertDs3ElementType(testElement);
        assertThat(c_type.getTypeRoot(), is("ds3_str"));
        assertThat(c_type.toString(), is("ds3_str*"));
    }
    @Test
    public void testStructMemberTypeUuidToString() throws ParseException, java.text.ParseException {
        final Ds3Element testElement = new Ds3Element("uuidElement", "java.util.UUID", null);
        final C_Type c_type = C_TypeHelper.convertDs3ElementType(testElement);
        assertThat(c_type.getTypeRoot(), is("ds3_str"));
        assertThat(c_type.toString(), is("ds3_str*"));
    }
    @Test
    public void testStructMemberTypeDoubleToString() throws ParseException, java.text.ParseException {
        final Ds3Element testElement = new Ds3Element("doubleElement", "double", null);
        final C_Type c_type = C_TypeHelper.convertDs3ElementType(testElement);
        assertThat(c_type.getTypeRoot(), is("uint64_t"));
        assertThat(c_type.toString(), is("uint64_t"));
    }
    @Test
    public void testStructMemberTypeLongToString1() throws ParseException, java.text.ParseException {
        final Ds3Element testElement = new Ds3Element("longElement1", "java.lang.Long", null);
        final C_Type c_type = C_TypeHelper.convertDs3ElementType(testElement);
        assertThat(c_type.getTypeRoot(), is("uint64_t"));
        assertThat(c_type.toString(), is("uint64_t"));
    }
    @Test
    public void testStructTypeLongToString2() throws ParseException, java.text.ParseException {
        final Ds3Element testElement = new Ds3Element("longElement2", "long", null);
        final C_Type c_type = C_TypeHelper.convertDs3ElementType(testElement);
        assertThat(c_type.getTypeRoot(), is("uint64_t"));
        assertThat(c_type.toString(), is("uint64_t"));
    }
    @Test
    public void testStructTypeIntToString1() throws ParseException, java.text.ParseException {
        final Ds3Element testElement = new Ds3Element("intStruct1", "int", null);
        final C_Type c_type = C_TypeHelper.convertDs3ElementType(testElement);
        assertThat(c_type.getTypeRoot(), is("int"));
        assertThat(c_type.toString(), is("int"));
    }
    @Test
    public void testStructTypeIntToString2() throws ParseException, java.text.ParseException {
        final Ds3Element testElement = new Ds3Element("intStruct2", "java.lang.Integer", null);
        final C_Type c_type = C_TypeHelper.convertDs3ElementType(testElement);
        assertThat(c_type.getTypeRoot(), is("int"));
        assertThat(c_type.toString(), is("int"));
    }
    @Test
    public void testStructTypeBooleanToString2() throws ParseException, java.text.ParseException {
        final Ds3Element testElement = new Ds3Element("booleanStruct", "boolean", null);
        final C_Type c_type = C_TypeHelper.convertDs3ElementType(testElement);
        assertThat(c_type.getTypeRoot(), is("ds3_bool"));
        assertThat(c_type.toString(), is("ds3_bool"));
    }
    @Test
    public void testTypeIsPrimitive() throws ParseException {
        final StructMember testStruct1 = new StructMember(new PrimitiveType("int", false), "intMember");
        final StructMember testStruct2 = new StructMember(new PrimitiveType("ds3_bool", false), "boolMember");
        final ImmutableList<StructMember> testStructMembers = ImmutableList.of(testStruct1, testStruct2);
        final Struct testStruct = new Struct("testStruct", testStructMembers);
        assertTrue(StructHelper.requiresCustomParser(testStruct));
    }
    @Test
    public void testTypeIsNotPrimitive() throws ParseException {
        final StructMember testStruct1 = new StructMember(new PrimitiveType("ds3_bool", false), "boolMember");
        final StructMember testStruct2 = new StructMember(new ComplexType("ds3_user_api_bean_response", false), "beanMember");
        final ImmutableList<StructMember> testStructMembers = ImmutableList.of(testStruct1, testStruct2);
        final Struct testStruct = new Struct("testStruct", testStructMembers);
        assertFalse(StructHelper.requiresCustomParser(testStruct));
    }
}
