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
 * @test @(#)T6440583.java	1.4 07/01/18
 * @bug 6440583
 * @summary better error recovery
 */

import java.io.*;
import java.util.*;
import javax.tools.*;
import com.sun.source.tree.*;
import com.sun.source.util.*;
import com.sun.tools.javac.api.*;
import com.sun.tools.javac.tree.JCTree.*;

public class T6440583 {
    public static void main(String... args) throws Exception {
	String testSrc = System.getProperty("test.src", ".");
	String testClasses = System.getProperty("test.classes", ".");
	JavacTool tool = JavacTool.create();
	StandardJavaFileManager fm = tool.getStandardFileManager(null, null, null);
	Iterable<? extends JavaFileObject> files = 
	    fm.getJavaFileObjectsFromFiles(Arrays.asList(new File(testSrc, "A.java")));
	JavacTask task = tool.getTask(null, fm, null, null, null, files);

	Iterable<? extends Tree> trees = task.parse();

	TreeScanner<Boolean,Void> checker = new TreeScanner<Boolean,Void>() {
	    public Boolean visitErroneous(ErroneousTree tree, Void ignore) {
		JCErroneous etree = (JCErroneous) tree;
		List<? extends Tree> errs = etree.getErrorTrees();
		System.err.println("errs: " + errs);
		if (errs == null || errs.size() == 0)
		    throw new AssertionError("no error trees found");
		found = true;
		return true;
	    }
	};

	for (Tree tree: trees)
	    checker.scan(tree, null);

	if (!found)
	    throw new AssertionError("no ErroneousTree nodes found");
    }

    private static boolean found; 
}
