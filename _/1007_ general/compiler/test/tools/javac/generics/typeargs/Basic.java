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
 * @test @(#)Basic.java	1.5 07/01/18
 * @bug 4851039
 * @summary explicit type arguments
 * @author gafter
 *
 * @compile -source 1.5 Basic.java
 */

// Test all of the basic forms for explicit type arguments

class T<X> {

    class U<Y> extends T<X> {
	<B> U() {
	    <Object>super();
	}
	U(int i) {
	    <Object>this();
	}
    }

    class V<Z> extends U<Z> {
	<C> V(T<X> t) {
	    t.<Object>super();
	}
    }

    <A> T() {
    }

    <K> void f() {
	this.<Object>f();
    }

    public static void main(String[] args) {
	T<Integer> x = new <Object>T<Integer>();
	T<Integer>.U<Float> y = x.new <Object>U<Float>();
	x.<Object>f();
    }
}
