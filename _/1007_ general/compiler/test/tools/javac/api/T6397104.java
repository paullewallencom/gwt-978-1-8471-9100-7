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

/**
 * @test    @(#)T6397104.java	1.8 07/01/18
 * @bug     6397104
 * @summary JSR 199: JavaFileManager.getFileForOutput should have sibling argument
 * @author  Peter von der Ah\u00e9
 * @ignore  this test should be rewritten when fixing 6473901
 */

import java.io.File;
import java.util.Arrays;
import javax.tools.*;
import javax.tools.JavaFileManager.Location;

import static javax.tools.StandardLocation.CLASS_OUTPUT;

public class T6397104 {

    JavaCompiler tool = ToolProvider.getSystemJavaCompiler();

    void test(StandardJavaFileManager fm,
	      Location location,
	      File siblingFile,
	      String relName,
	      String expectedPath)
	throws Exception
    {
	JavaFileObject sibling = siblingFile == null
	    ? null
	    : fm.getJavaFileObjectsFromFiles(Arrays.asList(siblingFile)).iterator().next();
	FileObject fileObject =
	    fm.getFileForOutput(location, "java.lang", relName, sibling);
	if (!fileObject.toUri().getPath().equals(expectedPath))
	    throw new AssertionError("Expected " + expectedPath +
				     ", got " + fileObject.toUri().getPath());
	System.out.format("OK: (%s, %s) => %s%n", siblingFile, relName, fileObject.toUri());
    }

    void test(boolean hasLocation, File siblingFile, String relName, String expectedPath)
	throws Exception
    {
	StandardJavaFileManager fm;
	if (hasLocation) {
	    for (Location location : StandardLocation.values()) {
		fm = tool.getStandardFileManager(null, null, null);
		fm.setLocation(location, Arrays.asList(new File(".")));
		test(fm, location, siblingFile, relName, expectedPath);
	    }
	} else {
	    fm = tool.getStandardFileManager(null, null, null);
	    test(fm, CLASS_OUTPUT, siblingFile, relName, expectedPath);
	}
    }

    public static void main(String... args) throws Exception {
	T6397104 tester = new T6397104();
	tester.test(false,
		    new File(new File("foo", "bar"), "baz.java"),
		    "qux/baz.xml",
		    "foo/bar/baz.xml");
	tester.test(false,
		    null,
		    "qux/baz.xml",
		    "baz.xml"); // sb "java/lang/qux/baz.xml"
	tester.test(true,
		    new File(new File("foo", "bar"), "baz.java"),
		    "qux/baz.xml",
		    "./java/lang/qux/baz.xml");
	tester.test(true,
		    null,
		    "qux/baz.xml",
		    "./java/lang/qux/baz.xml");
    }

}
