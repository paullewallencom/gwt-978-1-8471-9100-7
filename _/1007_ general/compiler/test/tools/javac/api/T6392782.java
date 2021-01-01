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
 * @test @(#)T6392782.java	1.7 07/01/18
 * @bug 6392782
 * @summary TreeScanner.visitImport returns null, not result of nested scan
 */

import java.io.*;
import java.util.*;
import javax.tools.*;
import com.sun.source.tree.*;
import com.sun.source.util.*;
import com.sun.tools.javac.api.*;

public class T6392782 {
    public static void main(String... args) throws IOException {
	String testSrc = System.getProperty("test.src", ".");
	JavacTool tool = JavacTool.create();
        StandardJavaFileManager fm = tool.getStandardFileManager(null, null, null);
	Iterable<? extends JavaFileObject> files = 
	    fm.getJavaFileObjectsFromFiles(Arrays.asList(new File(testSrc, T6392782.class.getName()+".java")));
	JavacTask task = tool.getTask(null, fm, null, null, null, files);
	Iterable<? extends Tree> trees = task.parse();
	TreeScanner<Integer,Void> scanner = new MyScanner();
	check(scanner, 6, scanner.scan(trees, null));

	CountNodes nodeCounter = new CountNodes();
        // 383 nodes with the regular parser; 384 nodes with EndPosParser
        // We automatically swith to EndPosParser when calling JavacTask.parse()
        check(nodeCounter, 384, nodeCounter.scan(trees, null));

	CountIdentifiers idCounter = new CountIdentifiers();
	check(idCounter, 106, idCounter.scan(trees, null));
    }

    private static void check(TreeScanner<?,?> scanner, int expect, int found) {
	if (found != expect)
	    throw new AssertionError(scanner.getClass().getName() + ": expected: " + expect + " found: " + found);
    }
    
    static class MyScanner extends TreeScanner<Integer,Void> {
	@Override
	public Integer visitImport(ImportTree tree, Void ignore) {
	    //System.err.println(tree);
	    return 1;
	}
	
	@Override
	public Integer reduce(Integer i1, Integer i2) {
	    return (i1 == null ? 0 : i1) + (i2 == null ? 0 : i2);
	}
    }
    
    // example from TreeScanner javadoc
    static class CountNodes extends TreeScanner<Integer,Void> {
	@Override
	public Integer scan(Tree node, Void p) { 
	    Integer n = super.scan(node, p);
	    return (n == null ? 0 : n) + 1; 
        }
	@Override
	public Integer reduce(Integer r1, Integer r2) { 
	    return (r1 == null ? 0 : r1) + (r2 == null ? 0 : r2); 
	}
    }

    // example from TreeScanner javadoc
    static class CountIdentifiers extends TreeScanner<Integer,Void> {
	@Override
	public Integer visitIdentifier(IdentifierTree node, Void p) { 
	    return 1; 
	}
	@Override
	public Integer reduce(Integer r1, Integer r2) { 
	    return (r1 == null ? 0 : r1) + (r2 == null ? 0 : r2); 
	}
    }
}
