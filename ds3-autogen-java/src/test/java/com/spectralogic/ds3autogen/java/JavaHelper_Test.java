package com.spectralogic.ds3autogen.java;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.java.helpers.JavaHelper;
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
}
