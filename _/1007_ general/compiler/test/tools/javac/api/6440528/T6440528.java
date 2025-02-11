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
 * @test    @(#)T6440528.java	1.4 07/01/18
 * @bug     6440528
 * @summary javac deposits package-info.class in bogus directory
 * @author  Peter von der Ah\u00e9
 * @library ../lib
 * @compile T6440528.java
 * @run main T6440528
 */

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import static javax.tools.StandardLocation.CLASS_OUTPUT;
import javax.tools.*;

public class T6440528 extends ToolTester {
    void test(String... args) throws Exception {
        fm.setLocation(CLASS_OUTPUT, null); // no class files are
                                            // generated, so this will
                                            // not leave clutter in
                                            // the source directory
        Iterable<File> files = Arrays.asList(new File(test_src, "package-info.java"));
        JavaFileObject src = fm.getJavaFileObjectsFromFiles(files).iterator().next();
        char sep = File.separatorChar;
        FileObject cls = fm.getFileForOutput(CLASS_OUTPUT,
                                             "com.sun.foo.bar.baz",
                                             "package-info.class",
                                             src);
        File expect = new File(test_src, "package-info.class");
        File got = getUnderlyingFile(cls);
        if (!got.equals(expect))
            throw new AssertionError(String.format("Expected: %s; got: %s", expect, got));
        System.err.println("Expected: " + expect);
        System.err.println("Got:      " + got);
    }

    private File getUnderlyingFile(Object o) throws Exception {
        Field f = o.getClass().getDeclaredField("f");
        f.setAccessible(true);
        return (File)f.get(o);
    }

    public static void main(String... args) throws Exception {
        new T6440528().test(args);
    }
}
