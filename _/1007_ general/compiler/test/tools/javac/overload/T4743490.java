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
 * @test @(#)T4743490.java	1.4 07/01/18
 * @bug 4743490
 * @summary overloading versus super.f(args) versus interfaces
 * @author gafter
 *
 * @compile/fail T4743490.java
 */

class T4743490 {
    static class A {
	public void m(Object o, String s) {}
    }
    interface B {
	void m(String s, Object o);
    }
    static abstract class C extends A implements B {
    }
    static abstract class D extends C {
	void foo() {
	    C c = null;
	    super.m("", ""); // should be ambiguous.
	}
    }
}
