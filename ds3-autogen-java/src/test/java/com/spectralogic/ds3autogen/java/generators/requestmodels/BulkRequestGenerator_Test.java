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

package com.spectralogic.ds3autogen.java.generators.requestmodels;

import org.junit.Test;

import static com.spectralogic.ds3autogen.java.generators.requestmodels.BulkRequestGenerator.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BulkRequestGenerator_Test {

    @Test
    public void isBulkRequestArg_Test() {
        assertTrue(isBulkRequestArg("Priority"));
        assertTrue(isBulkRequestArg("WriteOptimization"));
        assertTrue(isBulkRequestArg("BucketName"));
        assertFalse(isBulkRequestArg("ChunkClientProcessingOrderGuarantee"));
    }
}
