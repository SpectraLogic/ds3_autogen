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

package com.spectralogic.ds3autogen.go.generators.request

import com.spectralogic.ds3autogen.api.models.Arguments
import com.spectralogic.ds3autogen.go.models.request.Variable

/**
 * Go generator for request handlers that take in a list of object names
 * and convert them into the payload:
 *   <Objects><Object Name="o1" /><Object Name="o2" />...</Objects>
 *
 * Used to generate the commands:
 *   GetPhysicalPlacementForObjectsSpectraS3Request
 *   GetPhysicalPlacementForObjectsWithFullDetailsSpectraS3Request
 *   VerifyPhysicalPlacementForObjectsWithFullDetailsSpectraS3Request
 *   VerifyPhysicalPlacementForObjectsSpectraS3Request
 *   EjectStorageDomainBlobsSpectraS3Request
 */
class ObjectNamesPayloadGenerator : RequestPayloadGenerator() {

    /**
     * Retrieves the list of object names that make up the request payload
     */
    override fun getPayloadArgument(): Arguments {
        return Arguments("[]string", "ObjectNames")
    }

    /**
     * Retrieves the struct assignment for the Ds3Object list request payload
     */
    override fun getStructAssignmentVariable(): Variable {
        return Variable("ObjectNames", "objectNames")
    }
}
