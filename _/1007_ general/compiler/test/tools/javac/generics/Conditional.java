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
 * @test @(#)Conditional.java	1.4 07/01/18
 * @bug 4881179 4883239
 * @summary Rule for semantics of ?: in the presence of generics and generic class Class
 * @author gafter
 *
 * @compile -source 1.5 Conditional.java
 */

package conditional;

import java.io.Serializable;

interface I {}
interface J {}
class A implements I, J {}
class B implements I, J {}
class C extends B {}

class Conditional {
    static boolean cond = String.class.getName().length() == 1;
    public static void main(String[] args) {
	Class c = cond ? A.class : B.class;
	Class<?> d = cond ? A.class : B.class;

	Class<? extends B> e = cond ? B.class : C.class;
    }

    void f(A a, B b) {
	I i = cond ? a : b;
	J j = cond ? a : b;
    }

    // required for compatibility
    Class g(Class a) {
	return cond ? a : B.class;
    }

    // required for compatibility
    byte[] h(byte[] a, byte[] b) {
	return cond ? a : b;
    }

    // This one is hard because of the recursive F-bounds
    // The naive result is the infinite type
    // Class<? extends Number&Comparable<? extends Number&Comparable<? extends
    // ...
    Class<? extends Comparable<?>> c =
	cond ? Integer.class : Float.class;

    Comparable<?> o =
	cond ? true : 3;

    /*

    // See 4942040
    void f(Cloneable a, int[] b) {
	Cloneable x = cond ? a : b;
    }
    void f(Serializable a, int[] b) {
	Serializable x = cond ? a : b;
    }

    // See 4941882
    void f(float[] a, int[] b) {
	Serializable x = cond ? a : b;
    }
    */
}
