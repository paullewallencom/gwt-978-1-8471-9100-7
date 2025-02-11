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
 * @test @(#)BitwiseAssignment.java	1.5 07/01/18
 * @bug 4387704
 * @summary Check correct generation of bitwise assignment access method.
 * @author Neal Gafter (gafter)
 *
 * @compile BitwiseAssignment.java
 * @run main BitwiseAssignment
 */

public class BitwiseAssignment {
    private static class Inner {
	private int data = 4711;
    }

    public static void main(String[] args) {
	{
	    Inner inner1 = new Inner();
	    inner1.data ^= 42;

	    Inner inner2 = new Inner();
	    inner2.data = inner2.data ^ 42;
	    if (inner1.data != inner2.data) throw new Error("Failed inner ^=");
	}

	{
	    Inner inner1 = new Inner();
	    inner1.data |= 42;

	    Inner inner2 = new Inner();
	    inner2.data = inner2.data | 42;
	    if (inner1.data != inner2.data) throw new Error("Failed inner |=");
	}

	{
	    Inner inner1 = new Inner();
	    inner1.data &= 4211;

	    Inner inner2 = new Inner();
	    inner2.data = inner2.data & 4211;
	    if (inner1.data != inner2.data) throw new Error("Failed inner &=");
	}
    }
}
