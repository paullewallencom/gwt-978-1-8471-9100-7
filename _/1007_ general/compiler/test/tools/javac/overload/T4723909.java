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
 * @test @(#)T4723909.java	1.6 07/01/18
 * @bug 4723909 4758654 4839284
 * @summary class methods do not conform to JLS 15.12.2.2 definition of most specific method
 * @author gafter
 *
 * @compile T4723909.java
 */

class T4723909 {
    static class Test {
	public static void main(String[] args) {
	    new Subclass().test(0);
	}
    }
    static class Superclass {
	static void test(int i) {
	    System.out.println("test(int i)");
	}
    }
    static class Subclass extends Superclass {
	static void test(long l) {
	    System.out.println("test(long l)");
	}
    }
}
