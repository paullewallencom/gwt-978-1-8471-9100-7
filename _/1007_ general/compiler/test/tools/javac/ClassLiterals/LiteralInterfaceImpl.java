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
 * 07/01/18 @(#)LiteralInterfaceImpl.java	1.5
 * @bug 4055017
 * @summary Test use of class literals within interfaces.
 * @author William Maddox (maddox)
 *
 * @compile LiteralInterface_1.java
 * @compile LiteralInterface_2.java
 * @compile LiteralInterface_3.java
 * @compile LiteralInterfaceImpl.java
 * @run main LiteralInterfaceImpl
 */

public class LiteralInterfaceImpl
implements LiteralInterface_1, LiteralInterface_2, LiteralInterface_3 {
    private static void check(Class c1, Class c2) throws Exception{
	if (c1 != c2) {
	    throw new Exception("mismatch: " + c1 + ", " + c2);
	}
    }
    public static void main(String[] args) throws Exception {

	check(c1, Object.class);
	check(c2, Integer.class);

	check(foo, Object.class);
	check(bar, String.class);
	check(baz, Integer.class);
	
	// Due to another bug (4119981),
	// J is not inherited from LiteralInterface.
	// check(quux, J.class);

	check(quux, LiteralInterface_2.J.class);

	LiteralInterface_2.J o = new LiteralInterface_2.J();
	check(o.quem, Float.class);
	
    }
}
