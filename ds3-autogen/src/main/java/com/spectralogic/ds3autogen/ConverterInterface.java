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

import com.spectralogic.ds3autogen.api.ResponseTypeNotFoundException;
import com.spectralogic.ds3autogen.api.TypeRenamingConflictException;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;

import static com.spectralogic.ds3autogen.converters.MultiResponseSplitConverter.splitAllMultiResponseRequests;
import static com.spectralogic.ds3autogen.converters.NameConverter.renameRequests;
import static com.spectralogic.ds3autogen.converters.RemoveDollarSignConverter.removeDollarSigns;
import static com.spectralogic.ds3autogen.converters.ResponseTypeConverter.convertResponseTypes;


/**
 * Creates a simple interface from which to launch the various Ds3ApiSpec converters
 * with guaranteed proper order of execution.
 */
public class ConverterInterface {

    private ConverterInterface() { }

    public static Ds3ApiSpec convertSpec(final Ds3ApiSpec spec) throws ResponseTypeNotFoundException, TypeRenamingConflictException {
        return renameRequests( //Rename requests from RequestHandler to Request
                splitAllMultiResponseRequests( //Split requests with multiple response codes
                        convertResponseTypes( //Converts response types with components into new encapsulating types
                                removeDollarSigns(spec)))); //Converts all type names containing '$' into proper type names
    }
}
