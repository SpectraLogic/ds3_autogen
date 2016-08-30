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

package com.spectralogic.ds3autogen.docspec;

import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;

import java.util.Optional;

/**
 * Implementation of an empty Ds3DocSpec. This is used when generating code
 * without documentation (i.e. no-doc option selected in cli).
 */
public class Ds3DocSpecEmptyImpl implements Ds3DocSpec {

    @Override
    public Optional<String> getRequestDocumentation(final String requestName) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getParamDocumentation(final String paramName) {
        return Optional.empty();
    }
}
