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
 * @test    @(#)TestEvalExpression.java	1.7 07/01/18
 * @bug     4164450
 * @summary JSR 199: Standard interface for Java compilers
 * @author  Peter von der Ah\u00e9
 * @compile TestEvalExpression.java evalexpr/ByteArrayClassLoader.java  evalexpr/CompileFromString.java  evalexpr/MemoryFileManager.java
 * @run main TestEvalExpression
 */

import java.lang.reflect.Method;
import java.util.*;
import javax.swing.JOptionPane;
import javax.tools.*;
import static evalexpr.CompileFromString.*;

public class TestEvalExpression {
    static int errorCount = 0;
    static class Listener implements DiagnosticListener<JavaFileObject> {
	public void report(Diagnostic<? extends JavaFileObject> message) {
	    if (message.getKind() == Diagnostic.Kind.ERROR)
		errorCount++;
	    System.err.println(message);
	}
    }

    public static void main(String[] args) {
        // Get a compiler tool
        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	final List<String> compilerFlags = new ArrayList();
        compilerFlags.add("-Xlint:all"); // report all warnings
        compilerFlags.add("-g:none"); // don't generate debug info
	final DiagnosticListener<JavaFileObject> listener = new Listener();
        String expression = "System.getProperty(\"java.vendor\")";
	Object result = null;
	try {
	    result = evalExpression(compiler, listener, compilerFlags, expression);
	} catch (Exception e) {
	    throw new AssertionError(e);
	}
	if (result == ERROR)
	    throw new AssertionError(result);
	System.out.format("%s => %s%n", expression, result);
	if (!System.getProperty("java.vendor").equals(result))
	    throw new AssertionError(result);
	if (errorCount != 0)
	    throw new AssertionError(errorCount);
	try {
	    result = evalExpression(compiler, listener, compilerFlags, "fisk hest");
	} catch (Exception e) {
	    throw new AssertionError(e);
	}
	if (errorCount == 0)
	    throw new AssertionError(errorCount);
    }
}
