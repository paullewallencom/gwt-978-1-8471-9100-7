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
 * @test @(#)T6395974.java	1.8 07/01/18
 * @bug 6395974
 * @summary files are parsed even after failure to find annotation processor is reported
 */

import java.io.*;
import java.util.*;
import javax.tools.*;
import com.sun.source.util.*;
import com.sun.tools.javac.api.*;


public class T6395974 {
    public static void main(String... args) throws Throwable {
	String self = T6395974.class.getName();

	String testSrc = System.getProperty("test.src");
	
	JavacTool tool = JavacTool.create();
	StandardJavaFileManager fm = tool.getStandardFileManager(null, null, null);
	Iterable<?extends JavaFileObject> f =
	    fm.getJavaFileObjectsFromFiles(Arrays.asList(new File(testSrc, self + ".java")));

	PrintWriter out = new PrintWriter(System.err, true);

	JavacTaskImpl task = (JavacTaskImpl) tool.getTask(out,
							  fm,
							  null,
							  Arrays.asList("-processor",
									"Foo.java"),
							  null,
							  f);
							  
	MyTaskListener tl = new MyTaskListener();
	task.setTaskListener(tl);

	task.call();

	if (tl.event != null)
	    throw new AssertionError("Unexpected TaskListener event: " + tl.event);
    }

    static class MyTaskListener implements TaskListener {
	public void started(TaskEvent e) {
	    System.err.println("Started: " + e);
	    if (event == null)
		event = e;
	}
	public void finished(TaskEvent e) {
	}
	
	TaskEvent event;
    }
}
