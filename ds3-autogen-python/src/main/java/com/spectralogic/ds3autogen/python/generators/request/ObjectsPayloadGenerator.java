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

package com.spectralogic.ds3autogen.python.generators.request;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.python.model.request.ConstructorParam;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;
import com.spectralogic.ds3autogen.utils.comparators.CustomArgumentComparator;

import static com.spectralogic.ds3autogen.python.helpers.PythonHelper.pythonIndent;
import static com.spectralogic.ds3autogen.python.utils.GeneratorUtils.hasRequiredFileObjectListPayload;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.*;
import static com.spectralogic.ds3autogen.utils.Helper.camelToUnderscore;
import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.getNonVoidArgsFromParamList;

/**
 * Creates the python request model for commands with request payloads of objects list and part list.
 */
public class ObjectsPayloadGenerator extends BaseRequestGenerator {

    private static final String OBJECTS_NAME = "object_list";
    private static final String FILE_OBJECTS_TYPE = "FileObjectList";
    private static final String DELETE_OBJECTS_TYPE = "DeleteObjectList";

    private static final String PARTS_NAME = "part_list";
    private static final String PARTS_TYPE = "PartList";

    /**
     * Gets the sorted list of required constructor parameters including the request payload
     */
    @Override
    public ImmutableList<ConstructorParam> toRequiredConstructorParams(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(getNonVoidArgsFromParamList(ds3Request.getRequiredQueryParams()));
        builder.addAll(getAssignmentArguments(ds3Request));
        if (hasRequiredFileObjectListPayload(ds3Request)) {
            builder.add(new Arguments(FILE_OBJECTS_TYPE, OBJECTS_NAME));
        }
        if (isMultiFileDeleteRequest(ds3Request)) {
            builder.add(new Arguments(DELETE_OBJECTS_TYPE, OBJECTS_NAME));
        }
        if (isCompleteMultiPartUploadRequest(ds3Request)) {
            builder.add(new Arguments(PARTS_TYPE, PARTS_NAME));
        }

        return builder.build().stream()
                .sorted(new CustomArgumentComparator())
                .map(arg -> new ConstructorParam(camelToUnderscore(arg.getName()), false))
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Gets the sorted list of optional constructor parameters including the request payload
     */
    @Override
    public ImmutableList<ConstructorParam> toOptionalConstructorParams(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(toOptionalArgumentsList(ds3Request.getOptionalQueryParams()));
        if (isEjectStorageDomainBlobsRequest(ds3Request)) {
            builder.add(new Arguments(FILE_OBJECTS_TYPE, OBJECTS_NAME));
        }

        return builder.build().stream()
                .sorted(new CustomArgumentComparator())
                .map(arg -> new ConstructorParam(camelToUnderscore(arg.getName()), true))
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Gets the python code for assigning the payload type to the body of the request
     */
    @Override
    public String getAdditionalContent(final Ds3Request ds3Request, final String requestName) {
        if (isEjectStorageDomainBlobsRequest(ds3Request)) {
            return toPayloadAssignment(requestName, OBJECTS_NAME, FILE_OBJECTS_TYPE);
        }
        if (hasRequiredFileObjectListPayload(ds3Request)) {
            return toPayloadAssignment(requestName, OBJECTS_NAME, FILE_OBJECTS_TYPE);
        }
        if (isMultiFileDeleteRequest(ds3Request)) {
            return toPayloadAssignment(requestName, OBJECTS_NAME, DELETE_OBJECTS_TYPE);
        }
        if (isCompleteMultiPartUploadRequest(ds3Request)) {
            return toPayloadAssignment(requestName, PARTS_NAME, PARTS_TYPE);
        }
        throw new IllegalArgumentException("The Ds3Request does not have an objects request payload: " + ds3Request.getName());
    }

    /**
     * Creates the python code for assigning the specified payload type to the body of the request
     */
    protected static String toPayloadAssignment(
            final String requestName,
            final String payloadName,
            final String payloadType) {
        return  "if " + payloadName + " is not None:\n"
                + pythonIndent(3) + "if not isinstance(" + payloadName + ", " + payloadType + "):\n"
                + pythonIndent(4) + "raise TypeError('" + requestName + " should have request payload of type: " + payloadType + "')\n"
                + pythonIndent(3) + "self.body = xmldom.tostring(" + payloadName + ".to_xml())\n";
    }
}
