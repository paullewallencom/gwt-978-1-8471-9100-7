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
 * @test @(#)StaticImport.java	1.7 07/01/18
 * @bug 4855358 4965039 4970828
 * @summary add support for JSR 201's static import facility
 * @author gafter
 *
 * @compile -source 1.5 StaticImport.java
 * @run main StaticImport
 */

// for 4970828, the offending code is generated from Collections.
// importing it is sufficient to trigger a javac failure.
import static java.util.Collections.*;

import static java.lang.Math.*;
import static java.lang.System.out;

public class StaticImport {
    public static void main(String[] args) {
	out.println(sin(PI/6));
	if (abs(1.0 - 2*sin(PI/6)) > 0.000001)
	    throw new Error();
    }
}
