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
 * @test @(#)SuperNew2.java	1.5 07/01/18
 * @bug 4636022
 * @summary Test for a regression noted in an internal milestone.
 * @author gafter
 *
 * @compile SuperNew2.java
 * @run main SuperNew2
 */

public class SuperNew2 {
    int x;
    
    class Dummy {
	Dummy(Object o) {}
    }
    
    class Inside extends Dummy {
	Inside(final int y) {
	    super(new Object() { int r = y; }); // ok
	}
    }
    
    public static void main(String[] args) {
	Object o = new SuperNew2().new Inside(12);
    }
}
