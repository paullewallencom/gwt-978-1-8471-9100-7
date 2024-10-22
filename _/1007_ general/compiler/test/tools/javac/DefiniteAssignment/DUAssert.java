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
 * @test @(#)DUAssert.java	1.5 %E
 * @bug 4478838 4533580
 * @summary Check correct handling of DU in assert statements
 * @author Neal Gafter (gafter)
 *
 * @run compile -source 1.4 DUAssert.java
 */

class DUSwitch {
    void foo() {
	final int i; 
	assert true : i=3; 
	i=4; 
    }
    void bar(boolean b) {
	final int i; 
	assert b : i=3; 
	i=4; 
    }
    void baz() {
	final int i; 
	assert false : i=3; 
	i=4; 
    }
}
