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
 * @test @(#)ParenVerify.java	1.4 07/01/18
 * @bug 4881397
 * @summary generics: verify error when redundant parens used!
 * @author gafter
 *
 * @compile -source 1.5 ParenVerify.java
 * @run main ParenVerify
 */

import java.util.*;

public class ParenVerify {

    public static void main(String argss[]) {
	for(Iterator<Integer> i  = test("Foo"); i.hasNext(); )
	    System.out.println(i.next());
    }
    static HashMap<String, LinkedList<Integer>> m
	= new HashMap<String, LinkedList<Integer>>();

    public static Iterator<Integer> test(String s) {
	LinkedList<Integer> lst = new LinkedList<Integer>();
	lst.add(new Integer(12));
	m.put("Hello", lst);

	// jsr14-1.3ea generates code that won't verify.
	// Removing the extra set of parenthesis in the
	// statement below fixes the problem
	lst = (m.get("Hello"));
	return lst.iterator();
    }
}
