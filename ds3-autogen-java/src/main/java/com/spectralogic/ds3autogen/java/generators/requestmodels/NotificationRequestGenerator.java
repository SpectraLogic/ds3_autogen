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
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.java.models.RequestConstructor;

import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isDeleteNotificationRequest;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isGetNotificationRequest;

public class NotificationRequestGenerator extends BaseRequestGenerator {

    private final static String ABSTRACT_DELETE_NOTIFICATION_REQUEST_IMPORT = "com.spectralogic.ds3client.commands.interfaces.AbstractDeleteNotificationRequest";
    private final static String ABSTRACT_GET_NOTIFICATION_REQUEST_IMPORT = "com.spectralogic.ds3client.commands.interfaces.AbstractGetNotificationRequest";

    /**
     * Returns the import for the parent class for standard requests, which
     * is AbstractRequest
     */
    @Override
    public String getParentImport(final Ds3Request ds3Request) {
        if (isDeleteNotificationRequest(ds3Request)) {
            return ABSTRACT_DELETE_NOTIFICATION_REQUEST_IMPORT;
        }
        if (isGetNotificationRequest(ds3Request)) {
            return ABSTRACT_GET_NOTIFICATION_REQUEST_IMPORT;
        }
        throw new IllegalArgumentException("This generator only supports delete and get notification request handlers: " + ds3Request.getName());
    }

    /**
     * Gets all the required imports that the Request will need in order to properly
     * generate the Java request code, always including UUID
     */
    @Override
    public ImmutableList<String> getAllImports(
            final Ds3Request ds3Request,
            final String packageName) {
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();

        builder.add(getParentImport(ds3Request));
        builder.addAll(getImportsFromParamList(ds3Request.getRequiredQueryParams()));
        builder.addAll(getImportsFromParamList(ds3Request.getOptionalQueryParams()));
        builder.add("java.util.UUID");

        return builder.build().asList();
    }

    /**
     * Gets the list of constructor models from a Ds3Request. The notification
     * constructor will have the parameter NotificationId
     */
    @Override
    public ImmutableList<RequestConstructor> toConstructorList(final Ds3Request ds3Request) {
        final ImmutableList<Arguments> constructorArgs = toConstructorArgumentsList(ds3Request);

        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(constructorArgs);
        builder.add(new Arguments("UUID", "NotificationId"));

        final RequestConstructor constructor = new RequestConstructor(
                builder.build(),
                constructorArgs,
                toQueryParamsList(ds3Request));

        return ImmutableList.of(constructor);
    }
}
