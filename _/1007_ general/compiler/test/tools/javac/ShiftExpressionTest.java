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
 * @test @(#)ShiftExpressionTest.java	1.5 07/01/18
 * @bug 4082814
 * @summary Constant shift expressions whose left operands were of type
 *          long were previously not evaluated correctly at compile-time.
 *          This is for the most part invisible, but it can be detected
 *          with the following test after 1.2beta1.
 * @author turnidge
 * 
 * @compile ShiftExpressionTest.java
 * @run main ShiftExpressionTest
 */

public class ShiftExpressionTest {
    public static void main(String[] args) throws Exception {
	String s = "" + (0x0101L << 2) + (0x0101L >> 2) + (0x0101L >>> 2);
	if (s.indexOf("null",0) != -1) {
	    throw new Exception(
		  "incorrect compile-time evaluation of shift");
	}
    }
}
