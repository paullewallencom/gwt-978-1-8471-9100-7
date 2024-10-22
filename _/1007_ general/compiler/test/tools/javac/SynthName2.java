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
 * @test @(#)SynthName2.java	1.6 07/01/18
 * @bug 4462714
 * @summary using of synthetic names in local class causes ClassFormatError
 * @author gafter
 *
 * @compile/fail -source 1.4 -target 1.4 SynthName2.java
 */

import java.io.PrintStream;
 
class SynthName1 {
    public static void main(String args[]) {
	run(args, System.out);
    }
    public static void run(String args[],PrintStream out) {
	int  res1, res2;
	Intf ob = meth(1, 2);
	
	res1 = ob.getFirst();
	res2 = ob.getSecond();
	
	if ( res1 == 1 && res2 == 2 )
	    return;
	out.println("Failed:  res1=" + res1 + ", res2=" + res2);
	throw new Error("test failed!");
    }
    interface Intf {
	int getFirst();
	int getSecond();
    }
    static Intf meth(final int prm1, final int zzz) {
	class InnClass implements Intf {
	    int val$prm1 = prm1;
	    int val$zzz  = zzz;
	    int locVar;
	    public int getFirst() {
		locVar = val$prm1;
		return prm1;
	    }
	    public int getSecond() {
		return zzz;
	    }
	}
	return new InnClass();
    }
}
