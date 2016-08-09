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

package com.spectralogic.ds3autogen;

import com.spectralogic.ds3autogen.api.Ds3NameMapperParser;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.models.enums.Classification;
import com.spectralogic.ds3autogen.api.models.namemap.Ds3NameMapper;
import com.spectralogic.ds3autogen.api.models.namemap.NameMapperType;

import java.io.IOException;
import java.io.InputStream;

public class NameMapper {

    private static final String DEFAULT_TYPE_NAME_MAP_FILE = "/typeNameMap.json";
    private final Ds3NameMapper ds3NameMapper;

    public NameMapper() throws IOException, ParserException {
        ds3NameMapper = initNameMapper(DEFAULT_TYPE_NAME_MAP_FILE);
    }

    public NameMapper(final String typeMapFile) throws IOException, ParserException {
        ds3NameMapper = initNameMapper(typeMapFile);
    }

    private static Ds3NameMapper initNameMapper(final String typeMapFile) throws IOException, ParserException {
        final InputStream inputStream = Ds3NameMapperParserImpl.class.getResourceAsStream(typeMapFile);
        final Ds3NameMapperParser parser = new Ds3NameMapperParserImpl();
        return parser.getMap(inputStream);
    }

    public boolean containsName(final String namePath) {
        return ds3NameMapper.containsType(namePath);
    }

    public String getConvertedName(final String namePath, final Classification classification) {
        if (classification == Classification.amazons3) {
            return ds3NameMapper.getConvertedName(namePath, NameMapperType.AMAZONS3);
        }
        if (classification == Classification.spectrads3) {
            return ds3NameMapper.getConvertedName(namePath, NameMapperType.SPECTRADS3);
        }
        return ds3NameMapper.getConvertedName(namePath, NameMapperType.NONE);
    }
}
