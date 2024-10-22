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
 * @test    @(#)Xprint.java	1.7 07/01/18
 * @bug     6266828
 * @summary JSR 269: Java Language Model API
 * @author  Peter von der Ah\u00e9
 */
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class Xprint {
    public static void main(String[] args) {
	JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
	javac.run(System.in, null, null,
		  "-Xprint",
		  "com.sun.tools.javac.code.Types",
		  "com.sun.tools.javac.parser.Parser",
		  "java.util.EnumSet");
    }
}
