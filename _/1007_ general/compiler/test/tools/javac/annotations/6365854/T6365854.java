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
 * @test    @(#)T6365854.java	1.4 07/01/18
 * @bug     6365854
 * @summary javac crashes when compiling against an annotated class
 * @author  c.j.tucker@gmail.com
 * @compile TestAnnotation.java TestCore.java
 * @clean test.annotation.TestAnnotation
 * @compile T6365854.java
 * @compile evolve/TestAnnotation.java T6365854.java
 * @compile T6365854.java
 *
 * @compile TestAnnotation.java TestCore.java
 * @clean test.annotation.TestAnnotation
 * @compile/ref=test1.out -XDstdout -XDrawDiagnostics T6365854.java
 * @run main T6365854
 * @compile/ref=test2.out -XDstdout -XDrawDiagnostics evolve/TestAnnotation.java T6365854.java
 * @run main T6365854
 * @compile/ref=test2.out -XDstdout -XDrawDiagnostics T6365854.java
 * @run main T6365854
 */

import test.core.TestCore;

public class T6365854 {
    public static void main(String... args) {
        TestCore tc = new TestCore();
        System.out.println(tc.toString());
    }
}
