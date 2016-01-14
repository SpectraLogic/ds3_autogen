/*
 * ******************************************************************************
 *   Copyright 2014-2015 Spectra Logic Corporation. All Rights Reserved.
 *   Licensed under the Apache License, Version 2.0 (the "License"). You may not use
 *   this file except in compliance with the License. A copy of the License is located at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   or in the "license" file accompanying this file.
 *   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 *   CONDITIONS OF ANY KIND, either express or implied. See the License for the
 *   specific language governing permissions and limitations under the License.
 * ****************************************************************************
 */

package com.spectralogic.ds3autogen.c;

import com.spectralogic.ds3autogen.c.helpers.ElementHelper;
import com.spectralogic.ds3autogen.c.models.Element;
import org.junit.Test;

import java.text.ParseException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ElementHelper_Test {
    @Test
    public void testElementTypeStringToString() throws ParseException {
        final Element testElement = new Element("stringElement", "java.lang.String", null, null);
        assertThat(ElementHelper.getDs3Type(testElement), is("ds3_str*"));
    }
    @Test
    public void testElementTypeDateToString() throws ParseException {
        final Element testElement = new Element("dateElement", "java.util.Date", null, null);
        assertThat(ElementHelper.getDs3Type(testElement), is("ds3_str*"));
    }
    @Test
    public void testElementTypeUuidToString() throws ParseException {
        final Element testElement = new Element("uuidElement", "java.util.UUID", null, null);
        assertThat(ElementHelper.getDs3Type(testElement), is("ds3_str*"));
    }
    @Test
    public void testElementTypeDoubleToString() throws ParseException {
        final Element testElement = new Element("doubleElement", "double", null, null);
        assertThat(ElementHelper.getDs3Type(testElement), is("double"));
    }
    @Test
    public void testElementTypeLongToString1() throws ParseException {
        final Element testElement = new Element("longElement1", "java.lang.Long", null, null);
        assertThat(ElementHelper.getDs3Type(testElement), is("uint64_t"));
    }
    @Test
    public void testElementTypeLongToString2() throws ParseException {
        final Element testElement = new Element("longElement2", "long", null, null);
        assertThat(ElementHelper.getDs3Type(testElement), is("uint64_t"));
    }
    @Test
    public void testElementTypeIntToString1() throws ParseException {
        final Element testElement = new Element("intElement1", "int", null, null);
        assertThat(ElementHelper.getDs3Type(testElement), is("int"));
    }
    @Test
    public void testElementTypeIntToString2() throws ParseException {
        final Element testElement = new Element("intElement2", "java.lang.Integer", null, null);
        assertThat(ElementHelper.getDs3Type(testElement), is("int"));
    }
    @Test
    public void testElementTypeBooleanToString2() throws ParseException {
        final Element testElement = new Element("booleanElement", "boolean", null, null);
        assertThat(ElementHelper.getDs3Type(testElement), is("ds3_bool"));
    }
    @Test
    public void testElementTypeDs3ResponseToString2() throws ParseException {
        final Element testElement = new Element("BuildInformation", "com.spectralogic.s3.common.platform.lang.BuildInformation", null, null);
        assertThat(ElementHelper.getDs3Type(testElement), is("ds3_build_information_response*"));
    }
}
