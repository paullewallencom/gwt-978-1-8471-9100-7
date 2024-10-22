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
 * @test @(#)Closure1.java	1.7 07/01/18
 * @bug 4030374
 * @summary Initialization of up-level links, immediately after super(), occurs too late.
 * @author gafter
 *
 * @compile -source 1.4 -target 1.4 Closure1.java
 * @run main Closure1
 */

// Make sure the closure is present when the superclass is constructed.
// Specifically, N must have its T.this initialized when S calls hi().

public class Closure1 {
    static class S {
	void hi() { throw new Error(); }
	S() { hi(); }
    }
    static class T {
	void greet() { }
	class N extends S {
	    void hi() {
		T.this.greet();
	    }
	}
    }
    public static void main(String av[]) { new T().new N(); }
}
