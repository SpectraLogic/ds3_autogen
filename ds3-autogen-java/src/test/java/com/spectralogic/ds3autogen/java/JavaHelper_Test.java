package com.spectralogic.ds3autogen.java;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.Operation;
import com.spectralogic.ds3autogen.java.helpers.JavaHelper;
import com.spectralogic.ds3autogen.java.models.Element;
import com.spectralogic.ds3autogen.java.models.EnumConstant;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class JavaHelper_Test {

    @Test
    public void bulkWithPriorityConstructor() {
        final String expectedResult =
                        "    @Override\n" +
                        "    public CreatePutJobRequestHandler withPriority(final Priority priority) {\n" +
                        "        super.withPriority(priority);\n" +
                        "        return this;\n" +
                        "    }\n";
        final Arguments arg = new Arguments("Priority", "Priority");
        final String result = JavaHelper.createWithConstructorBulk(arg, "CreatePutJobRequestHandler");
        assertThat(result, is(expectedResult));
    }

    @Test
    public void bulkWithMaxUploadSizeConstructor() {
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
        final String result = JavaHelper.createWithConstructorBulk(arg, "CreatePutJobRequestHandler");
        assertThat(result, is(expectedResult));
    }

    @Test
    public void withVoidConstructor() {
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
        final String result = JavaHelper.createWithConstructorBulk(arg, "GetJobsRequestHandler");
        assertThat(result, is(expectedResult));
    }

    @Test
    public void argTypeList() {
        final String expectedResult = "Type1, Type2, Type3";
        final ImmutableList<Arguments> arguments = ImmutableList.of(
                new Arguments("Type2", "arg2"),
                new Arguments("Type1", "arg1"),
                new Arguments("Type3", "arg3"));
        final String result = JavaHelper.argTypeList(arguments);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void withConstructor() {
        final String expectedResult =
                "    public GetBucketRequestHandler withDelimiter(final String delimiter) {\n" +
                "        this.delimiter = delimiter;\n" +
                "        this.updateQueryParam(\"delimiter\", delimiter);\n" +
                "        return this;\n" +
                "    }\n";
        final Arguments arg = new Arguments("String", "Delimiter");
        final String result = JavaHelper.createWithConstructorBulk(arg, "GetBucketRequestHandler");
        assertThat(result, is(expectedResult));
    }

    @Test
    public void argsToList() {
        final String expectedResult = "arg1, arg2, arg3";
        final List<Arguments> arguments = Arrays.asList(
                new Arguments("type1", "Arg1"),
                new Arguments("type1", "Arg2"),
                new Arguments("type1", "Arg3"));
        final String result = JavaHelper.argsToList(arguments);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void getType() {
        assertThat(JavaHelper.getType(new Arguments("void", "test")), is("boolean"));
        assertThat(JavaHelper.getType(new Arguments("Integer", "test")), is("int"));
        assertThat(JavaHelper.getType(new Arguments("long", "test")), is("long"));
        assertThat(JavaHelper.getType(new Arguments(null, "test")), is(""));
    }

    @Test
    public void sortConstructorArgs() {
        final ImmutableList<Arguments> expectedResult = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("String", "ObjectName"),
                new Arguments("Type1", "Arg1"),
                new Arguments("Type2", "Arg2"),
                new Arguments("Type3", "Arg3"));
        final ImmutableList<Arguments> arguments = ImmutableList.of(
                new Arguments("Type2", "Arg2"),
                new Arguments("String", "ObjectName"),
                new Arguments("Type1", "Arg1"),
                new Arguments("Type3", "Arg3"),
                new Arguments("String", "BucketName"));
        final ImmutableList<Arguments> result = JavaHelper.sortConstructorArgs(arguments);
        for (int i = 0; i < arguments.size(); i++) {
            assertTrue(result.get(i).getName().equals(expectedResult.get(i).getName()));
        }
    }

    @Test
    public void constructorArgs() {
        final String expectedResult = "final String bucketName, final String objectName, final Type1 arg1, final Type2 arg2, final Type3 arg3";
        final ImmutableList<Arguments> arguments = ImmutableList.of(
                new Arguments("Type1", "Arg1"),
                new Arguments("Type3", "Arg3"),
                new Arguments("String", "ObjectName"),
                new Arguments("Type2", "Arg2"),
                new Arguments("String", "BucketName"));
        final String result = JavaHelper.constructorArgs(arguments);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void constructorArgs2() {
        final String expectedResult = "final String bucketName, final String objectName, final SeekableByteChannel channel, final long size";
        final ImmutableList<Arguments> arguments = ImmutableList.of(
                new Arguments("SeekableByteChannel", "Channel"),
                new Arguments("long", "Size"),
                new Arguments("String", "bucketName"),
                new Arguments("String", "objectName"));
        final String result = JavaHelper.constructorArgs(arguments);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void addArgument() {
        final ImmutableList<Arguments> arguments1 = ImmutableList.of(
                new Arguments("Type1", "Arg1"),
                new Arguments("Type2", "Arg2"));
        final ImmutableList<Arguments> arguments2 = ImmutableList.of(
                new Arguments("Type3", "Arg3"),
                new Arguments("Type4", "Arg4"));
        final ImmutableList<Arguments> resultAddLists = JavaHelper.addArgument(arguments1, arguments2);
        assertTrue(containsArgName(resultAddLists, "Arg1"));
        assertTrue(containsArgName(resultAddLists, "Arg2"));
        assertTrue(containsArgName(resultAddLists, "Arg3"));
        assertTrue(containsArgName(resultAddLists, "Arg4"));

        final ImmutableList<Arguments> resultAddSingle = JavaHelper.addArgument(arguments1, "Arg5", "Type5");
        assertTrue(containsArgName(resultAddSingle, "Arg1"));
        assertTrue(containsArgName(resultAddSingle, "Arg2"));
        assertTrue(containsArgName(resultAddSingle, "Arg5"));
    }

    @Test
    public void removeArgument() {
        final ImmutableList<Arguments> arguments = ImmutableList.of(
                new Arguments("Type1", "Arg1"),
                new Arguments("Type2", "Arg2"),
                new Arguments("Type3", "Arg3"));
        final ImmutableList<Arguments> result = JavaHelper.removeArgument(arguments, "Arg2");

        assertFalse(containsArgName(result, "Arg2"));
        assertTrue(containsArgName(result, "Arg1"));
        assertTrue(containsArgName(result, "Arg3"));
    }

    private boolean containsArgName(final ImmutableList<Arguments> arguments, final String argName) {
        for (final Arguments arg : arguments) {
            if (arg.getName().equals(argName)) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void createGetter() {
        final String expectedResult = "public String getBucketName() {\n"
                + "        return this.bucketName;\n"
                + "    }\n";

        final String result = JavaHelper.createGetter("BucketName", "String");
        assertThat(result, is(expectedResult));
    }

    @Test
    public void modifiedArgNameList() {
        final String expectedResult = "Integer.toString(arg1), arg2, arg3";
        final ImmutableList<Arguments> arguments = ImmutableList.of(
                new Arguments("Type1", "Arg1"),
                new Arguments("Type2", "Arg2"),
                new Arguments("Type3", "Arg3"));
        final String result = JavaHelper.modifiedArgNameList(arguments, "Arg1", "Integer.toString(arg1)");
        assertThat(result, is(expectedResult));
    }

    @Test
    public void getResponseCodes() {
        final String expectedResult = "200, 206, 307, 400";
        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                new Ds3ResponseCode(307, null),
                new Ds3ResponseCode(206, null),
                new Ds3ResponseCode(200, null),
                new Ds3ResponseCode(400, null));

        final String result = JavaHelper.getResponseCodes(responseCodes);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void getModelVariable() {
        final String expectedResult =
                "    @JsonProperty(\"TestName\")\n"
                + "    private testType testName;";
        final Element element = new Element(
                "testName",
                "testType",
                null);
        final String result = JavaHelper.getModelVariable(element);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void getModelVariableWithArrayComponentType() {
        final String expectedResult =
                "    @JsonProperty(\"TestName\")\n"
                + "    @JacksonXmlElementWrapper\n"
                + "    private List<BlobApiBean> testName;";
        final Element element = new Element(
                "testName",
                "array",
                "com.spectralogic.s3.common.platform.domain.BlobApiBean");
        final String result = JavaHelper.getModelVariable(element);
        assertThat(result, is(expectedResult));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getModelVariableWithOtherComponentType() {
        final Element element = new Element(
                "testName",
                "map",
                "com.spectralogic.s3.common.platform.domain.BlobApiBean");

        JavaHelper.getModelVariable(element);
    }

    @Test
    public void stripPath() {
        final String expectedResult = "BlobApiBean";
        final String result = JavaHelper.stripPath("com.spectralogic.s3.common.platform.domain.BlobApiBean");
        assertThat(result, is(expectedResult));

        final String result2 = JavaHelper.stripPath("BlobApiBean");
        assertThat(result2, is(expectedResult));
    }

    @Test
    public void getModelConstructorArgs() {
        final String expectedResult = "final Type1 elmt1, final Type2 elmt2, final List<Type3> elmt3";
        final ImmutableList<Element> elements = ImmutableList.of(
                new Element("Elmt2", "Type2", null),
                new Element("Elmt1", "Type1", null),
                new Element("Elmt3", "array", "Type3"));
        final String result = JavaHelper.getModelConstructorArgs(elements);
        assertThat(result, is(expectedResult));

        final String emptyResult = JavaHelper.getModelConstructorArgs(null);
        assertThat(emptyResult, is(""));
    }

    @Test
    public void sortModelConstructorArgs() {
        final ImmutableList<Element> expectedResult = ImmutableList.of(
                new Element("Elmt1", "Type1", null),
                new Element("Elmt2", "Type2", null),
                new Element("Elmt3", "array", "Type3"));
        final ImmutableList<Element> elements = ImmutableList.of(
                new Element("Elmt2", "Type2", null),
                new Element("Elmt3", "array", "Type3"),
                new Element("Elmt1", "Type1", null));
        final ImmutableList<Element> result = JavaHelper.sortModelConstructorArgs(elements);
        for (int i = 0; i < elements.size(); i++) {
            assertTrue(result.get(i).getName().equals(expectedResult.get(i).getName()));
        }

        final ImmutableList<Element> emptyResult = JavaHelper.sortModelConstructorArgs(null);
        assertTrue(emptyResult.isEmpty());
    }

    @Test
    public void isBulkRequestArg() {
        assertTrue(JavaHelper.isBulkRequestArg("Priority"));
        assertTrue(JavaHelper.isBulkRequestArg("WriteOptimization"));
        assertFalse(JavaHelper.isBulkRequestArg("ChunkClientProcessingOrderGuarantee"));
    }

    @Test
    public void createWithConstructor() {
        final String expectedResult =
                "    public RequestName withArgName(final ArgType argName) {\n" +
                "        this.argName = argName;\n" +
                "        this.updateQueryParam(\"arg_name\", argName.toString());\n" +
                "        return this;\n" +
                "    }\n";
        final Arguments argument = new Arguments("ArgType", "ArgName");
        final String result = JavaHelper.createWithConstructor(argument, "RequestName");
        assertThat(result, is(expectedResult));

        final String expectedResultBoolean =
                "    public RequestName withArgName(final boolean argName) {\n" +
                "        this.argName = argName;\n" +
                "        this.updateQueryParam(\"arg_name\", argName.toString());\n" +
                "        return this;\n" +
                "    }\n";
        final Arguments booleanArgument = new Arguments("boolean", "ArgName");
        final String booleanResult = JavaHelper.createWithConstructor(booleanArgument, "RequestName");
        assertThat(booleanResult, is(expectedResultBoolean));
    }

    @Test
    public void putQueryParamLine() {
        final String expectedResult = "this.getQueryParams().put(\"arg_name\", argName.toString());";
        final Arguments argument = new Arguments("ArgType", "ArgName");
        final String result = JavaHelper.putQueryParamLine(argument);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void toXmlLine() {
        final String bulkPutExpectedResult = "final String OutputStringName = XmlOutput.toXml(ObjectListName, true);";
        final String bulkPutResult = JavaHelper.toXmlLine("OutputStringName", "ObjectListName", Operation.START_BULK_PUT);
        assertThat(bulkPutResult, is(bulkPutExpectedResult));

        final String bulkGetExpectedResult = "final String OutputStringName = XmlOutput.toXml(ObjectListName, false);";
        final String bulkGetResult = JavaHelper.toXmlLine("OutputStringName", "ObjectListName", Operation.START_BULK_GET);
        assertThat(bulkGetResult, is(bulkGetExpectedResult));
    }

    @Test
    public void argToString() {
        assertThat(JavaHelper.argToString(new Arguments("void", "ArgName")), is("null"));
        assertThat(JavaHelper.argToString(new Arguments("String", "ArgName")), is("argName"));
        assertThat(JavaHelper.argToString(new Arguments("Integer", "ArgName")), is("Integer.toString(argName)"));
        assertThat(JavaHelper.argToString(new Arguments("long", "ArgName")), is("Long.toString(argName)"));
        assertThat(JavaHelper.argToString(new Arguments("UUID", "ArgName")), is("argName.toString()"));
    }

    @Test
    public void convertType() {
        final Element element = new Element("Length", "long", "");
        assertThat(JavaHelper.convertType(element), is("long"));

        final Element compositeElement = new Element("Tapes", "array", "com.spectralogic.s3.common.dao.domain.tape.Tape");
        assertThat(JavaHelper.convertType(compositeElement), is("List<Tape>"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertTypeException() {
        final Element compositeElement = new Element("Tapes", "map", "com.spectralogic.s3.common.dao.domain.tape.Tape");
        JavaHelper.convertType(compositeElement);
    }

    @Test
    public void getEnumValues() {
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
        final String result = JavaHelper.getEnumValues(enumConstants);
        assertThat(result, is(expectedResult));

        assertThat(JavaHelper.getEnumValues(ImmutableList.of()), is(""));
        assertThat(JavaHelper.getEnumValues(null), is(""));
    }

    @Test
    public void isSpectraDs3() {
        assertTrue(JavaHelper.isSpectraDs3("com.spectralogic.ds3client.commands.spectrads3"));
        assertFalse(JavaHelper.isSpectraDs3("com.spectralogic.ds3client.commands"));

        assertTrue(JavaHelper.isSpectraDs3("com.spectralogic.ds3client.commands.spectrads3.notifications"));
        assertFalse(JavaHelper.isSpectraDs3("com.spectralogic.ds3client.commands.notifications"));
    }

    @Test
    public void isSpectraDs3OrNotification() {
        assertTrue(JavaHelper.isSpectraDs3OrNotification("com.spectralogic.ds3client.commands.spectrads3"));
        assertFalse(JavaHelper.isSpectraDs3OrNotification("com.spectralogic.ds3client.commands"));

        assertTrue(JavaHelper.isSpectraDs3OrNotification("com.spectralogic.ds3client.commands.spectrads3.notifications"));
        assertTrue(JavaHelper.isSpectraDs3OrNotification("com.spectralogic.ds3client.commands.notifications"));
    }
}
