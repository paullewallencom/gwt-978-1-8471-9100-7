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
 * @test    @(#)T6422327.java	1.4 07/01/18
 * @bug     6422327
 * @summary JSR 199: JavaCompilerTool can compile and generate '.class' of non '.java' files
 * @author  Peter von der Ah\u00e9
 * @library ../lib
 */

import java.io.File;

public class T6422327 extends ToolTester {
    void test(String... args) {
        File file = new File(test_src, "T6422327.other");
        try {
            task = tool.getTask(null, fm, null, null, null, fm.getJavaFileObjects(file));
            throw new AssertionError("Expected exception not thrown");
        } catch (IllegalArgumentException e) {
            System.err.println("OK, got expected exception: " + e.getLocalizedMessage());
        }
    }
    public static void main(String... args) {
        new T6422327().test(args);
    }
}
