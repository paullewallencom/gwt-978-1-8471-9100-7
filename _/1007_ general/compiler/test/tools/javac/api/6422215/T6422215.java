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
 * @test    @(#)T6422215.java	1.4 07/01/18
 * @bug     6422215
 * @summary JSR 199: What happens if a directory is missing
 * @author  Peter von der Ah\u00e9
 * @library ../lib
 */

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import static javax.tools.StandardLocation.CLASS_OUTPUT;

public class T6422215 extends ToolTester {
    void test(String... args) {
        try {
            fm.setLocation(CLASS_OUTPUT,
                           Collections.singleton(new File(test_src, "no_such_dir")));
            throw new AssertionError("Expected exception not thrown");
        } catch (IOException e) {
            System.out.println("OK: caught expected exception: " + e.getLocalizedMessage());
        }
    }
    public static void main(String... args) {
        new T6422215().test(args);
    }
}
