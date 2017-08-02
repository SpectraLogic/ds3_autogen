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

package com.spectralogic.ds3autogen.java.generators.requestmodels;

import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;

/**
 * Generator for Java Request handlers that have a request payload of format
 * <Ids><Id>id1</Id><Id>id2</Id>...</Ids>
 *
 * These requests extend the AbstractIdsPayloadRequest
 */
public class IdsRequestPayloadGenerator extends BaseRequestGenerator {

    private final static String ABSTRACT_IDS_PAYLOAD_REQUEST = "com.spectralogic.ds3client.commands.interfaces.AbstractIdsPayloadRequest";

    /**
     * Returns the import for the parent class for the create notification request,
     * which is AbstractIdsPayloadRequest
     */
    @Override
    public String getParentImport(final Ds3Request ds3Request) {
        return ABSTRACT_IDS_PAYLOAD_REQUEST;
    }
}
