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

package com.spectralogic.ds3autogen.go.models.client;

import com.spectralogic.ds3autogen.api.models.enums.HttpVerb;
import com.spectralogic.ds3autogen.api.models.enums.Operation;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestBuildLine_Test {

    @Test
    public void httpVerbBuildLineTest() {
        assertThat(new HttpVerbBuildLine(HttpVerb.GET).getLine())
                .isEqualTo("WithHttpVerb(HTTP_VERB_GET).");
        assertThat(new HttpVerbBuildLine(HttpVerb.PUT).getLine())
                .isEqualTo("WithHttpVerb(HTTP_VERB_PUT).");
        assertThat(new HttpVerbBuildLine(HttpVerb.POST).getLine())
                .isEqualTo("WithHttpVerb(HTTP_VERB_POST).");
        assertThat(new HttpVerbBuildLine(HttpVerb.DELETE).getLine())
                .isEqualTo("WithHttpVerb(HTTP_VERB_DELETE).");
        assertThat(new HttpVerbBuildLine(HttpVerb.HEAD).getLine())
                .isEqualTo("WithHttpVerb(HTTP_VERB_HEAD).");
    }

    @Test
    public void pathBuildLineTest() {
        assertThat(new PathBuildLine("\"/_rest_/my-path\"").getLine())
                .isEqualTo("WithPath(\"/_rest_/my-path\").");
    }

    @Test
    public void operationBuildLineTest() {
        assertThat(new OperationBuildLine(Operation.ALLOCATE).getLine())
                .isEqualTo("WithQueryParam(\"operation\", \"allocate\").");
        assertThat(new OperationBuildLine(Operation.GET_PHYSICAL_PLACEMENT).getLine())
                .isEqualTo("WithQueryParam(\"operation\", \"get_physical_placement\").");
        assertThat(new OperationBuildLine(Operation.VERIFY_SAFE_TO_START_BULK_PUT).getLine())
                .isEqualTo("WithQueryParam(\"operation\", \"verify_safe_to_start_bulk_put\").");
    }

    @Test
    public void queryParamBuildLineTest() {
        assertThat(new QueryParamBuildLine("ParamKey", "ParamValue").getLine())
                .isEqualTo("WithQueryParam(\"ParamKey\", ParamValue).");
    }

    @Test
    public void optionalQueryParamBuildLineTest() {
        assertThat(new OptionalQueryParamBuildLine("ParamKey", "ParamValue").getLine())
                .isEqualTo("WithOptionalQueryParam(\"ParamKey\", ParamValue).");
    }

    @Test
    public void voidOptionalQueryParamBuildLineTest() {
        assertThat(new VoidOptionalQueryParamBuildLine("ParamKey", "ParamName").getLine())
                .isEqualTo("WithOptionalVoidQueryParam(\"ParamKey\", request.ParamName).");
    }
}
