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
 * @test @(#)ArrayClone.java	1.4 07/01/18
 * @bug 4900415
 * @summary The clone method on arrays should be strongly typed
 * @author gafter
 *
 * @compile -source 1.5 ArrayClone.java
 * @run main ArrayClone
 */

public class ArrayClone {
    public static void main(String... args) {
	if (args.length==0)
	    main("foo", "bar", "xyzzy");
	String[] args2 = args.clone();
	for (int i=0; i<args2.length; i++)
	    if (!args[i].equals(args2[i])) 
		throw new Error(args2[i]);
	f1(1, 2, 3, 4, 5);
    }
    static void f1(int... a) {
	int[] b = a.clone();
	for (int i=0; i<a.length; i++)
	    if (a[i] != b[i])
		throw new Error(""+b[i]);
    }
}
