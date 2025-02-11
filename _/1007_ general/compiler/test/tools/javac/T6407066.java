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
 * @test @(#)T6407066.java	1.7 07/01/18
 * @bug 6407066
 * @summary Paths code should not discard non-existent directories
 */

import java.io.File;
import java.util.*;
import javax.tools.*;

public class T6407066 {
    public static void main(String... args) throws Exception {
	String testSrc = System.getProperty("test.src", ".");
	String testClasses = System.getProperty("test.classes", ".");

        JavaCompiler tool = ToolProvider.getSystemJavaCompiler();
	StandardJavaFileManager jfm = tool.getStandardFileManager(null, null, null);

	List<File> path = new ArrayList<File>();
	path.add(new File("BadDirectory"));
	path.add(new File(testSrc));
	path.add(new File("BadFile.jar"));

	jfm.setLocation(StandardLocation.SOURCE_PATH, path);

	List<File> path2  = new ArrayList<File>();
	for (File f: jfm.getLocation(StandardLocation.SOURCE_PATH))
	    path2.add(f);

	if (!path.equals(path2))
	    throw new AssertionError("path not preserved");
    }
}
