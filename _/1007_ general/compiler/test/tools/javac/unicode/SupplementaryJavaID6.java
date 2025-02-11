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
 * @(#)SupplementaryJavaID6.java	1.6 07/01/18
 */

public class SupplementaryJavaID6 {
    public static void main(String[] s) {
        new SupplementaryJavaID6();
    }

    public SupplementaryJavaID6() {
        \ud801\udc00 instance = new \ud801\udc00();
	instance.\ud801\udc01();
    }

    class \ud801\udc00 {
        void \ud801\udc01() {
	    // If Java can create the strangely named class file,
	    // then Java can delete it, while `rm' might be unable to.
	    new java.io.File(this.getClass().getName() + ".class")
		.deleteOnExit();
	    System.out.println("success");
        }
    }
}
