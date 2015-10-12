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

package com.spectralogic.d3autogen.models.xml;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseType;

import java.util.List;

public class ResponseCode {

    @JsonProperty("Code")
    private String code;

    @JsonProperty("ResponseTypes")
    @JacksonXmlElementWrapper(useWrapping = true)
    private List<ResponseType> responseTypes;

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public List<ResponseType> getResponseTypes() {
        return responseTypes;
    }

    public void setResponseTypes(final List<ResponseType> responseTypes) {
        this.responseTypes = responseTypes;
    }

    public Ds3ResponseCode toDs3ResponseCode() {
        final Ds3ResponseCode ds3ResponseCode = new Ds3ResponseCode(code, toDs3ResponseTypes());
        return ds3ResponseCode;
    }

    private ImmutableList<Ds3ResponseType> toDs3ResponseTypes() {
        final ImmutableList.Builder<Ds3ResponseType> ds3ResponseTypesBuilder = ImmutableList.builder();
        for(final ResponseType responseType : responseTypes) {
            ds3ResponseTypesBuilder.add(responseType.toDs3ResponseType());
        }
        return ds3ResponseTypesBuilder.build();
    }
}
