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
 * @test @(#)StringAppendAccessMethodOnLHS.java	1.4 07/01/18
 * @bug 4318526
 * @author iag, maddox
 * @summary Verify that the compiler does not crash if a method in an inner
 * class appends a character to a string declared private in the enclosing
 * class.  Execute the example just for good measure.
 * 
 * @run compile StringAppendAccessMethodOnLHS.java
 * @run main StringAppendAccessMethodOnLHS
 */

public class StringAppendAccessMethodOnLHS {

    // s must be declared private.

    private String s = "";

    class Inner {
 	void m() {
	    s += "a";
 	    s += 'a';  // this is the case that failed in 4318526
 	}
    }

    void test () {
	Inner o = new Inner();
	o.m();
    }

    public static void main(String[] args) throws Exception {
	StringAppendAccessMethodOnLHS o = new StringAppendAccessMethodOnLHS();
	o.test();
	if (!o.s.equals("aa")) throw new Exception();
    }

}
