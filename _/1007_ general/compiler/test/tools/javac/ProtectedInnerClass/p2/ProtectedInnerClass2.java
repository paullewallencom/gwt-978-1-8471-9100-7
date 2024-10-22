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
 * @(#)ProtectedInnerClass2.java	1.10 07/01/18
 * Auxilliary file for test for 4087314.
 * Verify allowed access to protected class from another package.
 *
 * This file should compile and run successfully.
 * Note that class p1.ProtectedInnerClass1 must be compiled first.
 */

package p2;

public class ProtectedInnerClass2 extends p1.ProtectedInnerClass1 
{  
    class Bug extends Foo {
	String getBug() { return getBar(); }
    }

    public static void main(String[] args) {
	ProtectedInnerClass2 x = new ProtectedInnerClass2();
	Bug y = x.new Bug();
	System.out.println(y.getBug());
    }
}
