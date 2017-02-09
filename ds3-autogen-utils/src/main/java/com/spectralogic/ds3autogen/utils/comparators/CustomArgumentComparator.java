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

package com.spectralogic.ds3autogen.utils.comparators;

import com.spectralogic.ds3autogen.api.models.Arguments;

import java.util.Comparator;

public class CustomArgumentComparator implements Comparator<Arguments> {
    /**
     * Compares arguments by name. Comparison follows
     *    "BucketName" and "Bucket" are lowest value
     *    "ObjectName" and "Object" are second lowest value
     *    All other Arguments are sorted alphabetically
     */
    @Override
    public int compare(final Arguments leftArg, final Arguments rightArg) {
        if (leftArg.getName().equalsIgnoreCase("Bucket") || leftArg.getName().equalsIgnoreCase("BucketName")) {
            return -1;
        } else if (rightArg.getName().equalsIgnoreCase("Bucket") || rightArg.getName().equalsIgnoreCase("BucketName")) {
            return 1;
        } else if (leftArg.getName().equalsIgnoreCase("ObjectName") || leftArg.getName().equalsIgnoreCase("Object")) {
            return -1;
        } else if (rightArg.getName().equalsIgnoreCase("ObjectName") || rightArg.getName().equalsIgnoreCase("Object")) {
            return 1;
        } else {
            return leftArg.getName().compareToIgnoreCase(rightArg.getName());
        }
    }
}
