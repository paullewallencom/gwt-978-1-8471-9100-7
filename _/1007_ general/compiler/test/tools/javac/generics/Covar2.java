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
 * @test @(#)Covar2.java	1.6 07/01/18
 * @bug 4949303 4960369 4965756
 * @summary Additional unit tests for covariant returns
 * @author gafter
 *
 * @compile -source 1.5 Covar2.java
 * @run main Covar2
 */

public class Covar2 {
    static class A1 {
	public Long f() { return 12L; }
    }
    static class B1 extends A1 {
	public Long f() { return 12L; }
    }

    static class B2 {
	public int f() { return 12; }
    }
    static class C2 extends B2 {
	public int f() { return (short)12; }
    }

    static class C3 extends B2 {
	public int f() { return (short)12; }
    }

    public static void main(String[] args) {
	A1 b1 = new B1();
	System.out.println(b1.f());

	B2 c2 = new C2();
	System.out.println(c2.f());

	B2 c3 = new C3();
	System.out.println(c3.f());
    }
}
