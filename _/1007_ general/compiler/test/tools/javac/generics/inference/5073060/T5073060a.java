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
 * @test    @(#)T5073060a.java	1.5 07/01/18
 * @bug     5073060
 * @summary Package private members not found for intersection types
 * @author  Peter von der Ah\u00e9
 */

public class T5073060a {
    static class C1 {
	void c1m1() {
	    System.out.println("FISK");
	}
    }
    static interface I {}

    static class C2 extends C1 implements I {}

    static class C3 extends C1 implements I {}

    public <T> T m1(T t1, T t2) { return t1; }

    public <T extends C1 & I> void test(C2 c2, C3 c3, T t) {
	m1(c2, c3).c1m1(); // error
	t.c1m1(); // error
	(t != null ? c2 : c3).c1m1(); // error
    }

    public static void main(String... args) {
	T5073060a t = new T5073060a();
	t.test(new C2(), new C3(), new C2());
    }
}
