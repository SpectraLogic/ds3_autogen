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

package com.spectralogic.ds3autogen.java.helpers;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.enums.Operation;
import com.spectralogic.ds3autogen.java.models.*;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.helpers.JavaHelper.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class JavaHelper_Test {

    @Test
    public void getType_Argument_Test() {
        assertThat(getType(new Arguments("void", "test")), is("boolean"));
        assertThat(getType(new Arguments("Integer", "test")), is("Integer"));
        assertThat(getType(new Arguments("long", "test")), is("long"));
        assertThat(getType(new Arguments(null, "test")), is(""));
        assertThat(getType(new Arguments("ChecksumType", "test")), is("ChecksumType.Type"));
    }

    @Test
    public void getType_Variable_Test() {
        assertThat(getType(new Variable("test", "void", true)), is("boolean"));
        assertThat(getType(new Variable("test", "Integer", true)), is("Integer"));
        assertThat(getType(new Variable("test", "long", true)), is("long"));
        assertThat(getType(new Variable("test", null, true)), is(""));
        assertThat(getType(new Variable("test", "ChecksumType", true)), is("ChecksumType.Type"));

        assertThat(getType(new Variable("test", "void", false)), is("boolean"));
        assertThat(getType(new Variable("test", "Integer", false)), is("Integer"));
        assertThat(getType(new Variable("test", "long", false)), is("long"));
        assertThat(getType(new Variable("test", null, false)), is(""));
        assertThat(getType(new Variable("test", "ChecksumType", false)), is("ChecksumType.Type"));
    }

    @Test
    public void constructorArgs_NullList_Test() {
        final String result = constructorArgs(null);
        assertThat(result, is(""));
    }

    @Test
    public void constructorArgs_EmptyList_Test() {
        final String result = constructorArgs(ImmutableList.of());
        assertThat(result, is(""));
    }

    @Test
    public void constructorArgs_FullList_Test() {
        final String expectedResult = "final String bucketName, final String objectName, final Type1 arg1, final Type2 arg2, final Type3 arg3";
        final ImmutableList<ConstructorParam> params = ImmutableList.of(
                new SimpleConstructorParam("Arg1", "Type1"),
                new SimpleConstructorParam("Arg3", "Type3"),
                new SimpleConstructorParam("ObjectName", "String"),
                new SimpleConstructorParam("Arg2", "Type2"),
                new SimpleConstructorParam("BucketName", "String"));
        final String result = constructorArgs(params);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void constructorArgs_FullList_Test2() {
        final String expectedResult = "final String bucketName, final String objectName, @Nonnull final SeekableByteChannel channel, final long size";
        final ImmutableList<ConstructorParam> params = ImmutableList.of(
                new NonnullConstructorParam("Channel", "SeekableByteChannel"),
                new SimpleConstructorParam("Size", "long"),
                new SimpleConstructorParam("bucketName", "String"),
                new SimpleConstructorParam("objectName", "String"));
        final String result = constructorArgs(params);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void createGetter_Test() {
        final String expectedResult = "public String getBucketName() {\n"
                + "        return this.bucketName;\n"
                + "    }\n";

        final String result = createGetter("BucketName", "String");
        assertThat(result, is(expectedResult));
    }

    @Test
    public void getModelVariable_SimpleType_Test() {
        final String expectedResult =
                "    @JsonProperty(\"XmlName\")\n"
                + "    private testType testName;";
        final Element element = new Element(
                "testName",
                "xmlName",
                false,
                false,
                "testType",
                null);
        final String result = getModelVariable(element);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void getModelVariable_SimpleType_AsAttribute_Test() {
        final String expectedResult =
                "    @JacksonXmlProperty(isAttribute = true, localName = \"xmlName\")\n"
                        + "    private testType testName;";
        final Element element = new Element(
                "testName",
                "xmlName",
                true,
                false,
                "testType",
                null);
        final String result = getModelVariable(element);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void getModelVariable_ArrayComponentType_Test() {
        final String expectedResult =
                "    @JsonProperty(\"XmlName\")\n"
                + "    @JacksonXmlElementWrapper(useWrapping = false)\n"
                + "    private List<BlobApiBean> testName = new ArrayList<>();";
        final Element element = new Element(
                "testName",
                "xmlName",
                false,
                false,
                "array",
                "com.spectralogic.s3.common.platform.domain.BlobApiBean");
        final String result = getModelVariable(element);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void getModelVariable_ComponentType_Wrapper_Test() {
        final String expectedResult =
                "    @JsonProperty(\"TestName\")\n"
                        + "    @JacksonXmlElementWrapper(useWrapping = true)\n"
                        + "    private List<BlobApiBean> testName = new ArrayList<>();";
        final Element element = new Element(
                "testName",
                "xmlName",
                false,
                true,
                "array",
                "com.spectralogic.s3.common.platform.domain.BlobApiBean");
        final String result = getModelVariable(element);
        assertThat(result, is(expectedResult));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getModelVariable_NonArrayComponentType_Test() {
        final Element element = new Element(
                "testName",
                "map",
                "com.spectralogic.s3.common.platform.domain.BlobApiBean");

        getModelVariable(element);
    }

    @Test
    public void getModelConstructorArgs_NullList_Test() {
        final String result = getModelConstructorArgs(null);
        assertThat(result, is(""));
    }

    @Test
    public void getModelConstructorArgs_EmptyList_Test() {
        final String result = getModelConstructorArgs(ImmutableList.of());
        assertThat(result, is(""));
    }

    @Test
    public void getModelConstructorArgs_FullList_Test() {
        final String expectedResult = "final Type1 elmt1, final Type2 elmt2, final List<Type3> elmt3";
        final ImmutableList<Element> elements = ImmutableList.of(
                new Element("Elmt2", "Type2", null),
                new Element("Elmt1", "Type1", null),
                new Element("Elmt3", "array", "Type3"));
        final String result = getModelConstructorArgs(elements);
        assertThat(result, is(expectedResult));

        final String emptyResult = getModelConstructorArgs(null);
        assertThat(emptyResult, is(""));
    }

    @Test
    public void sortModelConstructorArgs_NullList_Test() {
        final ImmutableList<Element> result = sortModelConstructorArgs(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void sortModelConstructorArgs_EmptyList_Test() {
        final ImmutableList<Element> result = sortModelConstructorArgs(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void sortModelConstructorArgs_FullList_Test() {
        final ImmutableList<Element> expectedResult = ImmutableList.of(
                new Element("Elmt1", "Type1", null),
                new Element("Elmt2", "Type2", null),
                new Element("Elmt3", "array", "Type3"));
        final ImmutableList<Element> elements = ImmutableList.of(
                new Element("Elmt2", "Type2", null),
                new Element("Elmt3", "array", "Type3"),
                new Element("Elmt1", "Type1", null));
        final ImmutableList<Element> result = sortModelConstructorArgs(elements);
        for (int i = 0; i < elements.size(); i++) {
            assertTrue(result.get(i).getName().equals(expectedResult.get(i).getName()));
        }

        final ImmutableList<Element> emptyResult = sortModelConstructorArgs(null);
        assertTrue(emptyResult.isEmpty());
    }

    @Test
    public void toXmlLine_Test() {
        final String bulkPutExpectedResult = "final String OutputStringName = XmlOutput.toXml(ObjectListName, true);";
        final String bulkPutResult = toXmlLine("OutputStringName", "ObjectListName", Operation.START_BULK_PUT);
        assertThat(bulkPutResult, is(bulkPutExpectedResult));

        final String bulkGetExpectedResult = "final String OutputStringName = XmlOutput.toXml(ObjectListName, false);";
        final String bulkGetResult = toXmlLine("OutputStringName", "ObjectListName", Operation.START_BULK_GET);
        assertThat(bulkGetResult, is(bulkGetExpectedResult));
    }

    @Test
    public void argToString_Test() {
        assertThat(argToString(new Arguments("void", "ArgName")), is("null"));
        assertThat(argToString(new Arguments("boolean", "ArgName")), is("String.valueOf(argName)"));
        assertThat(argToString(new Arguments("String", "ArgName")), is("argName"));
        assertThat(argToString(new Arguments("Integer", "ArgName")), is("String.valueOf(argName)"));
        assertThat(argToString(new Arguments("long", "ArgName")), is("Long.toString(argName)"));
        assertThat(argToString(new Arguments("UUID", "ArgName")), is("argName.toString()"));
        assertThat(argToString(new Arguments("int", "ArgName")), is("Integer.toString(argName)"));
        assertThat(argToString(new Arguments("double", "ArgName")), is("Double.toString(argName)"));
        assertThat(argToString(new Arguments("Date", "ArgName")), is("Long.toString(argName.getTime())"));
    }

    @Test
    public void getEnumValues_NullList_Test() {
        final String result = getEnumValues(null, 1);
        assertThat(result, is(""));
    }

    @Test
    public void getEnumValues_EmptyList_Test() {
        final String result = getEnumValues(ImmutableList.of(), 1);
        assertThat(result, is(""));
    }

    @Test
    public void getEnumValues_FullList_Test() {
        final String expectedResult =
                "    DELETE,\n" +
                "    GET,\n" +
                "    HEAD,\n" +
                "    POST,\n" +
                "    PUT";
        final ImmutableList<EnumConstant> enumConstants = ImmutableList.of(
                new EnumConstant("DELETE"),
                new EnumConstant("GET"),
                new EnumConstant("HEAD"),
                new EnumConstant("POST"),
                new EnumConstant("PUT"));
        final String result = getEnumValues(enumConstants, 1);
        assertThat(result, is(expectedResult));

        assertThat(getEnumValues(ImmutableList.of(), 1), is(""));
        assertThat(getEnumValues(null, 1), is(""));
    }

    @Test
    public void removeVariable_NullList_Test() {
        final ImmutableList<Variable> result = removeVariable(null, "test");
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeVariable_EmptyList_Test() {
        final ImmutableList<Variable> result = removeVariable(ImmutableList.of(), "test");
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeVariable_FullList_Test() {
        final ImmutableList<Variable> variables = ImmutableList.of(
                new Variable("Name", "Type", false),
                new Variable("Name2", "Type2", false));

        final ImmutableList<Variable> nullVarName = removeVariable(variables, null);
        assertThat(nullVarName.size(), is(2));

        final ImmutableList<Variable> emptyVarName = removeVariable(variables, "");
        assertThat(emptyVarName.size(), is(2));

        final ImmutableList<Variable> result = removeVariable(variables, "Name");
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("Name2"));
    }

    @Test
    public void paramAssignmentRHS_Test() {
        assertThat(paramAssignmentRHS(new Arguments("UUID", "UuidArg")), is("uuidArg.toString()"));
        assertThat(paramAssignmentRHS(new Arguments("String", "StringArg")), is("stringArg"));
        assertThat(paramAssignmentRHS(new Arguments("int", "IntArg")), is("intArg"));
        assertThat(paramAssignmentRHS(new Arguments("Double", "DoubleArg")), is("doubleArg"));
    }

    @Test
    public void toAnnotation_NullObject_Test() {
        assertThat(toAnnotation(null), is(""));
    }

    @Test
    public void toAnnotation_Test() {
        final String expected = "@ResponsePayloadModel(\"MyPayload\")\n" +
                "    @Action(\"MyAction\")\n" +
                "    @Resource(\"MyResource\")";
        final AnnotationInfo annotationInfo = new AnnotationInfo("MyPayload", "MyAction", "MyResource");
        assertThat(toAnnotation(annotationInfo), is(expected));
    }

    @Test
    public void createAnnotation_NullValue_Test() {
        assertThat(createAnnotation("Annotation", null), is(""));
        assertThat(createAnnotation(null, "Value"), is(""));
        assertThat(createAnnotation(null, null), is(""));
    }

    @Test
    public void createAnnotation_EmptyValue_Test() {
        assertThat(createAnnotation("Annotation", ""), is(""));
        assertThat(createAnnotation("", "Value"), is(""));
        assertThat(createAnnotation("", ""), is(""));
    }

    @Test
    public void createAnnotation_Test() {
        final String expected = "@Annotation(\"Value\")";
        assertThat(createAnnotation("Annotation", "Value"), is(expected));
    }
}
