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
 * @test @(#)BadTest3.java	1.4 07/01/18
 * @ bug 
 * @summary Negative regression test from odersky
 * @author odersky
 *
 * @compile/fail -source 1.5 BadTest3.java
 */

class BadTest3 {

    interface I {}
    interface J {}
    static class C implements I, J {}
    static class D implements I, J {}

    interface Ord {}

    static class Main {

	static C c = new C();
	static D d = new D();

	static <B extends Ord> List<B> nil() { return new List<B>(); }
	static <B extends I & J> B f(B x) { return x; }

	static <A> List<A> cons(A x, List<A> xs) { return xs.prepend(x); }
	static <A> Cell<A> makeCell(A x) { return new Cell<A>(x); }
	static <A> A id(A x) { return x; }

	public static void main(String[] args) {
	    List<String> xs = nil();
	    f(null);
	    f(nil());
	    I i = f(null);
	    J j = f(nil());
	}
    }
}
