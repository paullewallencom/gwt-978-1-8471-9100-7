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
 * @test @(#)T4718134.java	1.4 07/01/18
 * @bug 4718134
 * @summary DA/DU analysis vs break/continue vs finally that cannot complete normally
 * @author Neal Gafter (gafter)
 *
 * @compile T4718134.java
 */

class T4718134 {
    void f(int x) {
	final int i;
	L: {
	    if (x==0) break L;
	    try {
		i = 3;
		break L; // this may not exit L
	    } finally {
		return; // because this stops it
	    }
	}
	i = 2; // so there is NO ERROR here
    }
}
