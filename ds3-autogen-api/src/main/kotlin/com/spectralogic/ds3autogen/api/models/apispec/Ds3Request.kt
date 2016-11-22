/*
 * ******************************************************************************
 *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.api.models.apispec

import com.google.common.collect.ImmutableList
import com.spectralogic.ds3autogen.api.models.enums.*

data class Ds3Request(
        var name: String,
        var httpVerb: HttpVerb?,
        var classification: Classification,
        var bucketRequirement: Requirement?,
        var objectRequirement: Requirement?,
        var action: Action?,
        var resource: Resource?,
        var resourceType: ResourceType?,
        var operation: Operation?,
        var includeInPath: Boolean,
        var ds3ResponseCodes: ImmutableList<Ds3ResponseCode>?,
        var optionalQueryParams: ImmutableList<Ds3Param>?,
        var requiredQueryParams: ImmutableList<Ds3Param>?)
