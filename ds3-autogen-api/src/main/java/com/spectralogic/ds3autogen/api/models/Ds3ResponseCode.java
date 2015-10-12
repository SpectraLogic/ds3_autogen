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

package com.spectralogic.ds3autogen.api.models;

import java.util.List;

public class Ds3ResponseCode {

    private String code;
    private List<Ds3ResponseType> ds3ResponseTypes;

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public List<Ds3ResponseType> getDs3ResponseTypes() {
        return ds3ResponseTypes;
    }

    public void setDs3ResponseTypes(final List<Ds3ResponseType> ds3ResponseType) {
        this.ds3ResponseTypes = ds3ResponseType;
    }
}
