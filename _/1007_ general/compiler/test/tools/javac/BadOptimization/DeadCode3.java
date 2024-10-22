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
 * @test @(#)DeadCode3.java	1.4 07/01/18
 * @bug 4057345 4120016 4120014
 * @summary final void function:  Verify that overzealous dead-code elimination
 * no longer removes live code.
 * @author dps
 *
 * @run clean DeadCode3
 * @run compile -O DeadCode3.java
 * @run main DeadCode3
 */

public class DeadCode3
{
    private final void fun1() { }

    private void fun2() {
        DeadCode3 r1 = null;
        fun1();

	// we expect an NullPointerException because of this line
        r1.fun1();
    }

    public static void main( String[] args ) {
	try {
	    new DeadCode3() . fun2();
	    // if we got past the constructor, then there must be a problem
	    throw new RuntimeException("accidental removal of live code");
	} catch (NullPointerException e) {
	    System.out.println("NullPointerException correctly thrown");
	    e.printStackTrace();
	}
    }
}
