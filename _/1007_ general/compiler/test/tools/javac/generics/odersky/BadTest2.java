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
 * @test @(#)BadTest2.java	1.6 07/01/18
 * @ bug 
 * @summary Regression test from odersky
 * @author odersky
 *
 * @compile -source 1.5 BadTest2.java
 */

// this used to be a negative regression test, but when we
// added variance it started passing, undoubtedly due to
// bug fixes.  The code looks OK.
class BadTest2 {

    interface I {}
    interface J {}
    static class C implements I, J {}
    static class D implements I, J {}

    static class Main {

	static C c = new C();
	static D d = new D();

	static <A> boolean equals(A x, A y) { return x.equals(y); }

	public static void main(String[] args) {
	    equals(c, d); // infer A=I&J
	}
    }

}
