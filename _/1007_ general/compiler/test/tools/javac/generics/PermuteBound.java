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
 * @test @(#)PermuteBound.java	1.4 07/01/18
 * @bug 4698375
 * @summary generics: subtyping problem when type parameters permuted
 * @author gafter
 *
 * @compile -source 1.5 PermuteBound.java
 */

package test.tools.javac.generics.PermuteBound;

class C<X, Y> {}

class D<X, Y> extends C<X, Y>
{
    void f(C<X, Y> c)
    {
        D<X, Y> d = (D<X, Y>) c;// OK because D extends C
    }

    void g(C<Y, X> c)// parameters X and Y are now permuted
    {
        D<Y, X> d = (D<Y, X>) c;// should also be OK but is not !?
        //                    ^
        // inconvertible types
        // found :    C<Y,X>
        // required : D<Y,X>
    }
    /*
    void gWorkAround(C<Y, X> c)// but generates unchecked warning
    {
        D<Y, X> d = (D) ((C) c);
    }
    */
}
