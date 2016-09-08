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
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecEmptyImpl;
import com.spectralogic.ds3autogen.java.models.RequestConstructor;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.test.helpers.RequestGeneratorTestHelper.createSimpleTestDs3Request;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestDeleteNotification;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestGetNotification;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class NotificationRequestGenerator_Test {

    private static final NotificationRequestGenerator generator = new NotificationRequestGenerator();

    @Test
    public void getParentImport_Delete_Test() {
        final Ds3Request request = getRequestDeleteNotification();
        final String result = generator.getParentImport(request);
        assertThat(result, is("com.spectralogic.ds3client.commands.interfaces.AbstractDeleteNotificationRequest"));
    }

    @Test
    public void getParentImport_Get_Test() {
        final Ds3Request request = getRequestGetNotification();
        final String result = generator.getParentImport(request);
        assertThat(result, is("com.spectralogic.ds3client.commands.interfaces.AbstractGetNotificationRequest"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getParentImport_Exception_Test() {
        final Ds3Request request = createSimpleTestDs3Request();
        generator.getParentImport(request);
    }

    @Test
    public void getAllImports_Delete_Test() {
        final Ds3Request request = getRequestDeleteNotification();

        final ImmutableList<String> result = generator.getAllImports(
                request,
                "com.spectralogic.ds3client.commands.spectrads3.notifications");

        assertThat(result.size(), is(2));
        assertTrue(result.contains("com.spectralogic.ds3client.commands.interfaces.AbstractDeleteNotificationRequest"));
        assertTrue(result.contains("java.util.UUID"));
    }

    @Test
    public void getAllImports_Get_Test() {
        final Ds3Request request = getRequestGetNotification();

        final ImmutableList<String> result = generator.getAllImports(
                request,
                "com.spectralogic.ds3client.commands.spectrads3.notifications");

        assertThat(result.size(), is(2));
        assertTrue(result.contains("com.spectralogic.ds3client.commands.interfaces.AbstractGetNotificationRequest"));
        assertTrue(result.contains("java.util.UUID"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllImports_Exception_Test() {
        final Ds3Request request = createSimpleTestDs3Request();
        generator.getAllImports(request, "com.spectralogic.ds3client.commands.spectrads3.notifications");
    }

    @Test
    public void toConstructorList_Test() {
        final Ds3Request request = getRequestDeleteNotification();

        final ImmutableList<RequestConstructor> result = generator.toConstructorList(request, "", new Ds3DocSpecEmptyImpl());
        assertThat(result.size(), is(1));

        final RequestConstructor constructor = result.get(0);
        assertThat(constructor.getAdditionalLines().size(), is(0));
        assertThat(constructor.isDeprecated(), is(false));

        final ImmutableList<Arguments> constructorParams = constructor.getParameters();
        assertThat(constructorParams.size(), is(1));
        assertThat(constructorParams.get(0).getName(), is("NotificationId"));

        assertThat(constructor.getAssignments().size(), is(0));
        assertThat(constructor.getQueryParams().size(), is(0));
    }

}
