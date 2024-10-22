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
 * @test
 * @bug 6260188 6290772
 * @summary provide variable policies for javac operation
 * 		Default compile policy is now "by file" (reverted to "todo" for 6382700)
 * 		Because of attr errors in B, no code should be generated
 * @compile/fail/ref=bytodo.ABD.out -XDstdout -XDrawDiagnostics -XDverboseCompilePolicy A.java B.java D.java
 */

/*
 * @test
 * @bug 6260188
 * @summary provide variable policies for javac operation
 * 		Generate code for A, A1, A2, B
 * @compile/fail/ref=bytodo.ABD.out -XDstdout -XDrawDiagnostics -XDverboseCompilePolicy -XDcompilePolicy=bytodo A.java B.java D.java
 */

/*
 * @test
 * @bug 6260188
 * @summary provide variable policies for javac operation
 * 		Because of attr errors in B, no code should be generated
 * @compile/fail/ref=simple.ABD.out -XDstdout -XDrawDiagnostics -XDverboseCompilePolicy -XDcompilePolicy=simple A.java B.java D.java
 */

/*
 * @test
 * @bug 6260188
 * @summary provide variable policies for javac operation
 * 		Because of attr errors in B, no code should be generated
 * @compile/fail/ref=byfile.ABD.out -XDstdout -XDrawDiagnostics -XDverboseCompilePolicy -XDcompilePolicy=byfile A.java B.java D.java
 */




/*
 * @test
 * @bug 6260188 6290772
 * @summary provide variable policies for javac operation
 * 		Default compile policy is now "by file" (reverted to "todo" for 6382700)
 * 		Generate code for A, A1, A2, but because of flow errors in C, no more code should be generated
 * @compile/fail/ref=bytodo.ACD.out -XDstdout -XDrawDiagnostics -XDverboseCompilePolicy A.java C.java D.java
 */

/*
 * @test
 * @bug 6260188
 * @summary provide variable policies for javac operation
 * 		Generate code for A, A1, A2, C
 * @compile/fail/ref=bytodo.ACD.out -XDstdout -XDrawDiagnostics -XDverboseCompilePolicy -XDcompilePolicy=bytodo A.java C.java D.java
 */

/*
 * @test
 * @bug 6260188
 * @summary provide variable policies for javac operation
 * 		Because of flow errors in C, no code should be generated
 * @compile/fail/ref=simple.ACD.out -XDstdout -XDrawDiagnostics -XDverboseCompilePolicy -XDcompilePolicy=simple A.java C.java D.java
 */

/*
 * @test
 * @bug 6260188
 * @summary provide variable policies for javac operation
 * 		Generate code for A, A1, A2, but because of flow errors in C, no more code should be generated
 * @compile/fail/ref=byfile.ACD.out -XDstdout -XDrawDiagnostics -XDverboseCompilePolicy -XDcompilePolicy=byfile A.java C.java D.java
 */


