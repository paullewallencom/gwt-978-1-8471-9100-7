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
 * @test @(#)CrossPackageImplA.java	1.4 07/01/18
 * @bug 4362349
 * @summary Cannot override abstract method in same package
 * @author gafter
 *
 * @compile CrossPackageImplA.java CrossPackageImplB.java
 */

package a;

public abstract class CrossPackageImplA {
    public static void main(String[] args) {
	CrossPackageImplA a = new CrossPackageImplC();
	System.out.println(a.message());
    }

    abstract String message();
}

class CrossPackageImplC extends b.CrossPackageImplB {
    String message() { return "Hello, world"; }
}
