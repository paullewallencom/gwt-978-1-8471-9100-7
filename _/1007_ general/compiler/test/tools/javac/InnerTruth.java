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
 * @test @(#)InnerTruth.java	1.4 07/01/18
 * @bug 4242399
 * @summary Verify sense of boolean constants is not reversed
 * inside inner classes.
 * @author maddox
 *
 */

public class InnerTruth {

    static final boolean DEBUG = true;

    public static void main(String[] args) throws Exception {
	InnerTruth me = new InnerTruth();
    }

    InnerTruth() throws Exception {
	new Inner().doIt();
	if (DEBUG) {
	    System.out.println("OK");
	} else {
	    System.out.println("FAILED in outer");
	    throw new Exception("FAILED in outer");
	}
    }

    class Inner {
	public void doIt() throws Exception {
	    if (DEBUG) {
		System.out.println("OK");
	    } else {
		System.out.println("FAILED in inner");
		throw new Exception("FAILED in inner");
	    }
	}
    }
}
