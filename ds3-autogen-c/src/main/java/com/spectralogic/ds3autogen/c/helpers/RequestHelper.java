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

package com.spectralogic.ds3autogen.c.helpers;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseType;
import com.spectralogic.ds3autogen.utils.ConverterUtil;
import com.spectralogic.ds3autogen.utils.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public final class RequestHelper {
    private static final Logger LOG = LoggerFactory.getLogger(RequestHelper.class);
    private final static RequestHelper requestHelper = new RequestHelper();

    public static RequestHelper getInstance() {
        return requestHelper;
    }

    public static String getNameRoot(final String name) {
        return Helper.removeTrailingRequestHandler(Helper.unqualifiedName(name));
    }

    public static String getNameRootUnderscores(final String name) {
        return Helper.camelToUnderscore(getNameRoot(name));
    }

    public static boolean hasResponsePayload(final ImmutableList<Ds3ResponseCode> responseCodes) {
        for (final Ds3ResponseCode responseCode : responseCodes) {
            final int rc = responseCode.getCode();
            if (rc >= 200 && rc < 300)
            for (final Ds3ResponseType responseType : responseCode.getDs3ResponseTypes()) {
                if (ConverterUtil.hasContent(responseType.getType())
                    && !Objects.equals(responseType.getType(), "null")) {
                    LOG.debug("response type " + responseType.getType());
                    return true;
                } else if (ConverterUtil.hasContent(responseType.getComponentType())
                           && !Objects.equals(responseType.getComponentType(), "null")) {
                    LOG.debug("response type " + responseType.getComponentType());
                    return true;
                }
            }
        }
        return false;
    }
}
