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
 * @test @(#)ConstValInit.java	1.4 07/01/18
 * @bug 4253590
 * @summary Verify that non-static constant fields are initialized correctly.
 *          Also, while a runtime initialization is required, verify that
 *          references to such fields via a simple variable are correctly 
 *          recognized as constant expressions.
 * @author maddox (adapted from bug report)
 *
 * @run compile ConstValInit.java
 * @run main ConstValInit
 */

public class ConstValInit {

    public final String fnl_str = "Test string";
    
    public final int fnl_int = 1;
    
    public static void main(String args[]) throws Exception {

	Class checksClass = Class.forName("ConstValInit");
	ConstValInit checksObject = (ConstValInit)(checksClass.newInstance());
	String reflected_fnl_str = (String)checksClass.getField("fnl_str").get(checksObject);
	if (!checksObject.fnl_str.equals(reflected_fnl_str)) {
	    throw new Exception("FAILED: ordinary and reflected field values differ");
	}

    }

    void foo() {
	// Statement below will not compile if fnl_int is not recognized
	// as a constant expression.
	switch (1) {
	    case fnl_int: break;
        }
    }

}
