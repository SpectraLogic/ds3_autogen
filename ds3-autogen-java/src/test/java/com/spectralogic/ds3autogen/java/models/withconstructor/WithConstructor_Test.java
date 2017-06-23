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

package com.spectralogic.ds3autogen.java.models.withconstructor;

import com.spectralogic.ds3autogen.api.models.Arguments;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class WithConstructor_Test {

    private final Arguments TEST_ARG = new Arguments("TestType", "ArgName");
    private final String TEST_REQUEST = "TestRequest";

    @Test
    public void baseWithConstructor_Test() {
        final String expected =
                "    public TestRequest withArgName(final TestType argName) {\n" +
                "        this.argName = argName;\n" +
                "        this.updateQueryParam(\"arg_name\", argName);\n" +
                "        return this;\n" +
                "    }\n";

        final WithConstructor constructor = new BaseWithConstructor(TEST_ARG, TEST_REQUEST);
        final String result = constructor.toJavaCode();
        assertThat(result, is(expected));
    }

    @Test
    public void voidWithConstructor_Test() {
        final String expected =
                "    public TestRequest withArgName(final TestType argName) {\n" +
                "        this.argName = argName;\n" +
                "        if (this.argName) {\n" +
                "            this.getQueryParams().put(\"arg_name\", null);\n" +
                "        } else {\n" +
                "            this.getQueryParams().remove(\"arg_name\");\n" +
                "        }\n" +
                "        return this;\n" +
                "    }\n";

        final WithConstructor constructor = new VoidWithConstructor(TEST_ARG, TEST_REQUEST);
        final String result = constructor.toJavaCode();
        assertThat(result, is(expected));
    }

    @Test
     public void bulkWithConstructor_Test() {
        final String expected =
                "    @Override\n" +
                        "    public TestRequest withArgName(final TestType argName) {\n" +
                        "        super.withArgName(argName);\n" +
                        "        return this;\n" +
                        "    }\n";

        final WithConstructor constructor = new BulkWithConstructor(TEST_ARG, TEST_REQUEST);
        final String result = constructor.toJavaCode();
        assertThat(result, is(expected));
    }

    @Test
    public void maxUploadSizeWithConstructor_Test() {
        final String expected =
                "    public TestRequest withArgName(final TestType argName) {\n" +
                "        if (argName >= MIN_UPLOAD_SIZE_IN_BYTES) {\n" +
                "            this.getQueryParams().put(\"arg_name\", argName.toString());\n" +
                "        } else {\n" +
                "            this.getQueryParams().put(\"arg_name\", MAX_UPLOAD_SIZE_IN_BYTES);\n" +
                "        }\n" +
                "        return this;\n" +
                "    }\n";

        final WithConstructor constructor = new MaxUploadSizeWithConstructor(TEST_ARG, TEST_REQUEST);
        final String result = constructor.toJavaCode();
        assertThat(result, is(expected));
    }
}
