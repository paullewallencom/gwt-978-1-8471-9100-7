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
 * @test @(#)T6431435.java	1.7 07/01/18
 * @bug 6431435 6439406
 * @summary Tree API: source files loaded implicitly from source path
 * @run main T6431435
 */

import java.io.*;
import java.util.*;
import javax.tools.*;
import com.sun.source.util.*;
import com.sun.tools.javac.api.*;

public class T6431435 {
    public static void main(String... args) throws IOException {
	String testSrc = System.getProperty("test.src", ".");
	String testClasses = System.getProperty("test.classes", ".");
	JavacTool tool = JavacTool.create();
        StandardJavaFileManager fm = tool.getStandardFileManager(null, null, null);
	fm.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(new File(".")));
	fm.setLocation(StandardLocation.SOURCE_PATH, Arrays.asList(new File(testSrc)));
	Iterable<? extends JavaFileObject> files = fm.getJavaFileObjectsFromFiles(Arrays.asList(
		new File(testSrc, "A.java")));
	JavacTask task = tool.getTask(null, fm, null, null, null, files);
	boolean ok = true;
	ok &= check("parse", task.parse(), 1);       // A.java
	ok &= check("analyze", task.analyze(), 3);   // A, Foo, p.B
	ok &= check("generate", task.generate(), 5); // A, Foo, Foo$Baz, Foo$1, p.B
	if (!ok)
	    throw new AssertionError("Test failed");
    }

    private static boolean check(String name, Iterable<?> iter, int expect) {
	int found = 0;
	for (Object o: iter) {
	    found++;
	    //System.err.println(name + " " + found + " " + o);
	}
	if (found == expect) 
	    return true;   
	System.err.println(name + ": found " + found + " -- expected " + expect);
	return false;
    }
}
