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

package com.spectralogic.ds3autogen.java.helpers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseType;
import com.spectralogic.ds3autogen.api.models.Operation;
import com.spectralogic.ds3autogen.java.models.Element;
import com.spectralogic.ds3autogen.java.models.EnumConstant;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.spectralogic.ds3autogen.java.helpers.JavaHelper.*;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class JavaHelper_Test {

    @Test
    public void createWithConstructorBulk_PriorityParam_Test() {
        final String expectedResult =
                        "    @Override\n" +
                        "    public CreatePutJobRequestHandler withPriority(final Priority priority) {\n" +
                        "        super.withPriority(priority);\n" +
                        "        return this;\n" +
                        "    }\n";
        final Arguments arg = new Arguments("Priority", "Priority");
        final String result = createWithConstructorBulk(arg, "CreatePutJobRequestHandler");
        assertThat(result, is(expectedResult));
    }

    @Test
    public void createWithConstructorBulk_MaxUploadSizeParam_Test() {
        final String expectedResult =
                "    public CreatePutJobRequestHandler withMaxUploadSize(final long maxUploadSize) {\n" +
                "        if (maxUploadSize > MIN_UPLOAD_SIZE_IN_BYTES) {\n" +
                "            this.getQueryParams().put(\"max_upload_size\", Long.toString(maxUploadSize));\n" +
                "        } else {\n" +
                "            this.getQueryParams().put(\"max_upload_size\", MAX_UPLOAD_SIZE_IN_BYTES);\n" +
                "        }\n" +
                "        return this;\n" +
                "    }\n";
        final Arguments arg = new Arguments("long", "MaxUploadSize");
        final String result = createWithConstructorBulk(arg, "CreatePutJobRequestHandler");
        assertThat(result, is(expectedResult));
    }

    @Test
    public void createWithConstructorBulk_VoidParam_Test() {
        final String expectedResult =
                "    public GetJobsRequestHandler withFullDetails(final boolean fullDetails) {\n" +
                "        this.fullDetails = fullDetails;\n" +
                "        if (this.fullDetails) {\n" +
                "            this.getQueryParams().put(\"full_details\", null);\n" +
                "        } else {\n" +
                "            this.getQueryParams().remove(\"full_details\");\n" +
                "        }\n" +
                "        return this;\n" +
                "    }\n";
        final Arguments arg = new Arguments("void", "FullDetails");
        final String result = createWithConstructorBulk(arg, "GetJobsRequestHandler");
        assertThat(result, is(expectedResult));
    }

    @Test
    public void argTypeList_NullList_Test() {
        final String result = argTypeList(null);
        assertThat(result, is(""));
    }

    @Test
    public void argTypeList_EmptyList_Test() {
        final String result = argTypeList(ImmutableList.of());
        assertThat(result, is(""));
    }

    @Test
    public void argTypeList_FullList_Test() {
        final String expectedResult = "Type1, Type2, Type3";
        final ImmutableList<Arguments> arguments = ImmutableList.of(
                new Arguments("Type2", "arg2"),
                new Arguments("Type1", "arg1"),
                new Arguments("Type3", "arg3"));
        final String result = argTypeList(arguments);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void createWithConstructorBulk_Test() {
        final String expectedResult =
                "    public GetBucketRequestHandler withDelimiter(final String delimiter) {\n" +
                "        this.delimiter = delimiter;\n" +
                "        this.updateQueryParam(\"delimiter\", delimiter);\n" +
                "        return this;\n" +
                "    }\n";
        final Arguments arg = new Arguments("String", "Delimiter");
        final String result = createWithConstructorBulk(arg, "GetBucketRequestHandler");
        assertThat(result, is(expectedResult));
    }

    @Test
    public void argsToList_NullList_Test() {
        final String result = argsToList(null);
        assertThat(result, is(""));
    }

    @Test
    public void argsToList_EmptyList_Test() {
        final String result = argsToList(ImmutableList.of());
        assertThat(result, is(""));
    }

    @Test
    public void argsToList_FullList_Test() {
        final String expectedResult = "arg1, arg2, arg3";
        final List<Arguments> arguments = Arrays.asList(
                new Arguments("type1", "Arg1"),
                new Arguments("type1", "Arg2"),
                new Arguments("type1", "Arg3"));
        final String result = argsToList(arguments);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void getType_Test() {
        assertThat(getType(new Arguments("void", "test")), is("boolean"));
        assertThat(getType(new Arguments("Integer", "test")), is("int"));
        assertThat(getType(new Arguments("long", "test")), is("long"));
        assertThat(getType(new Arguments(null, "test")), is(""));
        assertThat(getType(new Arguments("ChecksumType", "test")), is("ChecksumType.Type"));
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
        final ImmutableList<Arguments> arguments = ImmutableList.of(
                new Arguments("Type1", "Arg1"),
                new Arguments("Type3", "Arg3"),
                new Arguments("String", "ObjectName"),
                new Arguments("Type2", "Arg2"),
                new Arguments("String", "BucketName"));
        final String result = constructorArgs(arguments);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void constructorArgs_FullList_Test2() {
        final String expectedResult = "final String bucketName, final String objectName, final SeekableByteChannel channel, final long size";
        final ImmutableList<Arguments> arguments = ImmutableList.of(
                new Arguments("SeekableByteChannel", "Channel"),
                new Arguments("long", "Size"),
                new Arguments("String", "bucketName"),
                new Arguments("String", "objectName"));
        final String result = constructorArgs(arguments);
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
    public void modifiedArgNameList_NullList_Test() {
        final String result = modifiedArgNameList(null, "Arg1", "Integer.toString(arg1)");
        assertThat(result, is(""));
    }

    @Test
    public void modifiedArgNameList_EmptyList_Test() {
        final String result = modifiedArgNameList(ImmutableList.of(), "Arg1", "Integer.toString(arg1)");
        assertThat(result, is(""));
    }

    @Test
    public void modifiedArgNameList_FullList_Test() {
        final String expectedResult = "Integer.toString(arg1), arg2, arg3";
        final ImmutableList<Arguments> arguments = ImmutableList.of(
                new Arguments("Type1", "Arg1"),
                new Arguments("Type2", "Arg2"),
                new Arguments("Type3", "Arg3"));
        final String result = modifiedArgNameList(arguments, "Arg1", "Integer.toString(arg1)");
        assertThat(result, is(expectedResult));
    }

    @Test
    public void getModelVariable_SimpleType_Test() {
        final String expectedResult =
                "    @JsonProperty(\"TestName\")\n"
                + "    private testType testName;";
        final Element element = new Element(
                "testName",
                "testType",
                null);
        final String result = getModelVariable(element);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void getModelVariable_ArrayComponentType_Test() {
        final String expectedResult =
                "    @JsonProperty(\"TestName\")\n"
                + "    @JacksonXmlElementWrapper\n"
                + "    private List<BlobApiBean> testName;";
        final Element element = new Element(
                "testName",
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
    public void isBulkRequestArg_Test() {
        assertTrue(isBulkRequestArg("Priority"));
        assertTrue(isBulkRequestArg("WriteOptimization"));
        assertTrue(isBulkRequestArg("BucketName"));
        assertFalse(isBulkRequestArg("ChunkClientProcessingOrderGuarantee"));
    }

    @Test
    public void createWithConstructor_Test() {
        final String expectedResult =
                "    public RequestName withArgName(final ArgType argName) {\n" +
                "        this.argName = argName;\n" +
                "        this.updateQueryParam(\"arg_name\", argName.toString());\n" +
                "        return this;\n" +
                "    }\n";
        final Arguments argument = new Arguments("ArgType", "ArgName");
        final String result = createWithConstructor(argument, "RequestName");
        assertThat(result, is(expectedResult));

        final String expectedResultBoolean =
                "    public RequestName withArgName(final boolean argName) {\n" +
                "        this.argName = argName;\n" +
                "        this.updateQueryParam(\"arg_name\", null);\n" +
                "        return this;\n" +
                "    }\n";
        final Arguments booleanArgument = new Arguments("boolean", "ArgName");
        final String booleanResult = createWithConstructor(booleanArgument, "RequestName");
        assertThat(booleanResult, is(expectedResultBoolean));
    }

    @Test
    public void putQueryParamLine_Test() {
        final String expectedResult = "this.getQueryParams().put(\"arg_name\", argName.toString());";
        final Arguments argument = new Arguments("ArgType", "ArgName");
        final String result = putQueryParamLine(argument);
        assertThat(result, is(expectedResult));
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
        assertThat(argToString(new Arguments("boolean", "ArgName")), is("null"));
        assertThat(argToString(new Arguments("String", "ArgName")), is("argName"));
        assertThat(argToString(new Arguments("Integer", "ArgName")), is("Integer.toString(argName)"));
        assertThat(argToString(new Arguments("long", "ArgName")), is("Long.toString(argName)"));
        assertThat(argToString(new Arguments("UUID", "ArgName")), is("argName.toString()"));
        assertThat(argToString(new Arguments("int", "ArgName")), is("Integer.toString(argName)"));
        assertThat(argToString(new Arguments("double", "ArgName")), is("Double.toString(argName)"));
    }

    @Test
    public void convertType_String_Test() {
        assertThat(convertType("long", null), is("long"));
        assertThat(convertType("long", ""), is("long"));
        assertThat(convertType("array", "com.spectralogic.s3.common.dao.domain.tape.Tape"), is("List<Tape>"));
    }

    @Test
    public void convertType_Element_Test() {
        final Element element = new Element("Length", "long", "");
        assertThat(convertType(element), is("long"));

        final Element compositeElement = new Element("Tapes", "array", "com.spectralogic.s3.common.dao.domain.tape.Tape");
        assertThat(convertType(compositeElement), is("List<Tape>"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertType_Exception_Test() {
        final Element compositeElement = new Element("Tapes", "map", "com.spectralogic.s3.common.dao.domain.tape.Tape");
        convertType(compositeElement);
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
    public void addEnum_NullList_Test() {
        final ImmutableList<EnumConstant> result = addEnum(null, "ONE");
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("ONE"));
    }

    @Test
    public void addEnum_EmptyList_Test() {
        final ImmutableList<EnumConstant> result = addEnum(ImmutableList.of(), "ONE");
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("ONE"));
    }

    @Test
    public void addEnum_FullList_Test() {
        final ImmutableList<EnumConstant> enumConstants = ImmutableList.of(
                new EnumConstant("ONE"),
                new EnumConstant("TWO"),
                new EnumConstant("THREE"));
        final ImmutableList<EnumConstant> result = addEnum(enumConstants, "FOUR");
        assertThat(result.size(), is(4));
        assertThat(result.get(3).getName(), is("FOUR"));
    }

    @Test
    public void isSpectraDs3_Test() {
        assertTrue(isSpectraDs3("com.spectralogic.ds3client.commands.spectrads3"));
        assertFalse(isSpectraDs3("com.spectralogic.ds3client.commands"));

        assertTrue(isSpectraDs3("com.spectralogic.ds3client.commands.spectrads3.notifications"));
        assertFalse(isSpectraDs3("com.spectralogic.ds3client.commands.notifications"));
    }

    @Test
    public void isSpectraDs3OrNotification_Test() {
        assertTrue(isSpectraDs3OrNotification("com.spectralogic.ds3client.commands.spectrads3"));
        assertFalse(isSpectraDs3OrNotification("com.spectralogic.ds3client.commands"));

        assertTrue(isSpectraDs3OrNotification("com.spectralogic.ds3client.commands.spectrads3.notifications"));
        assertTrue(isSpectraDs3OrNotification("com.spectralogic.ds3client.commands.notifications"));
    }

    @Test
    public void createBulkVariable_Test() {
        final String baseClassExpected = "";
        final Arguments baseClassArg = new Arguments("BlobStoreTaskPriority", "Priority");
        assertThat(createBulkVariable(baseClassArg, true), is(baseClassExpected));

        final String optionalExpected = "private ArgType argName;";
        final Arguments arg = new Arguments("ArgType", "ArgName");
        assertThat(createBulkVariable(arg, false), is(optionalExpected));

        final String requiredExpected = "private final ArgType argName;";
        assertThat(createBulkVariable(arg, true), is(requiredExpected));
    }

    @Test
    public void processResponseCodeLines_NullType_Test() {
        final String expectedResult =
                "//Do nothing, payload is null\n" +
                "break;";

        final Ds3ResponseCode responseCode = new Ds3ResponseCode(
                200,
                ImmutableList.of(
                        new Ds3ResponseType("null", null)));

        final String result = processResponseCodeLines(responseCode, 0);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void processResponseCodeLines_Test() {
        final String expectedResult =
                "try (final InputStream content = getResponse().getResponseStream()) {\n" +
                "    this.completeMultipartUploadResultApiBeanResult = XmlOutput.fromXml(content, CompleteMultipartUploadResultApiBean.class);\n" +
                "}";

        final Ds3ResponseCode responseCode = new Ds3ResponseCode(
                200,
                ImmutableList.of(
                        new Ds3ResponseType("com.spectralogic.s3.server.domain.CompleteMultipartUploadResultApiBean", null)));

        final String result = processResponseCodeLines(responseCode, 0);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void createAllResponseResultClassVars_NullList_Test() {
        final String result = createAllResponseResultClassVars(null);
        assertThat(result, is(""));
    }

    @Test
    public void createAllResponseResultClassVars_EmptyList_Test() {
        final String result = createAllResponseResultClassVars(ImmutableList.of());
        assertThat(result, is(""));
    }

    @Test
    public void createAllResponseResultClassVars_FullList_Test() {
        final String expectedResult =
                "    private BucketObjectsApiBean bucketObjectsApiBeanResult;\n" +
                "    private HttpErrorResultApiBean httpErrorResultApiBeanResult;";

        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                new Ds3ResponseCode(
                        200,
                        ImmutableList.of(
                                new Ds3ResponseType("null", null))),
                new Ds3ResponseCode(
                        206,
                        ImmutableList.of(
                                new Ds3ResponseType("com.spectralogic.s3.server.domain.BucketObjectsApiBean", null))),
                new Ds3ResponseCode(
                        208,
                        ImmutableList.of(
                                new Ds3ResponseType("com.spectralogic.s3.server.domain.BucketObjectsApiBean", null))),
                new Ds3ResponseCode(
                        307,
                        ImmutableList.of(
                                new Ds3ResponseType("com.spectralogic.s3.server.domain.HttpErrorResultApiBean", null))),
                new Ds3ResponseCode(
                        400,
                        ImmutableList.of(
                                new Ds3ResponseType("com.spectralogic.s3.server.domain.HttpErrorResultApiBean", null))));

        final String result = createAllResponseResultClassVars(responseCodes);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void createUniqueDs3ResponseTypesMap_NullList_Test() {
        final ImmutableMap<String, Ds3ResponseType> result = createUniqueDs3ResponseTypesMap(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void createUniqueDs3ResponseTypesMap_EmptyList_Test() {
        final ImmutableMap<String, Ds3ResponseType> result = createUniqueDs3ResponseTypesMap(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void createUniqueDs3ResponseTypesMap_FullList_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                new Ds3ResponseCode(
                        200,
                        ImmutableList.of(
                                new Ds3ResponseType("null", null))),
                new Ds3ResponseCode(
                        206,
                        ImmutableList.of(
                                new Ds3ResponseType("com.spectralogic.s3.server.domain.BucketObjectsApiBean", null))),
                new Ds3ResponseCode(
                        208,
                        ImmutableList.of(
                                new Ds3ResponseType("com.spectralogic.s3.server.domain.BucketObjectsApiBean", null))),
                new Ds3ResponseCode(
                        307,
                        ImmutableList.of(
                                new Ds3ResponseType("com.spectralogic.s3.server.domain.HttpErrorResultApiBean", null))),
                new Ds3ResponseCode(
                        400,
                        ImmutableList.of(
                                new Ds3ResponseType("com.spectralogic.s3.server.domain.HttpErrorResultApiBean", null))));

        final ImmutableMap<String, Ds3ResponseType> result = createUniqueDs3ResponseTypesMap(responseCodes);
        assertThat(result.size(), is(2));
        assertTrue(result.containsKey("httpErrorResultApiBeanResult"));
        assertTrue(result.containsKey("bucketObjectsApiBeanResult"));
    }

    @Test
    public void createDs3ResponseTypeParamName_Test() {
        assertThat(
                createDs3ResponseTypeParamName(new Ds3ResponseType("null", null)),
                is(""));

        assertThat(
                createDs3ResponseTypeParamName(new Ds3ResponseType("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl", null)),
                is("bucketAclResult"));

        assertThat(
                createDs3ResponseTypeParamName(new Ds3ResponseType("array", "SimpleComponentType")),
                is("simpleComponentTypeListResult"));

        assertThat(
                createDs3ResponseTypeParamName(new Ds3ResponseType("array", "com.spectralogic.s3.common.dao.domain.ds3.BucketAcl")),
                is("bucketAclListResult"));
    }

    @Test
    public void removeErrorResponseCodes_NullList_Test() {
        final ImmutableList<Ds3ResponseCode> result = removeErrorResponseCodes(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeErrorResponseCodes_EmptyList_Test() {
        final ImmutableList<Ds3ResponseCode> result = removeErrorResponseCodes(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeErrorResponseCodes_FullList_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                new Ds3ResponseCode(200, null),
                new Ds3ResponseCode(206, null),
                new Ds3ResponseCode(307, null),
                new Ds3ResponseCode(400, null),
                new Ds3ResponseCode(503, null));

        final ImmutableList<Ds3ResponseCode> result = removeErrorResponseCodes(responseCodes);

        assertThat(result.size(), is(3));
        assertThat(result.get(0).getCode(), is(200));
        assertThat(result.get(1).getCode(), is(206));
        assertThat(result.get(2).getCode(), is(307));
    }

    @Test
    public void createResponseResultGetter_NullParam_Test() {
        final Ds3ResponseType responseType = new Ds3ResponseType("Arg", "Type");
        final String result = createResponseResultGetter(null, responseType);
        assertThat(result, is(""));
    }

    @Test
    public void createResponseResultGetter_EmptyParam_Test() {
        final Ds3ResponseType responseType = new Ds3ResponseType("Arg", "Type");
        final String result = createResponseResultGetter("", responseType);
        assertThat(result, is(""));
    }

    @Test
    public void createResponseResultGetter_SimpleParam_Test() {
        final String expectedResult =
                "    public SystemFailure getSystemFailureResult() {\n" +
                "        return this.systemFailureResult;\n" +
                "    }\n";
        final Ds3ResponseType responseType = new Ds3ResponseType("com.spectralogic.s3.common.dao.domain.ds3.SystemFailure", null);
        final String result = createResponseResultGetter("systemFailureResult", responseType);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void createResponseResultGetter_ComponentParam_Test() {
        final String expectedResult =
                "    public List<SystemFailure> getSystemFailureListResult() {\n" +
                "        return this.systemFailureListResult;\n" +
                "    }\n";
        final Ds3ResponseType responseType = new Ds3ResponseType("array", "com.spectralogic.s3.common.dao.domain.ds3.SystemFailure");
        final String result = createResponseResultGetter("systemFailureListResult", responseType);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void createAllResponseResultGetters_NullList_Test() {
        final String result = createAllResponseResultGetters(null);
        assertTrue(isEmpty(result));
    }

    @Test
    public void createAllResponseResultGetters_EmptyList_Test() {
        final String result = createAllResponseResultGetters(ImmutableList.of());
        assertTrue(isEmpty(result));
    }

    @Test
    public void createAllResponseResultGetters_List_Test() {
        final String getterSimpleType =
                "    public SystemFailure getSystemFailureResult() {\n" +
                "        return this.systemFailureResult;\n" +
                "    }\n";

        final String getterCompositeType =
                "    public List<SystemFailure> getSystemFailureListResult() {\n" +
                "        return this.systemFailureListResult;\n" +
                "    }\n";

        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                new Ds3ResponseCode(
                        100,
                        ImmutableList.of(
                                new Ds3ResponseType("com.spectralogic.s3.common.dao.domain.ds3.SystemFailure", null))),
                new Ds3ResponseCode(
                        100,
                        ImmutableList.of(
                                new Ds3ResponseType("array", "com.spectralogic.s3.common.dao.domain.ds3.SystemFailure"))));

        final String result = createAllResponseResultGetters(responseCodes);
        assertTrue(result.contains(getterSimpleType));
        assertTrue(result.contains(getterCompositeType));
    }
}
