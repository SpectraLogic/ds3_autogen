/*
 * ******************************************************************************
 *   Copyright 2014-2017 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.go.models.parser;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ParseAttributeImpls_Test {

    private static final String XML_TAG = "XmlTag";
    private static final String MODEL_NAME = "modelName";
    private static final String PARAM_NAME = "ParamName";
    private static final String PARSER_NAMESPACE = "ParserNamespace";

    @Test
    public void ParseSimpleAttrTest() {
        final String expected = String.format("%s.%s = parse%sFromString(attr.Value, aggErr)", MODEL_NAME, PARAM_NAME, PARSER_NAMESPACE);

        final ParseElement parseElement = new ParseSimpleAttr(XML_TAG, MODEL_NAME, PARAM_NAME, PARSER_NAMESPACE);
        assertThat(parseElement.getXmlTag(), is(XML_TAG));
        assertThat(parseElement.getParsingCode(), is(expected));
    }

    @Test
    public void ParseStringAttrTest() {
        final String expected = String.format("%s.%s = attr.Value", MODEL_NAME, PARAM_NAME);

        final ParseElement parseElement = new ParseStringAttr(XML_TAG, MODEL_NAME, PARAM_NAME);
        assertThat(parseElement.getXmlTag(), is(XML_TAG));
        assertThat(parseElement.getParsingCode(), is(expected));
    }

    @Test
    public void ParseNullableStringAttrTest() {
        final String expected = String.format("%s.%s = parseNullableStringFromString(attr.Value)", MODEL_NAME, PARAM_NAME);

        final ParseElement parseElement = new ParseNullableStringAttr(XML_TAG, MODEL_NAME, PARAM_NAME);
        assertThat(parseElement.getXmlTag(), is(XML_TAG));
        assertThat(parseElement.getParsingCode(), is(expected));
    }

    @Test
    public void ParseEnumAttrTest() {
        final String expected = String.format("parseEnumFromString(attr.Value, &%s.%s, aggErr)", MODEL_NAME, PARAM_NAME);

        final ParseElement parseElement = new ParseEnumAttr(XML_TAG, MODEL_NAME, PARAM_NAME);
        assertThat(parseElement.getXmlTag(), is(XML_TAG));
        assertThat(parseElement.getParsingCode(), is(expected));
    }

    @Test
    public void ParseNullableEnumAttrTest() {
        final String expected = String.format("parseNullableEnumFromString(attr.Value, %s.%s, aggErr)", MODEL_NAME, PARAM_NAME);

        final ParseElement parseElement = new ParseNullableEnumAttr(XML_TAG, MODEL_NAME, PARAM_NAME);
        assertThat(parseElement.getXmlTag(), is(XML_TAG));
        assertThat(parseElement.getParsingCode(), is(expected));
    }
}
