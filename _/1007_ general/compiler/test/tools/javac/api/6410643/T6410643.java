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
 * @test    @(#)T6410643.java	1.5 07/01/18
 * @bug     6410643
 * @summary JSR 199: The method JavaCompilerTool.run fails to handle null arguments
 * @author  Peter von der Ah\u00e9
 * @library ../lib
 */

import javax.tools.JavaFileObject;
import static java.util.Collections.singleton;

public class T6410643 extends ToolTester {

    void testGetTask(Iterable<String> options,
                     Iterable<String> classes,
                     Iterable<? extends JavaFileObject> compilationUnits) {
        try {
            task = tool.getTask(null, null, null, null, null, singleton((JavaFileObject)null));
            throw new AssertionError("Error expected");
        } catch (NullPointerException e) {
            System.err.println("Expected error occurred: " + e);
        }
    }

    void test(String... args) {
        task = tool.getTask(null, null, null, null, null, null);
        if (task.call())
            throw new AssertionError("Error expected");
        System.err.println("Compilation failed as expected!");
        Iterable<String>         s = singleton(null);
        Iterable<JavaFileObject> f = singleton(null);
        //    case (null, null, null) is tested above
        testGetTask(null, null, f);
        testGetTask(null, s,    null);
        testGetTask(null, s,    f);
        testGetTask(s,    null, null);
        testGetTask(s,    null, f);
        testGetTask(s,    s,     null);
        testGetTask(s,    s,    f);
        System.err.println("Test result: PASSED");
    }
    public static void main(String... args) {
        new T6410643().test(args);
    }
}
