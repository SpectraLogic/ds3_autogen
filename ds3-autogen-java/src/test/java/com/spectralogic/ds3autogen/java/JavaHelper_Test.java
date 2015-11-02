package com.spectralogic.ds3autogen.java;

import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.java.helpers.JavaHelper;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JavaHelper_Test {

    private static final JavaHelper javaHelper = JavaHelper.getInstance();

    @Test
    public void bulkWithPriorityConstructor() {
        final String expectedResult =
                        "    @Override\n" +
                        "    public CreatePutJobRequestHandler withPriority(final Priority priority) {\n" +
                        "        super.withPriority(priority);\n" +
                        "        return this;\n" +
                        "    }\n";
        final Arguments arg = new Arguments("Priority", "Priority");
        final String result = javaHelper.createWithConstructorBulk(arg, "CreatePutJobRequestHandler");
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
        final String result = javaHelper.createWithConstructorBulk(arg, "CreatePutJobRequestHandler");
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
        final String result = javaHelper.createWithConstructorBulk(arg, "GetJobsRequestHandler");
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
        final String result = javaHelper.createWithConstructorBulk(arg, "GetBucketRequestHandler");
        assertThat(result, is(expectedResult));
    }

    @Test
    public void argsToList() {
        final String expectedResult = "arg1, arg2, arg3";
        final List<Arguments> arguments = Arrays.asList(
                new Arguments("type1", "Arg1"),
                new Arguments("type1", "Arg2"),
                new Arguments("type1", "Arg3"));
        final String result = javaHelper.argsToList(arguments);
        assertThat(result, is(expectedResult));
    }
}
