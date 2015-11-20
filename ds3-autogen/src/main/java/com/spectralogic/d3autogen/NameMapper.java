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

package com.spectralogic.d3autogen;

import com.spectralogic.ds3autogen.api.Ds3NameMapperParser;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.models.Ds3NameMapper;

import java.io.IOException;

public class NameMapper {

    private static final NameMapper nameMapper;
    private final Ds3NameMapper ds3NameMapper;

    static {
        try {
            nameMapper = new NameMapper();
        } catch (final IOException|ParserException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private NameMapper() throws IOException, ParserException {
        ds3NameMapper = initNameMapper();
    }

    private static Ds3NameMapper initNameMapper() throws IOException, ParserException {
        final Ds3NameMapperParser parser = new Ds3NameMapperParserImpl();
        return parser.getMap();
    }

    public static NameMapper getInstance() {
        return nameMapper;
    }

    public boolean containsName(final String namePath) {
        return ds3NameMapper.containsType(namePath);
    }

    public String getConvertedName(final String namePath) {
        return ds3NameMapper.getConvertedName(namePath);
    }
}
