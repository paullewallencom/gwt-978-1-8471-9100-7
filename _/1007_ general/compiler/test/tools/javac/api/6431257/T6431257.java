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
 * @test    @(#)T6431257.java	1.4 07/01/18
 * @bug     6431257
 * @summary JSR 199: Changes to JavaFileManager to support JSR 269 Filer API
 * @author  Peter von der Ah\u00e9
 * @library ../lib
 * @compile T6431257.java package-info.java
 * @run main T6431257 foo.bar.baz foo/bar/baz
 */

import java.io.IOException;
import java.util.EnumSet;
import javax.tools.*;
import static javax.tools.JavaFileObject.Kind.CLASS;
import static javax.tools.StandardLocation.CLASS_OUTPUT;
import static javax.tools.StandardLocation.PLATFORM_CLASS_PATH;

public class T6431257 extends ToolTester {
    void test(String... args) throws IOException {
        for (String arg : args)
            testPackage(arg);
    }

    void testPackage(String packageName) throws IOException {
        JavaFileObject object
            = fm.getJavaFileForInput(PLATFORM_CLASS_PATH, "java.lang.Object", CLASS);
        Iterable<? extends JavaFileObject> files
            = fm.list(CLASS_OUTPUT, packageName, EnumSet.of(CLASS), false);
        boolean found = false;
        String binaryPackageName = packageName.replace('/', '.');
        for (JavaFileObject file : files) {
            System.out.println("Found " + file.getName() + " in " + packageName);
            String name = fm.inferBinaryName(CLASS_OUTPUT, file);
            found |= name.equals(binaryPackageName + ".package-info");
            JavaFileObject other = fm.getJavaFileForInput(CLASS_OUTPUT, name, CLASS);
            if (!fm.isSameFile(file, other))
                throw new AssertionError(file + " != " + other);
            if (fm.isSameFile(file, object))
                throw new AssertionError(file + " == " + object);
        }
        if (!found)
            throw new AssertionError("Did not find " + binaryPackageName + ".package-info");
    }

    public static void main(String... args) throws IOException {
        new T6431257().test(args);
    }
}
