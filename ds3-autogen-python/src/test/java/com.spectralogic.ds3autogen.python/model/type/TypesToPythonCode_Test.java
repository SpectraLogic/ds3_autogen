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

package com.spectralogic.ds3autogen.python.model.type;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TypesToPythonCode_Test {

    @Test
    public void typeAttribute_Test() {
        assertThat(new TypeAttribute("Attr").toPythonCode(), is("'Attr'"));
    }

    @Test
    public void typeElement_Test() {
        assertThat(new TypeElement("XmlTag", "None").toPythonCode(), is("'XmlTag' : None"));
        assertThat(new TypeElement("XmlTag", "TypeModel").toPythonCode(), is("'XmlTag' : TypeModel()"));
    }

    @Test
    public void typeElementList_Test() {
        assertThat(new TypeElementList("XmlTag", "None", "None").toPythonCode(),
                is("('XmlTag', None, None)"));

        assertThat(new TypeElementList("XmlTag", "ParentTag", "None").toPythonCode(),
                is("('XmlTag', 'ParentTag', None)"));

        assertThat(new TypeElementList("XmlTag", "ParentTag", "TypeModel").toPythonCode(),
                is("('XmlTag', 'ParentTag', TypeModel())"));

        assertThat(new TypeElementList("XmlTag", "None", "TypeModel").toPythonCode(),
                is("('XmlTag', None, TypeModel())"));
    }
}
