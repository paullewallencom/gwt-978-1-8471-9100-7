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
 * @test @(#)FaultySignature.java	1.5 07/01/18
 * @bug 4085633
 * @summary javac used to pass the uplevel arguments into insideMember's
 *          constructor in the wrong order
 * @author turnidge
 *
 * @compile FaultySignature.java
 * @run main FaultySignature
 */

public
class FaultySignature {
    void method() throws Exception {
	final int value = 888;

	class local {
	    local() throws Exception {
		(new insideMember()).tester();
	    }

	    class insideMember {
		void tester() throws Exception {
		    if (value != 888) {
			throw new Exception("Signature is out of order.");
		    }
		}
	    }
	}

	new local();
    }

    public static void main(String[] args) throws Exception {
	(new FaultySignature()).method();
    }
}
    
