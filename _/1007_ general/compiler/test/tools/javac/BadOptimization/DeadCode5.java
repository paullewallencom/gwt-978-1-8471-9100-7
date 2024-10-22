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
 * @test @(#)DeadCode5.java	1.4 07/01/18
 * @bug 4136312 4073244
 * @summary init:  Verify that overzealous dead-code elimination no
 * longer removes live code.
 * @author dps
 *
 * @run clean DeadCode5
 * @run compile -O DeadCode5.java
 * @run main DeadCode5
 */


// Test for bug 4136312

public class DeadCode5
{
    int fld;

    static public void main(String args[]) {
        DeadCode5 t = null;
        try {
            int dummy = t.fld;
	    // if we got here, then there must be a problem
	    throw new RuntimeException("accidental removal of live code");
        } catch (NullPointerException e) {
	    System.out.println("NullPointerException correctly thrown");
	    e.printStackTrace();
        }
    }
}
