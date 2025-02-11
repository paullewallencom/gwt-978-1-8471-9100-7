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
 * @test @(#)ReturnAfterIfThenElse.java	1.4 07/01/18
 * @bug 4144008
 * @summary Both branches of if-then-else preceding end of method must not return normally,
 * even if boolean expression is constant.
 *
 * @run compile/fail ReturnAfterIfThenElse.java
 */

public class ReturnAfterIfThenElse {
    int method() {
	if (false) {
	    // no return here
	} else {
	    return 1;
	}
	// ERROR
	// A return statement is required here,
	// becase, according to the special rules
	// for if-then-else in JLS 14.19, the
	// preceding statement *can* complete normally.
    }
}
