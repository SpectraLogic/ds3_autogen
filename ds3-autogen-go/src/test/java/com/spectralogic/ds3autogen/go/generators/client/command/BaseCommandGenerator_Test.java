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

package com.spectralogic.ds3autogen.go.generators.client.command;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Param;
import com.spectralogic.ds3autogen.api.models.enums.HttpVerb;
import com.spectralogic.ds3autogen.api.models.enums.Operation;
import com.spectralogic.ds3autogen.go.models.client.*;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getBucketRequest;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestCreateObject;
import static org.assertj.core.api.Assertions.assertThat;

public class BaseCommandGenerator_Test {

    private final BaseCommandGenerator generator = new BaseCommandGenerator();

    @Test
    public void toRequestBuildLinesPutObjectTest() {
        final ImmutableList<RequestBuildLine> result = generator.toRequestBuildLines(getRequestCreateObject());

        assertThat(result)
                .containsExactlyInAnyOrder(
                        new HttpVerbBuildLine(HttpVerb.PUT),
                        new PathBuildLine("\"/\" + request.BucketName + \"/\" + request.ObjectName"),
                        new OptionalQueryParamBuildLine("job", "request.Job"),
                        new OptionalQueryParamBuildLine("offset", "networking.Int64PtrToStrPtr(request.Offset)")
                );
    }

    @Test
    public void toRequestBuildLinesGetBucketTest() {
        final ImmutableList<RequestBuildLine> result = generator.toRequestBuildLines(getBucketRequest());

        assertThat(result)
                .containsExactlyInAnyOrder(
                        new HttpVerbBuildLine(HttpVerb.GET),
                        new PathBuildLine("\"/\" + request.BucketName"),
                        new OptionalQueryParamBuildLine("delimiter", "request.Delimiter"),
                        new OptionalQueryParamBuildLine("marker", "request.Marker"),
                        new OptionalQueryParamBuildLine("max_keys", "networking.IntPtrToStrPtr(request.MaxKeys)"),
                        new OptionalQueryParamBuildLine("prefix", "request.Prefix")
                );
    }

    @Test
    public void toHeadersBuildLineTest() {
        assertThat(generator.toHeadersBuildLine().isPresent()).isFalse();
    }

    @Test
    public void toChecksumBuildLineTest() {
        assertThat(generator.toChecksumBuildLine().isPresent()).isFalse();
    }

    @Test
    public void toReaderBuildLineTest() {
        assertThat(generator.toReaderBuildLine().isPresent()).isFalse();
    }

    @Test
    public void toOperationBuildLineTest() {
        assertThat(generator.toOperationBuildLine(null).isPresent()).isFalse();

        assertThat(generator.toOperationBuildLine(Operation.GET_PHYSICAL_PLACEMENT))
                .isNotEmpty()
                .contains(new OperationBuildLine(Operation.GET_PHYSICAL_PLACEMENT));

        assertThat(generator.toOperationBuildLine(Operation.ALLOCATE))
                .isNotEmpty()
                .contains(new OperationBuildLine(Operation.ALLOCATE));

        assertThat(generator.toOperationBuildLine(Operation.VERIFY_SAFE_TO_START_BULK_PUT))
                .isNotEmpty()
                .contains(new OperationBuildLine(Operation.VERIFY_SAFE_TO_START_BULK_PUT));
    }

    @Test
    public void toOptionalQueryParamBuildLinePrimitiveTypeTest() {
        final Ds3Param param = new Ds3Param("ParamName", "bool", false);
        final RequestBuildLine result = generator.toOptionalQueryParamBuildLine(param);

        assertThat(result.getLine())
                .isEqualTo("WithOptionalQueryParam(\"param_name\", networking.BoolPtrToStrPtr(request.ParamName)).");
    }

    @Test
    public void toOptionalQueryParamBuildLineJavaObjectTypeTest() {
        final Ds3Param param = new Ds3Param("ParamName", "java.lang.Boolean", false);
        final RequestBuildLine result = generator.toOptionalQueryParamBuildLine(param);

        assertThat(result.getLine())
                .isEqualTo("WithOptionalQueryParam(\"param_name\", networking.BoolPtrToStrPtr(request.ParamName)).");
    }

    @Test
    public void toOptionalQueryParamBuildLineSpectraTypeTest() {
        final Ds3Param param = new Ds3Param("ParamName", "com.test.SpectraType", false);
        final RequestBuildLine result = generator.toOptionalQueryParamBuildLine(param);

        assertThat(result.getLine())
                .isEqualTo("WithOptionalQueryParam(\"param_name\", networking.InterfaceToStrPtr(request.ParamName)).");
    }

    @Test
    public void toOptionalQueryParamBuildLineVoidTypeTest() {
        final Ds3Param param = new Ds3Param("ParamName", "void", false);
        final RequestBuildLine result = generator.toOptionalQueryParamBuildLine(param);

        assertThat(result.getLine())
                .isEqualTo("WithOptionalVoidQueryParam(\"param_name\", request.ParamName).");
    }

    @Test
    public void toOptionalQueryParamBuildLinesTest() {
        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("ParamName", "bool", false),
                new Ds3Param("ParamName", "com.test.SpectraType", false),
                new Ds3Param("ParamName", "void", false)
        );

        final ImmutableList<RequestBuildLine> result = generator.toOptionalQueryParamBuildLines(params);

        assertThat(result).extracting("line").containsExactlyInAnyOrder(
                "WithOptionalQueryParam(\"param_name\", networking.BoolPtrToStrPtr(request.ParamName)).",
                "WithOptionalQueryParam(\"param_name\", networking.InterfaceToStrPtr(request.ParamName)).",
                "WithOptionalVoidQueryParam(\"param_name\", request.ParamName).");
    }

    @Test
    public void toRequiredQueryParamBuildLineVoidTest() {
        final Ds3Param param = new Ds3Param("ParamName", "void", false);
        final RequestBuildLine result = generator.toRequiredQueryParamBuildLine(param);
        assertThat(result.getLine()).isEqualTo("WithQueryParam(\"param_name\", \"\").");
    }

    @Test
    public void toRequiredQueryParamBuildLinePrimitiveTest() {
        final Ds3Param param = new Ds3Param("ParamName", "int", false);
        final RequestBuildLine result = generator.toRequiredQueryParamBuildLine(param);
        assertThat(result.getLine()).isEqualTo("WithQueryParam(\"param_name\", strconv.Itoa(request.ParamName)).");
    }

    @Test
    public void toRequiredQueryParamBuildLineInterfaceTest() {
        final Ds3Param param = new Ds3Param("ParamName", "com.test.SpectraType", false);
        final RequestBuildLine result = generator.toRequiredQueryParamBuildLine(param);
        assertThat(result.getLine()).isEqualTo("WithQueryParam(\"param_name\", request.ParamName.String()).");
    }

    @Test
    public void toRequiredQueryParamBuildLinesTest() {
        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("ParamName", "bool", false),
                new Ds3Param("ParamName", "com.test.SpectraType", false),
                new Ds3Param("ParamName", "void", false)
        );

        final ImmutableList<RequestBuildLine> result = generator.toRequiredQueryParamBuildLines(params);

        assertThat(result).extracting("line").containsExactlyInAnyOrder(
                "WithQueryParam(\"param_name\", strconv.FormatBool(request.ParamName)).",
                "WithQueryParam(\"param_name\", request.ParamName.String()).",
                "WithQueryParam(\"param_name\", \"\").");
    }
}
