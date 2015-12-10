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

package com.spectralogic.ds3autogen.utils;

import com.spectralogic.ds3autogen.api.models.Action;
import com.spectralogic.ds3autogen.api.models.HttpVerb;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class Helper_Test {
    @Test
    public void camelCaseToUnderscore_Test() {
        assertEquals(Helper.getInstance().camelToUnderscore("BumpyCaseWord"), "bumpy_case_word");
    }

    @Test
    public void removeTrailingRequestHandler_Test() {
        assertEquals(Helper.getInstance().removeTrailingRequestHandler("SomeRequestHandler"), "Some");
    }

    @Test
    public void unqualifiedName_Test() {
        assertEquals(Helper.getInstance().unqualifiedName("some.qualified.name"), "name");
    }

    @Test
    public void getHttpVerb() {
        assertThat(Helper.getHttpVerb(HttpVerb.DELETE, null), is("DELETE"));
        assertThat(Helper.getHttpVerb(HttpVerb.DELETE, Action.CREATE), is("DELETE"));
        assertThat(Helper.getHttpVerb(null, Action.BULK_MODIFY), is("PUT"));
        assertThat(Helper.getHttpVerb(null, Action.CREATE), is("PUT"));
        assertThat(Helper.getHttpVerb(null, Action.DELETE), is("DELETE"));
        assertThat(Helper.getHttpVerb(null, Action.LIST), is("GET"));
        assertThat(Helper.getHttpVerb(null, Action.MODIFY), is("PUT"));
        assertThat(Helper.getHttpVerb(null, Action.SHOW), is("GET"));
        assertThat(Helper.getHttpVerb(null, Action.BULK_DELETE), is("DELETE"));

    }
}
