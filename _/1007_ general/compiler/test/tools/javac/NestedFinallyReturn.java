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
 * @test @(#)NestedFinallyReturn.java	1.4 07/01/18
 * @bug 4084230
 * @summary Return from within nested try-finally should not crash compiler.
 * 
 * @compile NestedFinallyReturn.java
 */

public class NestedFinallyReturn {
    public static void main(String[] argv) {
	try {
	    Object o = new Object() {
		public String toString() {  
		    try { return "Hi"; } 
		    finally { 
			System.out.println("nestedFinally"); 
		    } 
		} 
	    };
	}
	finally { System.out.println("outerFinally"); }
    }
}
