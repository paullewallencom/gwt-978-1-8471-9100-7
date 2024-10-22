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
 * @test @(#)Closure2.java	1.6 07/01/18
 * @bug 4030374
 * @summary Initialization of up-level links, immediately after super(), occurs too late.
 * @author gafter
 *
 * @compile -source 1.4 -target 1.4 Closure2.java
 * @run main Closure2
 */

// Make sure the closure is present when the superclass is constructed.
// Specifically, inner2 must have its Closure2.this initialized when inner calls go().

public class Closure2 {
    private int mValue;

    public Closure2() {
	new inner2();
    }

    private abstract class inner {
	public inner() {
	    go();
	}
	public abstract void go();
    }

    private class inner2 extends inner {
	public void go() {
	    mValue = 2;
	}
    }

    public static void main(String args[]) {
	new Closure2();
    }
}
