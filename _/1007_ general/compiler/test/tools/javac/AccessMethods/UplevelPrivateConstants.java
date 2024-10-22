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
 * @test @(#)UplevelPrivateConstants.java	1.4 07/01/18
 * @bug 4098737
 * @summary Compiler must treat an uplevel reference to a
 * private named constant as a constant expression.
 *
 * @compile UplevelPrivateConstants.java
 */

/*
 * Compilation will fail if the case labels are not
 * seen as constant expressions.
 */

class UplevelPrivateConstants {

    class Inner {

	private String value;

	Inner(int code) {
	    switch(code) {
	      case VAL1: value = "value1"; break;
	      case VAL2: value = "value2"; break;
	      case VAL3: value = "value3"; break;
	    }
	}
    }

    private static final int VAL1 = 1;
    private static final int VAL2 = 2;
    private static final int VAL3 = 3;
}
