package com.spectralogic.ds3autogen.java.generators.requestmodels;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.Ds3Request;

import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isDeleteNotificationRequest;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isGetNotificationRequest;

public class NotificationRequestGenerator extends BaseRequestGenerator {

    private final static String ABSTRACT_DELETE_NOTIFICATION_REQUEST_IMPORT = "com.spectralogic.ds3client.commands.notifications.AbstractDeleteNotificationRequest";
    private final static String ABSTRACT_GET_NOTIFICATION_REQUEST_IMPORT = "com.spectralogic.ds3client.commands.notifications.AbstractGetNotificationRequest";

    //TODO write unit test
    /**
     * Returns the import for the parent class for standard requests, which
     * is AbstractRequest
     */
    @Override
    protected String getParentImport(final Ds3Request ds3Request) {
        if (isDeleteNotificationRequest(ds3Request)) {
            return ABSTRACT_DELETE_NOTIFICATION_REQUEST_IMPORT;
        }
        if (isGetNotificationRequest(ds3Request)) {
            return ABSTRACT_GET_NOTIFICATION_REQUEST_IMPORT;
        }
        throw new IllegalArgumentException("This generator only supports delete and get notification request handlers: " + ds3Request.getName());
    }

    //TODO write unit tests
    /**
     * Gets all the required imports that the Request will need in order to properly
     * generate the Java request code, always including UUID
     */
    @Override
    protected ImmutableList<String> getAllImports(
            final Ds3Request ds3Request,
            final String packageName) {
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();

        if (isSpectraDs3(packageName)) {
            builder.add(getParentImport(ds3Request));
        }

        builder.addAll(getImportsFromParamList(ds3Request.getRequiredQueryParams()));
        builder.addAll(getImportsFromParamList(ds3Request.getOptionalQueryParams()));
        builder.add("java.util.UUID");

        return builder.build().asList();
    }


}
