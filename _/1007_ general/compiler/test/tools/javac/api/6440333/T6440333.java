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
 * @test    @(#)T6440333.java	1.2 07/01/18
 * @bug     6440333
 * @summary SimpleJavaFileObject.toString() generates URI with some extra message
 * @author  Peter von der Ah\u00e9
 * @library ../lib
 * @compile T6440333.java
 * @run main T6440333
 */

import java.io.File;
import java.io.IOException;
import javax.tools.JavaFileObject;

public class T6440333 extends ToolTester {
    void test(String... args) throws IOException {
        File path = test_src.getCanonicalFile();
        File src = new File(new File(path, "."), "T6440333.java");
        JavaFileObject fo = fm.getJavaFileObjects(src).iterator().next();
        String expect = src.getCanonicalFile().getPath().replace(File.separatorChar, '/');
        System.err.println("Expect " + expect);
        System.err.println("Got: " +  fo.toUri().getPath());
        if (!expect.equals(fo.toUri().getPath())) {
            throw new AssertionError();
        }
    }
    public static void main(String... args) throws IOException {
        new T6440333().test(args);
    }
}
