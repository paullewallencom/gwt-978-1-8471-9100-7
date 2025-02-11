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
 * @test @(#)BadTest.java	1.4 07/01/18
 * @ bug 
 * @summary Negative regression test from odersky
 * @author odersky
 *
 * @compile/fail -source 1.5 BadTest.java
 */

class BadTest {
    static class Main {

	static <B> List<B> nil() { return new List<B>(); }
	static <A> List<A> cons(A x, List<A> xs) { return xs.prepend(x); }
	static <A> Cell<A> makeCell(A x) { return new Cell<A>(x); }
	static <A> A id(A x) { return x; }

	public static void main(String[] args) {
	    List<Cell<String>> as = nil().prepend(makeCell(null));
	    List<Cell<String>> bs = cons(makeCell(null), nil());
	    List<String> xs = id(nil());
	    List<String> ys = cons("abc", id(nil()));
	    List<String> zs = id(nil()).prepend("abc");
	    List<Cell<String>> us = id(nil()).prepend(makeCell(null));
	    List<Cell<String>> vs = cons(makeCell(null), id(nil()));
	    System.out.println(nil() instanceof List<String>);
	    nil();
	}

    }
}
