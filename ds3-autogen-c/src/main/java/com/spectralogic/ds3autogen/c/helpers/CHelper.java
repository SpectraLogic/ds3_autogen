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


import com.spectralogic.ds3autogen.utils.Helper;


public class CHelper {
    private static Helper helper;
    private static RequestHelper requestHelper;
    private static TypeHelper typeHelper;
    private static ElementHelper elementHelper;

    private final static CHelper cHelper = new CHelper();

    private CHelper() {
        helper = Helper.getInstance();
        requestHelper = RequestHelper.getInstance();
        typeHelper = TypeHelper.getInstance();
        elementHelper = ElementHelper.getInstance();
    }

    public static CHelper getInstance() {
        return cHelper;
    }

    public static Helper getHelper() {
        return helper;
    }

    public static RequestHelper getRequestHelper() {
        return requestHelper;
    }

    public static TypeHelper getTypeHelper() {
        return typeHelper;
    }

    public static ElementHelper getElementHelper() {
        return elementHelper;
    }
}
