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

/**
 * @test @(#)WhichImplicitThis7.java	1.5 07/01/18
 * @bug 4714403
 * @summary private members in a superclass should not hide members from the enclosing scope
 *
 * @compile WhichImplicitThis7.java
 */

/*
  The following is required to compile without error.  javac rejects it, 
  because javac thinks the i is referring to the current class which has
  not been initialized yet.  But C has no member i - private members are
  not inherited.  i therefore refers to the one from the enclosing scope.
*/

class WhichImplicitThis7 {
    static private int i;
    static class B extends WhichImplicitThis7 {
	private int i;
    }
    class C extends B {
        C(int j) {}
        C() {
            // although c is a subclass of WhichImplicitThis7, it does
            // not inherit i because i is private.  So i in the
            // following refers to the one from the enclosing class,
            // which is allowed here because it is static
            this(i);
        }
    }
}

