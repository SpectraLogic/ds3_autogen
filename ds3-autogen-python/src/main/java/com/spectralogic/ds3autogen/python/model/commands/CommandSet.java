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

package com.spectralogic.ds3autogen.python.model.commands;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.python.model.client.BaseClient;
import com.spectralogic.ds3autogen.python.model.request.BaseRequest;
import com.spectralogic.ds3autogen.python.model.response.BaseResponse;
import com.spectralogic.ds3autogen.python.model.type.TypeDescriptor;

public class CommandSet {

    /** Models describing all Request Handlers */
    private final ImmutableList<BaseRequest> requests;

    /** Models describing all Response Handlers */
    private final ImmutableList<BaseResponse> responses;

    /** Models describing how to parse models */
    private final ImmutableList<TypeDescriptor> typeDescriptors;

    /** Models describing the client */
    private final ImmutableList<BaseClient> clientCommands;

    public CommandSet(
            final ImmutableList<BaseRequest> requests,
            final ImmutableList<BaseResponse> responses,
            final ImmutableList<TypeDescriptor> typeDescriptors,
            final ImmutableList<BaseClient> clientCommands) {
        this.requests = requests;
        this.responses = responses;
        this.typeDescriptors = typeDescriptors;
        this.clientCommands = clientCommands;
    }

    public ImmutableList<BaseRequest> getRequests() {
        return requests;
    }

    public ImmutableList<TypeDescriptor> getTypeDescriptors() {
        return typeDescriptors;
    }

    public ImmutableList<BaseResponse> getResponses() {
        return responses;
    }

    public ImmutableList<BaseClient> getClientCommands() {
        return clientCommands;
    }
}
