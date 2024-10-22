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
 * @test    @(#)T6437349.java	1.3 07/01/18
 * @bug     6437349
 * @summary JSR 199: JavaFileObject.isNameCompatible() will give true with some incompatible kinds
 * @library ../lib
 * @compile T6437349.java
 * @run main T6437349
 */

import java.io.IOException;
import javax.tools.*;
import static javax.tools.StandardLocation.*;
import static javax.tools.JavaFileObject.Kind.*;

public class T6437349 extends ToolTester {
    void test(String... args) throws IOException {
        task = tool.getTask(null, fm, null, null, null, null);
        JavaFileObject fo = fm.getJavaFileForInput(SOURCE_PATH, "T6437349", SOURCE);
        if (fo.isNameCompatible("T6437349.java", OTHER))
            throw new AssertionError();
        if (!fo.isNameCompatible("T6437349", SOURCE))
            throw new AssertionError();
        fo = fm.getJavaFileForInput(PLATFORM_CLASS_PATH, "java.lang.Object", CLASS);
        if (fo.isNameCompatible("Object.class", OTHER))
            throw new AssertionError();
        if (!fo.isNameCompatible("Object", CLASS))
            throw new AssertionError();
    }
    public static void main(String... args) throws IOException {
        new T6437349().test(args);
    }
}
