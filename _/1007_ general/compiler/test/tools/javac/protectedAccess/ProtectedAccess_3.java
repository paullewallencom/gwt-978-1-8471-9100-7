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
 * @test @(#)ProtectedAccess_3.java	1.5 07/01/18
 * @bug 4804371
 * @summary javac and java disagree about protected; results in a VerifyError
 * @author gafter
 *
 * @compile ProtectedAccess_3.java
 * @run main ProtectedAccess_3
 */

public class ProtectedAccess_3 {
    ProtectedAccess_3() {
	new Object() {
	    public void finalize() throws Throwable {
		ProtectedAccess_3.this.finalize();
	    }
	};
    }

    public static void main(String[] argv) {
	new ProtectedAccess_3();
    }
}
