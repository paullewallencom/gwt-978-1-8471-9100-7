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
 * @test    @(#)T6468384.java	1.3 07/01/18
 * @bug     6468384 6278587
 * @summary Problem with underconstrained type variables
 * @author  Jason Frame <weqlin@hotmail.com>
 * @compile T6468384.java
 */

import java.util.*;

public class T6468384 {

    public class A {};

    public class B extends A {};

    public <T, V extends T> Set<T> newSet(V... objects) {
        Set<T> set = new LinkedHashSet<T>();
        for (T t : objects) {
            set.add(t);
        }
        return set;
    }

    public static void main(String... args) {
        T6468384 g = new T6468384();

        // B << V => V :> B
        // so B is inferred for V

        // Then consider the return type:
        // Set<A> >> Set<T> => A = T
        // So A is inferred for T
        Set<A> set1 = g.newSet(g.new B());

        Set<A> set2 = g.<A,B>newSet(g.new B());
    }

}
