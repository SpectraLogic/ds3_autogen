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

package com.spectralogic.ds3autogen.python.model.commands;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.python.model.request.BaseRequest;

public class CommandSet {

    /** Models describing all Request Handlers */
    private final ImmutableList<BaseRequest> requests;

    public CommandSet(final ImmutableList<BaseRequest> requests) {
        this.requests = requests;
    }

    public ImmutableList<BaseRequest> getRequests() {
        return requests;
    }
}
