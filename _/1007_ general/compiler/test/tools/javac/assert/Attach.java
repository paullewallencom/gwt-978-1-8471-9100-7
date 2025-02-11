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
 * @test @(#)Attach.java	1.6 07/01/18
 * @bug 4478095
 * @summary assertion flag attached to wrong class
 * @author gafter
 *
 * @compile -source 1.4 Attach.java
 * @run main Attach
 */

public class Attach {
    public static void main(String[] args) {
        Attach.class.getClassLoader().
            setClassAssertionStatus("Attach", false);
	if (V.assertsEnabled()) 
	    throw new Error("failed 4478095.1");
        if (!U.correct)
	    throw new Error("failed 4478095.2");
    }
    static class U {
        static boolean correct = V.assertsEnabled();
    }
    static class V extends U {
        static boolean assertsEnabled() {
	    boolean enabled = false;
	    assert enabled=true;
	    return enabled;
        }
    }
}
