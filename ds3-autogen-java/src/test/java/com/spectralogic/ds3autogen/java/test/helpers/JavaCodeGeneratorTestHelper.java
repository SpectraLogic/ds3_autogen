/*
 * ******************************************************************************
 *   Copyright 2014-2017 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.java.test.helpers;

import static com.spectralogic.ds3autogen.java.utils.TestHelper.*;
import static org.junit.Assert.assertTrue;

/**
 * This class provides utilities for the functional testing
 * of the Java Code Generator
 */
public final class JavaCodeGeneratorTestHelper {

    /**
     * Checks that the generated Java Ds3Client is of correct syntax and contains the specified request
     */
    public static void testDs3Client(final String requestName, final String ds3ClientGeneratedCode) {
        assertTrue(hasCopyright(ds3ClientGeneratedCode));
        assertTrue(hasCommand(requestName, ds3ClientGeneratedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client", ds3ClientGeneratedCode));
        assertTrue(extendsInterface("Ds3Client", "Closeable", ds3ClientGeneratedCode));

        testClientImports(ds3ClientGeneratedCode);
        assertTrue(hasImport("java.io.Closeable", ds3ClientGeneratedCode));
    }

    /**
     * Checks that the generated Java Ds3ClientImpl is of correct syntax and contains the specified request
     */
    public static void testDs3ClientImpl(final String requestName, final String ds3ClientImplGeneratedCode) {
        assertTrue(hasCopyright(ds3ClientImplGeneratedCode));
        assertTrue(hasCommand(requestName, Scope.PUBLIC, ds3ClientImplGeneratedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client", ds3ClientImplGeneratedCode));
        assertTrue(implementsInterface("Ds3ClientImpl", "Ds3Client", ds3ClientImplGeneratedCode));

        testClientImports(ds3ClientImplGeneratedCode);
        assertTrue(hasImport("com.spectralogic.ds3client.networking.NetworkClient", ds3ClientImplGeneratedCode));
    }

    /**
     * Checks that the provided code has all required imports for the Java Ds3Client and Ds3ClientImpl
     */
    public static void testClientImports(final String clientCode) {
        assertTrue(hasImport("com.spectralogic.ds3client.commands.*", clientCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.spectrads3.*", clientCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.spectrads3.notifications.*", clientCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.JobNode", clientCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.ConnectionDetails", clientCode));

        assertTrue(hasImport("java.io.IOException", clientCode));
    }

    /**
     * Checks that the Java request handler contains checksum components (imports, params, and getters)
     */
    public static void testHasChecksumCode(
            final String requestGeneratedCode,
            final String requestName) {
        assertTrue(hasImport("com.spectralogic.ds3client.models.ChecksumType", requestGeneratedCode));
        assertTrue(isOptParamOfType("checksum", "ChecksumType", requestName, requestGeneratedCode, true));
        assertTrue(hasGetter("checksum", "ChecksumType", requestGeneratedCode));
        assertTrue(hasGetter("checksumType", "ChecksumType.Type", requestGeneratedCode));
    }
}
