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
 * @test @(#)T6439826.java	1.8 07/01/18
 * @bug 6439826 6411930 6380018 6392177
 * @summary Exception issuing Diagnostic while processing generated errant code
 */

import java.io.*;
import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.tools.*;
import com.sun.source.util.*;
import com.sun.tools.javac.api.*;
import static javax.lang.model.util.ElementFilter.*;


@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_6 )
public class T6439826 extends AbstractProcessor {
    public static void main(String... args) {
	String testSrc = System.getProperty("test.src", ".");
	String testClasses = System.getProperty("test.classes");
	JavacTool tool = JavacTool.create();
	MyDiagListener dl = new MyDiagListener();
        StandardJavaFileManager fm = tool.getStandardFileManager(dl, null, null);
	Iterable<? extends JavaFileObject> files = 
	    fm.getJavaFileObjectsFromFiles(Arrays.asList(new File(testSrc, T6439826.class.getName()+".java")));
	Iterable<String> opts = Arrays.asList("-proc:only",
					      "-processor", "T6439826",
					      "-processorpath", testClasses);
	StringWriter out = new StringWriter();
	JavacTask task = tool.getTask(out, fm, dl, opts, null, files);
        task.call();
	String s = out.toString();
	System.err.print(s);
	// Expect the following 2 diagnostics, and no output to log
	//   Foo.java:1: illegal character: \35
	//   Foo.java:1: reached end of file while parsing
	System.err.println(dl.count + " diagnostics; " + s.length() + " characters");
	if (dl.count != 2 || s.length() != 0)
	    throw new AssertionError("unexpected output from compiler");
    }

    public boolean process(Set<? extends TypeElement> annotations,
			   RoundEnvironment roundEnv) {
	Set<? extends TypeElement> elems = typesIn(roundEnv.getRootElements());
	for (TypeElement e: elems) {
	    if (e.getSimpleName().toString().equals(T6439826.class.getName())) 
		writeBadFile();
	}
	return false;
    }

    private void writeBadFile() {
	Filer filer = processingEnv.getFiler();
	Messager messager = processingEnv.getMessager();
	try {
	    Writer out = filer.createSourceFile("Foo").openWriter();
	    out.write("class Foo #"); // write a file that generates a scanner error
	    out.close();
	} catch (IOException e) {
	    messager.printMessage(Diagnostic.Kind.ERROR, e.toString());
	}
    }

    static class MyDiagListener implements DiagnosticListener {
	public void report(Diagnostic d) {
	    System.err.println(d);
	    count++;
	}

	public int count;
    }
}
