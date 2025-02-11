/*
 * Copyright (c) 2007 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *   
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *   
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *  
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *   
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 *  
 */

/*
 * @test    @(#)T6411333.java	1.2 07/01/18
 * @bug     6411333 6400208 6400225 6400267
 * @summary Ensure 6400208, 6400225, and 6400267 are tested
 * @author  Peter von der Ah\u00e9
 * @library ../lib
 * @compile T6411333.java
 * @run main T6411333
 */

import java.io.IOException;
import javax.tools.*;
import static javax.tools.StandardLocation.*;
import static javax.tools.JavaFileObject.Kind.*;

// Note: 6400225 (getEffectiveLocation) was dropped eventually.

public class T6411333 extends ToolTester {

    // 6400208: JSR 199: failure mode for inferBinaryName
    void testInferBinaryName(String binaryName, boolean fail) {
        try {
            JavaFileObject file = fm.getJavaFileForInput(PLATFORM_CLASS_PATH,
                                                         binaryName,
                                                         CLASS);
            String inferred = fm.inferBinaryName(fail ? CLASS_PATH : PLATFORM_CLASS_PATH,
                                                 file);
            if (inferred == null && fail)
                return;
            if (!inferred.equals(binaryName))
                throw new AssertionError(String.format("binaryName (%s) != inferred (%s)",
                                                       binaryName,
                                                       inferred));
        } catch (IOException ex) {
            throw new AssertionError(ex);
        }
    }

    // 6400267: JSR 199: specify the exact requirements for relative URIs
    void testRelativeUri(String name, boolean fail) {
        try {
            fm.getFileForInput(SOURCE_OUTPUT, "java.lang", name);
        } catch (IllegalArgumentException ex) {
            if (fail)
                return;
        } catch (IOException ex) {
            throw new AssertionError(ex);
        }
        if (fail)
            throw new AssertionError("Expected failure on " + name);
    }

    void test(String... args) {
        testInferBinaryName("java.lang.Object", false);
        testInferBinaryName("java.lang.Object", true);

        testRelativeUri("../util/List.java", true);
        testRelativeUri("util/List.java", false);
        testRelativeUri("/util/List.java", true);
    }
    public static void main(String... args) {
        new T6411333().test(args);
    }
}
