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

package com.spectralogic.ds3autogen.java.helper;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.java.models.Arguments;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Helper {

    private static final Helper helper = new Helper();

    private Helper() {}

    public static Helper getInstance() {
        return helper;
    }

    public static String camelToUnderscore(final String str) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, str);
    }

    public static String argToString(final Arguments arg) {
        if (arg.getType().equals("String")) {
            return StringUtils.uncapitalize(arg.getName());
        } else {
            return StringUtils.uncapitalize(arg.getName()) + ".toString()";
        }
    }

    public static String constructorArgs(final ImmutableList<Arguments> requiredArguments) {
        if (requiredArguments.isEmpty()) {
            return "";
        }

        final List<String> argArray = new ArrayList<>();

        final Iterator<String> argIter = requiredArguments.stream().map(a -> "final " + a.getType() + " "
                + StringUtils.uncapitalize(a.getName())).iterator();

        while(argIter.hasNext()) {
            argArray.add(argIter.next());
        }

        return String.join(", ", argArray);
    }
}
