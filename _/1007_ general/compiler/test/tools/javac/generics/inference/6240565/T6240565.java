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
 * @test    @(#)T6240565.java	1.6 07/01/18
 * @bug     6240565
 * @summary Unboxing, arrays, and type variables doesn't work
 * @compile T6240565.java
 * @run main/othervm -Xfuture T6240565
 */

public class T6240565 {
    public static <E extends Integer> void doit(E[] a) {
        System.out.println(a[0] / 4);
	for (int i : a)
	    System.out.println(i / 4);
	for (E e : a) {
	    E f = e;
	    System.out.println(e / 4);
	}
    }
    public static void main(String[] args) {
        T6240565.doit(new Integer[]{4 , 8 , 12});
    }
}
