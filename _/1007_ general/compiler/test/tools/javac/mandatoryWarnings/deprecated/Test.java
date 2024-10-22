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

// This file is not executed directly; it just exists to contain the
// set of test descriptions

/*
 * @test
 * @bug 5047307
 * @summary javac -nowarn improperly suppresses JLS-mandated warnings
 * @compile/ref=Test1.out -XDstdout -XDrawDiagnostics A.java
 */

/*
 * @test
 * @bug 5047307
 * @summary javac -nowarn improperly suppresses JLS-mandated warnings
 * @compile/ref=Test1.out -XDstdout -XDrawDiagnostics -nowarn A.java
 */

/*
 * @test
 * @bug 5047307
 * @summary javac -nowarn improperly suppresses JLS-mandated warnings
 * @compile/ref=Test1.out -XDstdout -XDrawDiagnostics -Xmaxwarns 1 A.java
 */

/*
 * @test
 * @bug 5047307
 * @summary javac -nowarn improperly suppresses JLS-mandated warnings
 * @compile/ref=Test2.out -XDstdout -XDrawDiagnostics A.java B.java
 */

/*
 * @test
 * @bug 5047307
 * @summary javac -nowarn improperly suppresses JLS-mandated warnings
 * @compile/ref=Test2.out -XDstdout -XDrawDiagnostics -nowarn A.java B.java
 */

/*
 * @test
 * @bug 5047307
 * @summary javac -nowarn improperly suppresses JLS-mandated warnings
 * @compile/ref=Test2.out -XDstdout -XDrawDiagnostics -Xmaxwarns 1 A.java B.java
 */

/*
 * @test
 * @bug 5047307
 * @summary javac -nowarn improperly suppresses JLS-mandated warnings
 * @compile/ref=Test3.out -XDstdout -XDrawDiagnostics -Xlint:deprecation A.java
 */

/*
 * @test
 * @bug 5047307
 * @summary javac -nowarn improperly suppresses JLS-mandated warnings
 * @compile/ref=Test3.out -XDstdout -XDrawDiagnostics -nowarn -Xlint:deprecation A.java
 */

/*
 * @test
 * @bug 5047307
 * @summary javac -nowarn improperly suppresses JLS-mandated warnings
 * @compile/ref=Test3b.out -XDstdout -XDrawDiagnostics -nowarn -Xlint:deprecation -Xmaxwarns 1 A.java
 */

/*
 * @test
 * @bug 5047307
 * @summary javac -nowarn improperly suppresses JLS-mandated warnings
 * @compile/ref=Test4.out -XDstdout -XDrawDiagnostics -Xlint:deprecation A.java B.java
 */

/*
 * @test
 * @bug 5047307
 * @summary javac -nowarn improperly suppresses JLS-mandated warnings
 * @compile/ref=Test4.out -XDstdout -XDrawDiagnostics -nowarn -Xlint:deprecation A.java B.java
 */

/*
 * @test
 * @bug 5047307
 * @summary javac -nowarn improperly suppresses JLS-mandated warnings
 * @compile/ref=Test4b.out -XDstdout -XDrawDiagnostics -nowarn -Xlint:deprecation -Xmaxwarns 1 A.java B.java
 */

/*
 * @test
 * @bug 5047307
 * @summary javac -nowarn improperly suppresses JLS-mandated warnings
 * @compile/ref=Test4c.out -XDstdout -XDrawDiagnostics -nowarn -Xlint:deprecation -Xmaxwarns 2 A.java B.java
 */

/*
 * @test
 * @bug 5047307
 * @summary javac -nowarn improperly suppresses JLS-mandated warnings
 * @compile/ref=Test4d.out -XDstdout -XDrawDiagnostics -nowarn -Xlint:deprecation -Xmaxwarns 3 A.java B.java
 */

/*
 * @test
 * @bug 5047307
 * @summary javac -nowarn improperly suppresses JLS-mandated warnings
 * @compile/ref=Test5.out -XDstdout -XDrawDiagnostics -Xlint:deprecation  P.java Q.java
 */

/*
 * @test
 * @bug 5047307
 * @summary javac -nowarn improperly suppresses JLS-mandated warnings
 * @compile/ref=Test5b.out -XDstdout -XDrawDiagnostics -Xlint:deprecation -Xmaxwarns 2 P.java Q.java
 */
