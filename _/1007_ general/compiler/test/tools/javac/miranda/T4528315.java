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
 * @test @(#)T4528315.java	1.4 07/01/18
 * @bug 4528315
 * @summary Multiple method inheritance and throws clauses
 * @author gafter
 *
 * @compile T4528315.java
 */

class E1 extends Exception {}
class E2 extends Exception {}
class E3 extends Exception {}
class E4 extends E1 {}
interface I1 {
    void foo() throws E1, E2;
}
interface I2 {
    void foo() throws E3, E4;
}
interface I3 extends I1, I2 {
    // void foo() throws E4;
}
class Blah {
    public void foo(I3 i3) {
	try {
	    i3.foo();
	} catch (E4 e4) {
	}
    }
}
