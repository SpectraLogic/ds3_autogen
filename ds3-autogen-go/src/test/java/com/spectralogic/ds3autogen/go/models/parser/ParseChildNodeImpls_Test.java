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

public class ParseChildNodeImpls_Test {

    private static final String XML_TAG = "XmlTag";
    private static final String MODEL_NAME = "modelName";
    private static final String PARAM_NAME = "ParamName";
    private static final String PARAM_TYPE = "ParamType";
    private static final String PARSER_NAMESPACE = "ParserNamespace";
    private static final String CHILD_TYPE = "ChildType";

    @Test
    public void ParseChildNodeAsPrimitiveTypeTest() {
        final String expected = String.format("%s.%s = parse%s(child.Content, aggErr)", MODEL_NAME, PARAM_NAME, PARSER_NAMESPACE);

        final ParseElement parseElement = new ParseChildNodeAsPrimitiveType(XML_TAG, MODEL_NAME, PARAM_NAME, PARSER_NAMESPACE);
        assertThat(parseElement.getXmlTag(), is(XML_TAG));
        assertThat(parseElement.getParsingCode(), is(expected));
    }

    @Test
    public void ParseChildNodeAsStringTest() {
        final String expected = String.format("%s.%s = parse%s(child.Content)", MODEL_NAME, PARAM_NAME, PARSER_NAMESPACE);

        final ParseElement parseElement = new ParseChildNodeAsString(XML_TAG, MODEL_NAME, PARAM_NAME, PARSER_NAMESPACE);
        assertThat(parseElement.getXmlTag(), is(XML_TAG));
        assertThat(parseElement.getParsingCode(), is(expected));
    }

    @Test
    public void ParseChildNodeAsDs3TypeTest() {
        final String expected = String.format("%s.%s.parse(&child, aggErr)", MODEL_NAME, PARAM_NAME);

        final ParseElement parseElement = new ParseChildNodeAsDs3Type(XML_TAG, MODEL_NAME, PARAM_NAME);
        assertThat(parseElement.getXmlTag(), is(XML_TAG));
        assertThat(parseElement.getParsingCode(), is(expected));
    }

    @Test
    public void ParseChildNodeAsSliceTest() {
        final String childTag = "ChildTag";
        final String expected = String.format("%s.%s = parse%sSlice(\"%s\", child.Children, aggErr)", MODEL_NAME, PARAM_NAME, CHILD_TYPE, childTag);

        final ParseElement parseElement = new ParseChildNodeAsSlice(XML_TAG, childTag, MODEL_NAME, PARAM_NAME, CHILD_TYPE);
        assertThat(parseElement.getXmlTag(), is(XML_TAG));
        assertThat(parseElement.getParsingCode(), is(expected));
    }

    @Test
    public void ParseChildNodeAddToSliceTest() {
        final String expected = String.format("var model %s\n" +
                "            model.parse(&child, aggErr)\n" +
                "            %s.%s = append(%s.%s, model)", CHILD_TYPE, MODEL_NAME, PARAM_NAME, MODEL_NAME, PARAM_NAME);

        final ParseElement parseElement = new ParseChildNodeAddToSlice(XML_TAG, MODEL_NAME, PARAM_NAME, CHILD_TYPE);
        assertThat(parseElement.getXmlTag(), is(XML_TAG));
        assertThat(parseElement.getParsingCode(), is(expected));
    }

    @Test
    public void ParseChildNodeAddEnumToSliceTest() {
        final String expected = String.format("var model %s\n" +
                "            parseEnum(child.Content, &model, aggErr)\n" +
                "            %s.%s = append(%s.%s, model)", CHILD_TYPE, MODEL_NAME, PARAM_NAME, MODEL_NAME, PARAM_NAME);

        final ParseElement parseElement = new ParseChildNodeAddEnumToSlice(XML_TAG, MODEL_NAME, PARAM_NAME, CHILD_TYPE);
        assertThat(parseElement.getXmlTag(), is(XML_TAG));
        assertThat(parseElement.getParsingCode(), is(expected));
    }

    @Test
    public void ParseChildNodeAddStringToSliceTest() {
        final String expected = String.format("var str = parseString(child.Content)\n" +
                "            %s.%s = append(%s.%s, str)", MODEL_NAME, PARAM_NAME, MODEL_NAME, PARAM_NAME);

        final ParseElement parseElement = new ParseChildNodeAddStringToSlice(XML_TAG, MODEL_NAME, PARAM_NAME);
        assertThat(parseElement.getXmlTag(), is(XML_TAG));
        assertThat(parseElement.getParsingCode(), is(expected));
    }

    @Test
    public void ParseChildNodeAsEnumTest() {
        final String expected = String.format("parseEnum(child.Content, &%s.%s, aggErr)", MODEL_NAME, PARAM_NAME);

        final ParseElement parseElement = new ParseChildNodeAsEnum(XML_TAG, MODEL_NAME, PARAM_NAME);
        assertThat(parseElement.getXmlTag(), is(XML_TAG));
        assertThat(parseElement.getParsingCode(), is(expected));
    }

    @Test
    public void ParseChildNodeAsNullableEnumTest() {
        final String expected = String.format("%s.%s = new%sFromContent(child.Content, aggErr)", MODEL_NAME, PARAM_NAME, PARAM_TYPE);

        final ParseElement parseElement = new ParseChildNodeAsNullableEnum(XML_TAG, MODEL_NAME, PARAM_NAME, PARAM_TYPE);
        assertThat(parseElement.getXmlTag(), is(XML_TAG));
        assertThat(parseElement.getParsingCode(), is(expected));
    }

    @Test
    public void ParseChildNodeAsCommonPrefixTest() {
        final String expected = String.format("var prefixes []string\n" +
                "            prefixes = parseStringSlice(\"Prefix\", child.Children, aggErr)\n" +
                "            %s.%s = append(%s.%s, prefixes...)", MODEL_NAME, PARAM_NAME, MODEL_NAME, PARAM_NAME);

        final ParseElement parseElement = new ParseChildNodeAsCommonPrefix(MODEL_NAME, PARAM_NAME);
        assertThat(parseElement.getXmlTag(), is("CommonPrefixes"));
        assertThat(parseElement.getParsingCode(), is(expected));
    }

    @Test
    public void ParseChildNodeAsNullableDs3TypeTest() {
        final String expected = String.format("var model %s\n" +
                "            model.parse(&child, aggErr)\n" +
                "            %s.%s = &model", CHILD_TYPE, MODEL_NAME, PARAM_NAME);

        final ParseElement parseElement = new ParseChildNodeAsNullableDs3Type(XML_TAG, MODEL_NAME, PARAM_NAME, CHILD_TYPE);
        assertThat(parseElement.getXmlTag(), is(XML_TAG));
        assertThat(parseElement.getParsingCode(), is(expected));
    }
}
