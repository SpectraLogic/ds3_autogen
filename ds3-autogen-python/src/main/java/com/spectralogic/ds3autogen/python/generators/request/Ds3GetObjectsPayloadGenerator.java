/*
 * ******************************************************************************
 *   Copyright 2014-2018 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.python.generators.request;

/**
 * Generators for request payloads that have request payloads of Ds3GetObject
 */
public class Ds3GetObjectsPayloadGenerator extends AbstractObjectsPayloadGenerator {

    @Override
    String getPayloadElementType() {
        return "Ds3GetObject";
    }

    @Override
    String getPayloadListType() {
        return "Ds3GetObjectList";
    }

    @Override
    String getRequestPayloadName() {
        return "object_list";
    }
}
