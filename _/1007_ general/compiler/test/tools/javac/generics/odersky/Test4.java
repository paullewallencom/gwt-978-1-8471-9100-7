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
 * @test @(#)Test4.java	1.5 07/01/18
 * @ bug 
 * @summary Positive regression test from odersky
 * @author odersky
 *
 * @compile -source 1.5 Test4.java
 */

class Test4 {
    interface I {}
    interface J {}
    static class C implements I, J {}
    static class D implements I, J {}

    interface Ord {}

    static C c = new C();
    static D d = new D();

    static <B> List<B> nil() { return new List<B>(); }
    static <A, B extends A> A f(A x, B y) { return x; }
    static <A, B extends A> B g(List<A> x, List<B> y) { return y.head; }

    static <A> List<A> cons(A x, List<A> xs) { return xs.prepend(x); }
    static <A> Cell<A> makeCell(A x) { return new Cell<A>(x); }
    static <A> A id(A x) { return x; }

    static Integer i = new Integer(1);
    static Number n = i;

    public static void main(String[] args) {
	Number x = f(n, i);
	String xy = g(cons("abc", Test4.<String>nil()), Test4.<String>nil());
	System.out.println(g(cons("abc", nil()), nil()));
    }
}
