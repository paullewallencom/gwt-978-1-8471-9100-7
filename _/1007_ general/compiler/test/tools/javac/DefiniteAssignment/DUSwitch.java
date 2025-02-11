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
 * @test @(#)DUSwitch.java	1.5 07/01/18
 * @bug 4452153
 * @summary Check correct handling of DU in switch statements
 * @author Neal Gafter (gafter)
 *
 * @run compile/fail DUSwitch.java
 */

class DUSwitch {
    void foo() {
        int c = 6; 
        final int a1; 
 
        switch (c%(a1=4)) { 
	case 1:
	    c+=1;  
	    break; 
	case 2:
	    c+=a1;
	    System.out.println("case2 "+c); 
	default:
	    a1=4; 
	    c+=a1;
	    System.out.println("default "+c); 
	    break; 
        } 
    }
}
