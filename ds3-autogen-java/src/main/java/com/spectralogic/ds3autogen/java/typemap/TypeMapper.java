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

import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.java.typemap.models.Ds3TypeMapper;
import com.spectralogic.ds3autogen.java.typemap.models.Ds3TypeMapperParserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TypeMapper {

    private ImmutableMap<String, ImmutableMap<String,String>> typeMapper;
    private final static Logger LOG = LoggerFactory.getLogger(TypeMapper.class);

    public TypeMapper() {
        try {
            final Ds3TypeMapperParser parser = new Ds3TypeMapperParserImpl();
            final Ds3TypeMapper ds3TypeMapper = parser.getMap();
            this.typeMapper = ds3TypeMapper.getTypeMapper();
        } catch (final ParserException|IOException e) {
            LOG.error("Attempted to create Type Mapper", e);
        }
    }

    public ImmutableMap<String, ImmutableMap<String, String>> getTypeMapper() {
        return typeMapper;
    }

    private boolean containsArgument(final String requestName, final String argName) {
        if (typeMapper.containsKey(requestName)) {
            if (typeMapper.get(requestName).containsKey(argName)) {
                return true;
            }
        }
        return false;
    }

    public String getMappedType(final String requestName, final String argName) {
        if (containsArgument(requestName, argName)) {
            return typeMapper.get(requestName).get(argName);
        } else {
            LOG.error("Could not map type for: " + requestName + ", " + argName);
        }
        return null;
    }
}
