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

package com.spectralogic.ds3autogen.java.generators.requestmodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.java.models.Variable;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.test.helpers.RequestGeneratorTestHelper.createSimpleTestDs3Request;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CreateNotificationRequestGenerator_Test {

    private final static CreateNotificationRequestGenerator generator = new CreateNotificationRequestGenerator();

    @Test
    public void getParentImport_Test() {
        assertThat(generator.getParentImport(null), is("com.spectralogic.ds3client.commands.notifications.AbstractCreateNotificationRequest"));
    }

    @Test
    public void toRequiredArgumentsList_Test() {
        final Ds3Request request = createSimpleTestDs3Request();

        final ImmutableList<Arguments> result = generator.toRequiredArgumentsList(request);
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("Priority"));
    }

    @Test
    public void toClassVariableArguments_Test() {
        final Ds3Request request = createSimpleTestDs3Request();

        final ImmutableList<Variable> result = generator.toClassVariableArguments(request);
        assertThat(result.size(), is(5));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(1).getName(), is("ObjectName"));
        assertThat(result.get(2).getName(), is("JobId"));
        assertThat(result.get(3).getName(), is("Priority"));
        assertThat(result.get(4).getName(), is("RequestType"));
    }
}
