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
 * @test @(#)RvalConversion.java	1.5 07/01/18
 * @bug 4918637 4931781
 * @summary rvalue conversion changes "? extends X" to "X".
 * @author gafter
 *
 * @compile -source 1.5 RvalConversion.java
 */

import java.util.*;

class T {
    void f(Map<?,?> m) {
	for ( Map.Entry e : m.entrySet() ) { }
    }
}
class Box<T> {
    T get() { return null; }
}
class Main {
    public static void main(String[] args) {
        Box<? extends Integer> bi = null;
        int i = bi.get();
    }
}
