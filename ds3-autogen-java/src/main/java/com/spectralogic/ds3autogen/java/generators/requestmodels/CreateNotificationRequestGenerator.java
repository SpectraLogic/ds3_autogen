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

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.java.models.Variable;

public class CreateNotificationRequestGenerator extends BaseRequestGenerator {

    private final static String ABSTRACT_CREATE_NOTIFICATION_REQUEST_IMPORT = "com.spectralogic.ds3client.commands.notifications.AbstractCreateNotificationRequest";

    /**
     * Returns the import for the parent class for the create notification request,
     * which is AbstractCreateNotificationRequest
     */
    @Override
    public String getParentImport(final Ds3Request ds3Request) {
        return ABSTRACT_CREATE_NOTIFICATION_REQUEST_IMPORT;
    }

    /**
     * Gets the list of required Arguments from a Ds3Request, excluding the parameter
     * passed to the abstract class: NotificationEndPoint. This is done to prevent it
     * from being added to the query parameters within the constructor.
     * @param ds3Request A Ds3Request
     * @return A list of required Arguments
     */
    @Override
    public ImmutableList<Arguments> toRequiredArgumentsList(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();

        for (final Arguments arg : toArgumentsList(ds3Request.getRequiredQueryParams())) {
            if (!arg.getName().equals("NotificationEndPoint")) {
                builder.add(arg);
            }
        }

        return builder.build();
    }

    /**
     * Gets all the class variables to properly generate the variables and their
     * getter functions, excluding the NotificationEndPoint variable
     */

    @Override
    public ImmutableList<Variable> toClassVariableArguments(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Variable> builder = ImmutableList.builder();

        for (final Arguments arg : toConstructorArgumentsList(ds3Request)) {
            if (!arg.getName().equals("NotificationEndPoint")) {
                builder.add(new Variable(arg.getName(), arg.getType(), true));
            }
        }
        for (final Arguments arg : toOptionalArgumentsList(ds3Request.getOptionalQueryParams())) {
            if (!arg.getName().equals("NotificationEndPoint")) {
                builder.add(new Variable(arg.getName(), arg.getType(), false));
            }
        }
        return builder.build();
    }
}
