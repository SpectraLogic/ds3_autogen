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

package com.spectralogic.ds3autogen.api.utils;

import java.util.List;
import java.util.Map;

public class ConverterUtil {

    public static boolean hasContent(final List<?> list) {
        return !isEmpty(list);
    }

    public static boolean hasContent(final Map<?,?> map) {
        return !isEmpty(map);
    }

    public static boolean hasContent(final String string) {
        return !isEmpty(string);
    }

    public static boolean isEmpty(final List<?> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isEmpty(final Map<?,?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isEmpty(final String string) {
        return string == null || string.isEmpty();
    }
}
