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

package com.spectralogic.ds3autogen.net.model.client;

import com.google.common.collect.ImmutableList;

public class BaseClient {

    private final ImmutableList<PayloadCommand> payloadCommands;
    private final ImmutableList<VoidCommand> voidCommands;
    private final ImmutableList<SpecializedCommand> specializedCommands;

    public BaseClient(
            final ImmutableList<PayloadCommand> payloadCommands,
            final ImmutableList<VoidCommand> voidCommands,
            final ImmutableList<SpecializedCommand> specializedCommands) {
        this.payloadCommands = payloadCommands;
        this.voidCommands = voidCommands;
        this.specializedCommands = specializedCommands;
    }

    public ImmutableList<PayloadCommand> getPayloadCommands() {
        return payloadCommands;
    }

    public ImmutableList<VoidCommand> getVoidCommands() {
        return voidCommands;
    }

    public ImmutableList<SpecializedCommand> getSpecializedCommands() {
        return specializedCommands;
    }
}
