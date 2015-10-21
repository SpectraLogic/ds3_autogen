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

package com.spectralogic.ds3autogen.java.typemap;

import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.java.typemap.models.Ds3TypeMapper;
import com.spectralogic.ds3autogen.java.typemap.models.Ds3TypeMapperParserImpl;

import java.io.IOException;

public class TypeMapper {

    private static final TypeMapper typeMapper;
    private final Ds3TypeMapper ds3TypeMapper;

    static {
        try {
            typeMapper = new TypeMapper();
        } catch (final IOException|ParserException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private TypeMapper() throws IOException, ParserException {
        ds3TypeMapper = initTypeMapper();
    }

    private static Ds3TypeMapper initTypeMapper() throws IOException, ParserException {
        final Ds3TypeMapperParser parser = new Ds3TypeMapperParserImpl();
        return parser.getMap();
    }

    public static TypeMapper getInstance() {
        return typeMapper;
    }

    public String getMappedType(final String requestName, final String argName) {
        return ds3TypeMapper.getMappedType(requestName, argName);
    }
}
