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

/**
 * @test @(#)EmptyBreak.java	1.4 07/01/18
 * @bug 4238511
 * @summary Empty "default/case" statements should not cause the compiler to crash.
 *
 * @run clean EmptyBreak
 * @run compile EmptyBreak.java
 */

// If the test fails to compile, then the bug exists.

public class EmptyBreak {

    public void emptyDefault(int i) {
	switch (i) {
	    default:
		// empty!
		break;
	}
    }

    public void emptyCase(int i) {
	switch (i) {
	    case 1:
		// empty!
		break;
	}
    }
}
